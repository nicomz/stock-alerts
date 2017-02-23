package ar.com.sac.services;

import ar.com.sac.model.Alert;
import ar.com.sac.model.Notification;
import ar.com.sac.model.operations.Operator;
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
   private ExpressionService expressionService;
   
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
      Operator operator = expressionService.parseExpression( alert.getExpression(), stockService );
      if(operator.evaluate()){
         Notification notification = new Notification();
         notification.setCreationDate( new Date() );
         notification.setAlert( alert );
         notifications.add( notification  );
      }
   }

   @Transactional
   public void saveAlert( Alert newAlert ) {
      normalizeId( newAlert );
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
      normalizeId( alert );
      if( getAlertById( alert.getId() ) == null ){
         throw new RuntimeException( "It doesn't exist an alert with id: " + alert.getId() );
      }
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
   
   
   private void normalizeId( Alert alert ) {
      alert.setId( toCamelCase( alert.getId()) );
   }
   
   private String toCamelCase(final String init) {
      if (init==null)
          return null;

      final StringBuilder ret = new StringBuilder(init.length());
      String word;
      String[] split = init.split(" ");
      for ( int i=0 ; i < split.length ; i++ ) {
         word = split[i];
         if (word.isEmpty()) {
            continue;
         }
         if( i == 0 ){
            ret.append( word.toLowerCase() );
         }else{
            ret.append(word.substring(0, 1).toUpperCase());
            ret.append(word.substring(1).toLowerCase());
         }
      }

      return ret.toString();
  }

}
