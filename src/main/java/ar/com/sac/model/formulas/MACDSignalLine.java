package ar.com.sac.model.formulas;

import ar.com.sac.model.Quote;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class MACDSignalLine implements Formula {
   
   private int slowPeriod; //12
   private int fastPeriod; //26
   private int signalPeriod; //9
   private List<Quote> quotes;
   

   public MACDSignalLine( int fastPeriod, int slowPeriod, int signalPeriod, List<Quote> quotes){
      this.fastPeriod = fastPeriod;
      this.slowPeriod = slowPeriod;
      this.signalPeriod = signalPeriod;
      this.quotes = quotes;
   }

   @Override
   public BigDecimal calculate() {
      List<Quote> macdQuotes = new ArrayList<>();
      MACD macd;
      BigDecimal macdValue;
      Quote macdQuote;
      //Calculate MACD of every quote and store in macdQuotes, then calculate EMA(signalPeriod)
      for(int i= quotes.size() - slowPeriod; i >= 0; i--){
         macd = new MACD( fastPeriod, slowPeriod, quotes.subList( i, quotes.size() ) );
         macdValue = macd.calculate();
         macdQuote = new Quote();
         macdQuote.setClose( macdValue );
         //Quotes always must be in descending order
         macdQuotes.add( 0, macdQuote );
      }
      
      ExponentialMovingAverage ema = new ExponentialMovingAverage( signalPeriod, macdQuotes );
      
      return ema.calculate();
   }

}
