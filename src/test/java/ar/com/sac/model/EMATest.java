package ar.com.sac.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;


public class EMATest {
   
   //desc order
   private double[] prices = {86.45,85.2,79.9,82.2,82.2,81.2,79,78.9,78.7,78.1,79.95,78.5,77.9,81,82.6,83.55,87.35,87.9,
                       86.35,86.6,84.2,85.35,84.25,86.65,78.1,80.25,82.05,81.45,82,79.75,76.25,75.75,77.5,79.2,
                       75.2,72.65,77.3};
   
   private List<Quote> quotes = new ArrayList<Quote>();
   
   private List<Quote> generateQuotes(){
      quotes = new ArrayList<Quote>();
      Quote quote;
      for(int i = 0; i < prices.length; i++){
         quote = new Quote();
         quote.setClose( new BigDecimal(prices[i]) );
         quote.setSymbol( "APBR.BA" );
         quotes.add( quote );
      }
      
      return quotes;
   }

   @Test
   public void EMA5test() {
      generateQuotes();
      ExponentialMovingAverage ema = new ExponentialMovingAverage( 5, quotes );
      Assert.assertEquals( "83.6273420702763843337379512377083301544189453125", ema.calculate().toString());
   }
   
   @Test
   public void EMA20test() {
      generateQuotes();
      ExponentialMovingAverage ema = new ExponentialMovingAverage( 20, quotes );
      Assert.assertEquals( "81.796756887220226417412050068378448486328125", ema.calculate().toString());
   }
   
   @Test
   public void EMA14test() {
      generateQuotes();
      ExponentialMovingAverage ema = new ExponentialMovingAverage( 14, quotes );
      Assert.assertEquals( "82.0812649626474097885875380598008632659912109375", ema.calculate().toString());
   }

}
