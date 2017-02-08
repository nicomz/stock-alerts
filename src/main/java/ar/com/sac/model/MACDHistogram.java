package ar.com.sac.model;

import java.math.BigDecimal;
import java.util.List;

public class MACDHistogram implements Formula{
   
   private int slowPeriod; //12
   private int fastPeriod; //26
   private int signalPeriod; //9
   private List<Quote> quotes;
   

   public MACDHistogram( int fastPeriod, int slowPeriod, int signalPeriod, List<Quote> quotes){
      this.fastPeriod = fastPeriod;
      this.slowPeriod = slowPeriod;
      this.signalPeriod = signalPeriod;
      this.quotes = quotes;
   }


   @Override
   public BigDecimal calculate() {
      MACD macd = new MACD( fastPeriod, slowPeriod, quotes );
      MACDSignalLine macdSignalLine = new MACDSignalLine( fastPeriod, slowPeriod, signalPeriod, quotes );
      return macd.calculate().subtract( macdSignalLine.calculate() );
   }

}
