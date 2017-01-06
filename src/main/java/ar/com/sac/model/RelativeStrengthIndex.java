package ar.com.sac.model;

import java.math.BigDecimal;
import java.util.List;

public class RelativeStrengthIndex implements Formula {
   private int period;
   private List<Quote> quotes;

   public RelativeStrengthIndex( int period, List<Quote> quotes ){
      this.period = period;
      this.quotes = quotes;
   }

   public BigDecimal calculate() {
      double avgGain = 0;
      double avgLoss = 0;
      double change = 0;
      Quote q1,q2;
      int j = 0;
      
      for(int i = quotes.size() - 1 ; i > quotes.size() - period -1  ; i--){
         j = i-1;
         q1 = quotes.get( i );
         q2 = quotes.get( j );
         change = q2.getClose().doubleValue() - q1.getClose().doubleValue();
         if(change < 0){
            avgLoss += change*-1;
         }else{
            avgGain += change;
         }
      }
      avgGain = avgGain / period;
      avgLoss = avgLoss / period;
      
      for(int i = quotes.size() - period -1 ; i >= 1  ; i--){
         j = i-1;
         q1 = quotes.get( i );
         q2 = quotes.get( j );
         change = q2.getClose().doubleValue() - q1.getClose().doubleValue();
         //loss
         if(change < 0){
            avgLoss = (avgLoss * (period -1) + (change*-1))/period;
            avgGain = (avgGain * (period -1))/period;
         }else{ //gain
            avgLoss = (avgLoss * (period -1))/period;
            avgGain = (avgGain * (period -1) + change)/period;
         }
      }
      //RS = avgGain / avgLoss
      //RSI = 100 - (100 / (1 + RS))
      if(avgLoss == 0){
         return new BigDecimal( 100 );
      }
      double rs = avgGain / avgLoss;
      double rsi = 100d - (100d / (1 + rs));
      
      return new BigDecimal( rsi );
   }

}
