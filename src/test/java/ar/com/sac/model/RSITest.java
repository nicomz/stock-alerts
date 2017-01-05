package ar.com.sac.model;

import org.junit.Assert;
import org.junit.Test;

public class RSITest {

   @Test
   public void RSI14test() {
      RelativeStrengthIndex rsi = new RelativeStrengthIndex( 14, UtilTest.getQuotes() );
      Assert.assertEquals( "59.0975561541093412643022020347416400909423828125", rsi.calculate().toString());
   }
   
}
