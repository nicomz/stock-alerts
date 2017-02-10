package ar.com.sac.model.formulas;

import ar.com.sac.model.Quote;
import java.math.BigDecimal;
import java.util.List;


public class SimpleMovingAverage implements Formula {
   
   private int period;
   private List<Quote> quotes;

   /**
    * @param period must be greater than 0
    * @param quotes must be in inverse order: Most recent quote first
    */
   public SimpleMovingAverage( int period, List<Quote> quotes ){
      this.period = period;
      this.quotes = quotes;
   }
   
   public BigDecimal calculate(){
      validate();
      BigDecimal sum = new BigDecimal( 0 );
      
      for( int i = 0; i < period; i++){
         sum = sum.add( quotes.get(i).getClose() );
      }
      
      return new BigDecimal( sum.doubleValue() /  period  );
   }

   private void validate() {
      if(quotes.size() < period){
         throw new RuntimeException( "SMA: There are no quotes to calculate SMA" );
      }
   }

}
