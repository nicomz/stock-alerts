package ar.com.sac.model.formulas;

import ar.com.sac.model.Quote;
import java.math.BigDecimal;
import java.util.List;


public class StandardDeviation implements Formula {
   
 private List<Quote> quotes;
   

   public StandardDeviation( List<Quote> quotes){
      this.quotes = quotes;
   }


   @Override
   public BigDecimal calculate() {
      double variance = new Variance( quotes ).calculate().doubleValue();
      return new BigDecimal( Math.sqrt( variance ) );
   }

}
