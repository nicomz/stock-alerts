package ar.com.sac.services;

import ar.com.sac.model.Alert;
import ar.com.sac.model.ExponentialMovingAverage;
import ar.com.sac.model.Notification;
import ar.com.sac.model.Price;
import ar.com.sac.model.Quote;
import ar.com.sac.model.RelativeStrengthIndex;
import ar.com.sac.model.operations.OperationConstantValue;
import ar.com.sac.model.operations.OperationFormula;
import ar.com.sac.model.operations.OperationTerm;
import ar.com.sac.model.operations.Operator;
import ar.com.sac.model.operations.OperatorAND;
import ar.com.sac.model.operations.OperatorGREATERThan;
import ar.com.sac.services.dao.AlertDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
   
   @Transactional(readOnly = true)
   public List<Alert> getAlerts(boolean onlyActive){
      return alertDAO.getAlerts( onlyActive );
   }
   
   @Scheduled(cron = "${alerts.process.cron}")
   public void processAlertsJob(){
      processAlerts();
   }
   
   public List<Notification> processAlerts() {
      List<Notification> notifications = new ArrayList<Notification>();
      for(Alert alert : getAlerts( true )){
         processAlert(alert, notifications);
      }
      
      Alert alert;
      for(Notification notification: notifications){
         alert = notification.getAlert();
         if(!alert.getSendEmail()){
            continue;
         }
         try {
            emailService.generateAndSendEmail( alert.getName(), alert.getDescription() );
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
      return notifications;
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
         OperatorGREATERThan greater = processAND( expression );
         result = greater;
      }else{
         OperatorAND and = new OperatorAND();
         for(int i = 0 ; i < andSplit.length; i++){
            and.addTerm( processAND( andSplit[i] ) );
         }
         result = and;
      }
      
      return result;
   }

   private OperatorGREATERThan processAND( String expression ) {
      String[] greaterSplit = expression.split( ">" );
      if(greaterSplit.length == 1){
         throw new RuntimeException( "Invalid Expression: " + expression );
      }
      OperatorGREATERThan greater = new OperatorGREATERThan();
      for(int i = 0 ; i < greaterSplit.length; i++){
         greater.addOperationTerm( processOperationTerm( greaterSplit[i] ) );
      }
      return greater;
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
      }else{
         result = new OperationConstantValue( Double.parseDouble( expression ) );
      }
      return result;
   }

   @Transactional
   public void saveAlert( Alert newAlert ) {
      alertDAO.persist( newAlert );
   }

}
