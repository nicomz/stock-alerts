package ar.com.sac.model.formulas;

import ar.com.sac.model.Quote;
import java.math.BigDecimal;
import java.util.List;

public class ExponentialMovingAverage implements Formula{
   
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
      SimpleMovingAverage sma = new SimpleMovingAverage( period, quotes.subList( quotes.size() - period, quotes.size() ) );
      double currentEMA = sma.calculate().doubleValue();
      
      Quote quote;
      for(int i = quotes.size()- period - 1; i >= 0; i-- ){
         quote = quotes.get( i );
         currentEMA = alpha * quote.getClose().doubleValue() + ((1 - alpha) * currentEMA);
      }
      
      return currentEMA;
   }

   private void validate() {
      if(quotes.size() < period){
         throw new RuntimeException( "EMA: There are not enough quotes to calculate EMA(" + period + ")" );
      }
      
   }
}
