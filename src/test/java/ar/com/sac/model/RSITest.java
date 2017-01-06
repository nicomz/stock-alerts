package ar.com.sac.model;

import org.junit.Assert;
import org.junit.Test;

public class RSITest {

   @Test
   public void RSI14test() {
      RelativeStrengthIndex rsi = new RelativeStrengthIndex( 14, UtilTest.getQuotes() );
      Assert.assertEquals( "61.11842658704150466064675129018723964691162109375", rsi.calculate().toString());
   }
   
}
