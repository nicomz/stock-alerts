package ar.com.sac.model;

import ar.com.sac.model.formulas.ExponentialMovingAverage;
import org.junit.Assert;
import org.junit.Test;


public class EMATest {

   @Test
   public void EMA5test() {
      ExponentialMovingAverage ema = new ExponentialMovingAverage( 5, UtilTest.getQuotes() );
      Assert.assertEquals( "83.627340567127163240002118982374668121337890625", ema.calculate().toString());
   }
   
   @Test
   public void EMA20test() {
      ExponentialMovingAverage ema = new ExponentialMovingAverage( 20, UtilTest.getQuotes() );
      Assert.assertEquals( "81.5510759271945886439425521530210971832275390625", ema.calculate().toString());
   }
   
   @Test
   public void EMA14test() {
      ExponentialMovingAverage ema = new ExponentialMovingAverage( 14, UtilTest.getQuotes() );
      Assert.assertEquals( "82.0432817571926733535292441956698894500732421875", ema.calculate().toString());
   }

}
