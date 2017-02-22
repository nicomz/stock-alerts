package ar.com.sac.model.formulas;

import ar.com.sac.model.Quote;
import java.math.BigDecimal;
import java.util.List;


public class Average implements Formula {
   private List<Quote> quotes;
   private int period;
   

   public Average(int period, List<Quote> quotes){
      this.period = period;
      this.quotes = quotes;
   }

   @Override
   public BigDecimal calculate() {
      double sum = 0;
      for(Quote quote : quotes.subList( 0, period )){
         sum += quote.getClose().doubleValue();
      }
      
      if (quotes.size() > 0 && period > 0 ){
         return  new BigDecimal( sum / period );
      }else{
         return new BigDecimal( sum );
      }
   }


}
