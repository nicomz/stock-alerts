package ar.com.sac.model;

import org.junit.Assert;
import org.junit.Test;


public class EMATest {

   @Test
   public void EMA5test() {
      ExponentialMovingAverage ema = new ExponentialMovingAverage( 5, UtilTest.getQuotes() );
      Assert.assertEquals( "83.6273420702763843337379512377083301544189453125", ema.calculate().toString());
   }
   
   @Test
   public void EMA20test() {
      ExponentialMovingAverage ema = new ExponentialMovingAverage( 20, UtilTest.getQuotes() );
      Assert.assertEquals( "81.796756887220226417412050068378448486328125", ema.calculate().toString());
   }
   
   @Test
   public void EMA14test() {
      ExponentialMovingAverage ema = new ExponentialMovingAverage( 14, UtilTest.getQuotes() );
      Assert.assertEquals( "82.0812649626474097885875380598008632659912109375", ema.calculate().toString());
   }

}
