package ar.com.sac.model.formulas;

import ar.com.sac.model.Quote;
import java.math.BigDecimal;
import java.util.List;


public class BollingerBandLower implements Formula {
   
   private int period;
   private int k;
   private List<Quote> quotes;
   
   public BollingerBandLower( int period, int k, List<Quote> quotes){
      this.period = period;
      this.k = k;
      this.quotes = quotes;
   }

   @Override
   public BigDecimal calculate() {
      double sma = new SimpleMovingAverage( period, quotes ).calculate().doubleValue();
      double sd = new StandardDeviation( period, quotes ).calculate().doubleValue();
      return new BigDecimal( sma - (k * sd));
   }

}
