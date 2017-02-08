package ar.com.sac.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Alert {
   
   @Id
   private String id;
   private Boolean active;
   private String expression;
   private String name;
   private String description;
   private Boolean sendEmail;
   private String symbol;
   private String opposedAlertId;
   
   
   public synchronized String getSymbol() {
      return symbol;
   }

   
   public synchronized void setSymbol( String symbol ) {
      this.symbol = symbol;
   }

   
   public synchronized String getOpposedAlertId() {
      return opposedAlertId;
   }

   
   public synchronized void setOpposedAlertId( String opposedAlertId ) {
      this.opposedAlertId = opposedAlertId;
   }

   public synchronized Boolean getActive() {
      return active;
   }
   
   public synchronized void setActive( Boolean active ) {
      this.active = active;
   }
   
   public synchronized String getExpression() {
      return expression;
   }
   
   public synchronized void setExpression( String expression ) {
      this.expression = expression;
   }
   
   public synchronized String getName() {
      return name;
   }
   
   public synchronized void setName( String name ) {
      this.name = name;
   }
   
   public synchronized String getDescription() {
      return description;
   }
   
   public synchronized void setDescription( String description ) {
      this.description = description;
   }
   
   public synchronized Boolean getSendEmail() {
      return sendEmail;
   }
   
   public synchronized void setSendEmail( Boolean sendEmail ) {
      this.sendEmail = sendEmail;
   }

   public String getId() {
      return id;
   }

   public void setId( String id ) {
      this.id = id;
   }
   

}
