package ar.com.sac.model.formulas;

import ar.com.sac.model.Quote;
import java.math.BigDecimal;
import java.util.List;


public class StandardDeviation implements Formula {
   
   private List<Quote> quotes;
   private int period;
   

   public StandardDeviation( int period, List<Quote> quotes){
      this.period = period;
      this.quotes = quotes;
   }


   @Override
   public BigDecimal calculate() {
      double variance = new Variance( period, quotes ).calculate().doubleValue();
      return new BigDecimal( Math.sqrt( variance ) );
   }

}
