package ar.com.sac.model.formulas;

import ar.com.sac.model.Quote;
import java.math.BigDecimal;
import java.util.List;


public class Variance implements Formula {
   
   private List<Quote> quotes;
   

   public Variance( List<Quote> quotes){
      this.quotes = quotes;
   }

   @Override
   public BigDecimal calculate() {
      double average = new Average( quotes ).calculate().doubleValue();
      double sum = 0;
      for(Quote quote : quotes){
         sum += Math.pow( quote.getClose().doubleValue() - average, 2d );
      }
      if(quotes.size() == 0){
         return new BigDecimal( 0 );
      }
      return new BigDecimal( sum / quotes.size() );
   }

}
