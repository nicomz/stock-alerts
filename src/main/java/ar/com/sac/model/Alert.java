package ar.com.sac.model;


public class Alert {
   
   private Boolean active;
   private String expression;
   private String name;
   private String description;
   private Boolean sendEmail;
   
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
   

}
