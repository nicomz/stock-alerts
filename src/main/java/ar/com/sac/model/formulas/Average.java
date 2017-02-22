package ar.com.sac.model.formulas;

import ar.com.sac.model.Quote;
import java.math.BigDecimal;
import java.util.List;


public class Average implements Formula {
 private List<Quote> quotes;
   

   public Average( List<Quote> quotes){
      this.quotes = quotes;
   }

   @Override
   public BigDecimal calculate() {
      double sum = 0;
      for(Quote quote : quotes){
         sum += quote.getClose().doubleValue();
      }
      
      if (quotes.size() > 0){
         return  new BigDecimal( sum / quotes.size() );
      }else{
         return new BigDecimal( sum );
      }
   }


}
