package ar.com.sac.model.formulas;

import ar.com.sac.model.Quote;
import java.math.BigDecimal;
import java.util.List;


public class StochasticOscillatorK implements Formula {

   private int length;
   private List<Quote> quotes;

   public StochasticOscillatorK( int length, List<Quote> quotes){
      this.length = length;
      this.quotes = quotes;
   }
   
   @Override
   public BigDecimal calculate() {
      validate();
      Quote firstQuote = quotes.get( 0 );
      double highestHigh = firstQuote.getHigh().doubleValue();
      double lowestLow = firstQuote.getLow().doubleValue();
      
      Quote quote;
      for(int i = 0 ; i < length; i++ ){
         quote = quotes.get( i );
         if(quote.getLow().doubleValue() < lowestLow){
            lowestLow = quote.getLow().doubleValue();
         }
         
         if(quote.getHigh().doubleValue() > highestHigh){
            highestHigh = quote.getHigh().doubleValue();
         }
      }
      //100(C - L14)/(H14 - L14)
      double dividend = highestHigh - lowestLow ;
      double numerator = firstQuote.getClose().doubleValue() - lowestLow ;
      return new BigDecimal( (numerator / dividend) * 100d);
   }

   private void validate() {
      if( quotes.size() < length ){
         throw new RuntimeException( "Stochastic Oscillator: There are no quotes to calculate Stochastic Oscillator" );
      }
   }

}
