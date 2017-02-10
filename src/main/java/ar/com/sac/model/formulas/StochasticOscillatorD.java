package ar.com.sac.model.formulas;

import ar.com.sac.model.Quote;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class StochasticOscillatorD implements Formula {
   private int length;
   private int period;
   private List<Quote> quotes;

   public StochasticOscillatorD( int length, int period, List<Quote> quotes){
      this.length = length;
      this.period = period;
      this.quotes = quotes;
   }
   
   @Override
   public BigDecimal calculate() {
      validate();
      List<BigDecimal> ks = new ArrayList<>();
      for(int i = 0; i < period ; i++){
         ks.add( new StochasticOscillatorK( length, quotes.subList( i, quotes.size() ) ).calculate());
      }
      
      double sum = 0;
      for(BigDecimal k : ks){
         sum += k.doubleValue();
      }
      
      return new BigDecimal( sum / period );
   }

   private void validate() {
      if( quotes.size() < length ){
         throw new RuntimeException( "Stochastic Oscillator: There are no quotes to calculate Stochastic Oscillator" );
      }
   }
}
