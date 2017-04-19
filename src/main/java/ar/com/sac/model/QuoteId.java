package ar.com.sac.model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Embeddable;

@Embeddable
public class QuoteId implements Serializable {
   private static final long serialVersionUID = 6539579434350594660L;
   private String symbol;
   private Calendar date;
   
   
   public QuoteId( String symbol, Calendar date ) {
      this.symbol = symbol;
      setDate( date );
   }

   /**
    * @return the symbol
    */
   public synchronized String getSymbol() {
      return symbol;
   }
   
   /**
    * @param symbol the symbol to set
    */
   public synchronized void setSymbol( String symbol ) {
      this.symbol = symbol;
   }
   
   /**
    * @return the date
    */
   public synchronized Calendar getDate() {
      return date;
   }
   
   /**
    * @param date the date to set
    */
   public synchronized void setDate( Calendar date ) {
      date = normalizeDate(date);
      this.date = date;
   }

   private Calendar normalizeDate( Calendar date ) {
      if(date != null){
         date.set(Calendar.HOUR_OF_DAY, 0);
         date.set(Calendar.MINUTE, 0);
         date.set(Calendar.SECOND, 0);
         date.set(Calendar.MILLISECOND, 0);
      }
      return date;
   }
   
   

}
