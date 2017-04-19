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

   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((date == null) ? 0 : date.hashCode());
      result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
      return result;
   }

   /* (non-Javadoc)
    * @see java.lang.Object#equals(java.lang.Object)
    */
   @Override
   public boolean equals( Object obj ) {
      if ( this == obj ) return true;
      if ( obj == null ) return false;
      if ( getClass() != obj.getClass() ) return false;
      QuoteId other = (QuoteId)obj;
      if ( date == null ) {
         if ( other.date != null ) return false;
      } else if ( !date.equals( other.date ) ) return false;
      if ( symbol == null ) {
         if ( other.symbol != null ) return false;
      } else if ( !symbol.equals( other.symbol ) ) return false;
      return true;
   }
   

}
