package ar.com.sac.services;

import ar.com.sac.model.Alert;
import ar.com.sac.model.Notification;
import ar.com.sac.model.Quote;
import ar.com.sac.model.formulas.ExponentialMovingAverage;
import ar.com.sac.model.formulas.MACD;
import ar.com.sac.model.formulas.MACDHistogram;
import ar.com.sac.model.formulas.MACDSignalLine;
import ar.com.sac.model.formulas.Price;
import ar.com.sac.model.formulas.RelativeStrengthIndex;
import ar.com.sac.model.formulas.SimpleMovingAverage;
import ar.com.sac.model.formulas.StochasticOscillatorD;
import ar.com.sac.model.formulas.StochasticOscillatorK;
import ar.com.sac.model.formulas.Volume;
import ar.com.sac.model.operations.OperationConstantValue;
import ar.com.sac.model.operations.OperationFormula;
import ar.com.sac.model.operations.OperationTerm;
import ar.com.sac.model.operations.Operator;
import ar.com.sac.model.operations.OperatorAND;
import ar.com.sac.model.operations.OperatorGREATERThan;
import ar.com.sac.model.operations.OperatorLESSThan;
import ar.com.sac.services.dao.AlertDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@EnableScheduling
@PropertySource("classpath:application.properties")
@Transactional
public class AlertService {
   
   @Autowired
   private StockService stockService;
   @Autowired
   private EmailService emailService;
   
   @Autowired
   private AlertDAO alertDAO;
   
   @Value("${alerts.application.host}")
   private String host;
   
   @Transactional(readOnly = true)
   public List<Alert> getAlerts(boolean onlyActive){
      return alertDAO.getAlerts( onlyActive );
   }
   
   @Scheduled(cron = "${alerts.process.cron}")
   public void processAlertsJob(){
      System.out.println( "Processing Alerts JOB: " + new Date() );
      processAlerts();
   }
   
   public List<Notification> processAlerts() {
      return processAlerts( getAlerts( true ));
   }
   
