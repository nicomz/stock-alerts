package ar.com.sac.model;

import java.math.BigDecimal;
import java.util.List;

public class ExponentialMovingAverage {
   
   private int period;
   private List<Quote> quotes;

   public ExponentialMovingAverage( int period, List<Quote> quotes){
      this.period = period;
      this.quotes = quotes;
   }
   
   public BigDecimal calculate(){
      validate();
      List<Quote> extractedQuotes = quotes.subList( 0, period );
      
      return new BigDecimal(0);
   }

   private void validate() {
      if(period > quotes.size()){
         throw new RuntimeException( "EMA period should be shorter than number of quotes" );
      }
      
   }
}
