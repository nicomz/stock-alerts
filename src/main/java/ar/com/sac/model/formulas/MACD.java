package ar.com.sac.model.formulas;

import ar.com.sac.model.Quote;
import java.math.BigDecimal;
import java.util.List;


public class MACD implements Formula {
   
   private List<Quote> quotes;
   private int slowPeriod; //12
   private int fastPeriod; //26

   public MACD( int fastPeriod, int slowPeriod, List<Quote> quotes ){
      this.fastPeriod = fastPeriod;
      this.slowPeriod = slowPeriod;
      this.quotes = quotes;
   }

   @Override
   public BigDecimal calculate() {
      BigDecimal emaFast = new ExponentialMovingAverage( fastPeriod, quotes ).calculate();
      BigDecimal emaSlow = new ExponentialMovingAverage( slowPeriod, quotes ).calculate();
      return emaFast.subtract( emaSlow );
   }

}
