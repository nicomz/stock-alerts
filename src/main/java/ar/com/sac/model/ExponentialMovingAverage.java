package ar.com.sac.model;

import java.math.BigDecimal;
import java.util.List;

public class ExponentialMovingAverage {
   
   private int period;
   private List<Quote> quotes;
   private double alpha;

   public ExponentialMovingAverage( int period, List<Quote> quotes){
      this.period = period;
      this.quotes = quotes;
   }
   
   public BigDecimal calculate(){
      validate();
      alpha = 2d / (period + 1);
      
      return new BigDecimal(calculateEMA());
   }

   private double calculateEMA() {
      double currentEMA = quotes.get( quotes.size() -1 ).getClose().doubleValue();
      Quote quote;
      for(int i = quotes.size()-2; i >= 0; i-- ){
         quote = quotes.get( i );
         currentEMA = alpha * quote.getClose().doubleValue() + ((1 - alpha) * currentEMA);
      }
      
      return currentEMA;
   }

   private void validate() {
      if(quotes.size() == 0){
         throw new RuntimeException( "EMA: There are no quotes to calculate EMA" );
      }
      
   }
}
