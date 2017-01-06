package ar.com.sac.model;

import java.util.Date;

public class Notification {
   
   private Date creationDate;
   private Alert alert;
   
   public synchronized Date getCreationDate() {
      return creationDate;
   }
   
   public synchronized void setCreationDate( Date creationDate ) {
      this.creationDate = creationDate;
   }
   
   public synchronized Alert getAlert() {
      return alert;
   }
   
   public synchronized void setAlert( Alert alert ) {
      this.alert = alert;
   }

   
}