   public List<Notification> processAlerts( List<Alert> alerts ){
      List<Notification> notifications = new ArrayList<Notification>();
      for(Alert alert : alerts){
         processAlert(alert, notifications);
      }
      
      Alert alert;
      for(Notification notification: notifications){
         alert = notification.getAlert();
         if(!alert.getSendEmail()){
            continue;
         }
         try {
            emailService.generateAndSendEmail( getEmailSubject(alert), getEmailBody(alert) );
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
      return notifications;
   }
   
   private String getEmailSubject( Alert alert ){
      return "(Stock Alert) " + alert.getName();
   }
   
   private String getEmailBody( Alert alert ){
      StringBuilder sb = new StringBuilder();
      sb.append( alert.getDescription() );
      sb.append( "<BR>" );
      sb.append( "Expression: " + alert.getExpression().replaceAll( ">", "&gt;" ).replaceAll( "<", "&lt;" ) );
      sb.append( "<BR><BR>" );
      sb.append( "<a href=\"" + generateLink(alert.getId()) +  "/deactivate\" target=\"_blank\">Deactivate this alert</a>" );
      sb.append( "<BR>" );
      if(alert.getOpposedAlertId() != null && !alert.getOpposedAlertId().isEmpty()){
         sb.append( "<a href=\"" + generateLink(alert.getOpposedAlertId()) +  "/activate\" target=\"_blank\">Activate opposed alert " + alert.getOpposedAlertId() + "</a>" );
         sb.append( "<BR>" );
      }
      sb.append( "<BR>" );
      sb.append( "<a href=\"http://finance.yahoo.com/chart/" + alert.getSymbol() + "\" target=\"_blank\">See the chart on Yahoo Finance</a>" );
      sb.append( "<BR>" );
      sb.append( "<i>Stock Alerts</i><br><b>Sergio A. Cormio</b>" );
      return sb.toString();
   }
   

   private String generateLink( String alertId ) {
      String link = host;
      if(!link.endsWith( "/" )){
         link += "/";
      }
      link += "alerts/";
      return link + alertId ;
   }

   private void processAlert( Alert alert, List<Notification> notifications ) {
      Operator operator = parseExpression( alert.getExpression().replace( " ", "" ).toUpperCase() );
      if(operator.evaluate()){
         Notification notification = new Notification();
         notification.setCreationDate( new Date() );
         notification.setAlert( alert );
         notifications.add( notification  );
      }
   }

   private Operator parseExpression( String expression ) {
      String[] andSplit = expression.split( "&&" );
      Operator result;
      if(andSplit.length == 1){
         result = processANDTerm( expression );
      }else{
         OperatorAND and = new OperatorAND();
         for(int i = 0 ; i < andSplit.length; i++){
            and.addTerm( processANDTerm( andSplit[i] ) );
         }
         result = and;
      }
      
      return result;
   }

   private Operator processANDTerm( String expression ) {
      Operator operator;
      if(expression.contains( ">" )){
         String[] greaterSplit = expression.split( ">" );
         OperatorGREATERThan greater = new OperatorGREATERThan();
         for(int i = 0 ; i < greaterSplit.length; i++){
            greater.addOperationTerm( processOperationTerm( greaterSplit[i] ) );
         }
         operator = greater;
      }else if(expression.contains( "<" )){
         String[] lessSplit = expression.split( "<" );
         OperatorLESSThan less = new OperatorLESSThan();
         for(int i = 0 ; i < lessSplit.length; i++){
            less.addOperationTerm( processOperationTerm( lessSplit[i] ) );
         }
         operator = less;
      }else{
         throw new RuntimeException( "Invalid Expression: " + expression );
      }
      return operator;
   }

   private OperationTerm processOperationTerm( String expression ) {
      OperationTerm result;
      if(expression.startsWith( "EMA" )){
         String[] params = expression.split( "," );
         List<Quote> quotes;
         try {
            quotes = stockService.getHistory( params[1].replace( ")","" ) );
         } catch (Exception e) {
            quotes = new ArrayList<Quote>();
            e.printStackTrace();
         }
         ExponentialMovingAverage ema = new ExponentialMovingAverage( Integer.parseInt( params[0].substring( 4 ) ), quotes );
         result = new OperationFormula( ema );
      }else if(expression.startsWith( "SMA" )){
         String[] params = expression.split( "," );
         List<Quote> quotes;
         try {
            quotes = stockService.getHistory( params[1].replace( ")","" ) );
         } catch (Exception e) {
            quotes = new ArrayList<Quote>();
            e.printStackTrace();
         }
         SimpleMovingAverage sma = new SimpleMovingAverage( Integer.parseInt( params[0].substring( 4 ) ), quotes );
         result = new OperationFormula( sma );
      }else if(expression.startsWith( "RSI" )){
         String[] params = expression.split( "," );
         List<Quote> quotes;
         try {
            quotes = stockService.getHistory( params[1].replace( ")","" ) );
         } catch (Exception e) {
            quotes = new ArrayList<Quote>();
            e.printStackTrace();
         }
         RelativeStrengthIndex rsi = new RelativeStrengthIndex( Integer.parseInt( params[0].substring( 4 ) ), quotes );
         result = new OperationFormula( rsi );
      }else if(expression.startsWith( "PRICE" )){
         String symbolParam = expression.substring( 6 ).replace( ")","" );
         Quote quote;
         try {
            quote = stockService.getStock( symbolParam ).getLastQuote();
         } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException( "No quote for symbol: " + symbolParam );
         }
         result = new OperationFormula( new Price( quote ) );
      }else if(expression.startsWith( "VOLUME" )){
         String symbolParam = expression.substring( 7 ).replace( ")","" );
         Quote quote;
         try {
            quote = stockService.getStock( symbolParam ).getLastQuote();
         } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException( "No quote for symbol: " + symbolParam );
         }
         result = new OperationFormula( new Volume( quote ) );
      }else if(expression.startsWith( "MACD_SIGNAL_LINE" )){
         String[] params = expression.split( "," );
         List<Quote> quotes;
         try {
            quotes = stockService.getHistory( params[3].replace( ")","" ) );
         } catch (Exception e) {
            quotes = new ArrayList<Quote>();
            e.printStackTrace();
         }
         MACDSignalLine macdSignal = new MACDSignalLine( Integer.parseInt( params[0].substring( 17 ) ), Integer.parseInt( params[1] ), Integer.parseInt( params[2] ), quotes );
         result = new OperationFormula( macdSignal );
      }else if(expression.startsWith( "MACD_HISTOGRAM" )){
         String[] params = expression.split( "," );
         List<Quote> quotes;
         try {
            quotes = stockService.getHistory( params[3].replace( ")","" ) );
         } catch (Exception e) {
            quotes = new ArrayList<Quote>();
            e.printStackTrace();
         }
         MACDHistogram macdHistogram = new MACDHistogram( Integer.parseInt( params[0].substring( 15 ) ), Integer.parseInt( params[1] ), Integer.parseInt( params[2] ), quotes );
         result = new OperationFormula( macdHistogram );
      }else if(expression.startsWith( "MACD" )){
         String[] params = expression.split( "," );
         List<Quote> quotes;
         try {
            quotes = stockService.getHistory( params[2].replace( ")","" ) );
         } catch (Exception e) {
            quotes = new ArrayList<Quote>();
            e.printStackTrace();
         }
         MACD macd = new MACD( Integer.parseInt( params[0].substring( 5 ) ), Integer.parseInt( params[1] ), quotes );
         result = new OperationFormula( macd );
      }else if(expression.startsWith( "STOCHASTIC_K" )){
         String[] params = expression.split( "," );
         List<Quote> quotes;
         try {
            quotes = stockService.getHistory( params[1].replace( ")","" ) );
         } catch (Exception e) {
            quotes = new ArrayList<Quote>();
            e.printStackTrace();
         }
         StochasticOscillatorK sok = new StochasticOscillatorK( Integer.parseInt( params[0].substring( 13 ) ), quotes );
         result = new OperationFormula( sok );
      }else if(expression.startsWith( "STOCHASTIC_D" )){
         String[] params = expression.split( "," );
         List<Quote> quotes;
         try {
            quotes = stockService.getHistory( params[2].replace( ")","" ) );
         } catch (Exception e) {
            quotes = new ArrayList<Quote>();
            e.printStackTrace();
         }
         StochasticOscillatorD sod = new StochasticOscillatorD( Integer.parseInt( params[0].substring( 13 )), Integer.parseInt( params[1] ) , quotes );
         result = new OperationFormula( sod );
      }else{
         result = new OperationConstantValue( Double.parseDouble( expression ) );
      }
      return result;
   }

   @Transactional
   public void saveAlert( Alert newAlert ) {
      alertDAO.persist( newAlert );
   }

   @Transactional
   public void deleteAlertById( String alertId ) {
      Alert alertToDelete = alertDAO.findById( alertId );
      if( alertToDelete != null ){
         alertDAO.remove( alertToDelete );
      }
   }

   @Transactional
   public void updateAlert( Alert alert ) {
      alertDAO.update( alert );
   }

   @Transactional( readOnly = true )
   public Alert getAlertById( String alertId ) {
      return alertDAO.findById( alertId );
   }
   
   @Transactional
   public void activateAlert( String alertId ) {
      changeActive(alertId, true);
   }
   
   @Transactional
   public void deactivateAlert( String alertId ) {
      changeActive(alertId, false);
   }

   private void changeActive( String alertId, boolean value ) {
      Alert alert = getAlertById( alertId );
      if(alert == null){
         return;
      }
      alert.setActive( value );
      alertDAO.update( alert );
   }

   public List<Alert> getAlertsBySymbol( String symbol ) {
      return alertDAO.getAlertsBySymbol(symbol);
   }

}
