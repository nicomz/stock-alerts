package ar.com.sac.model.formulas;

import ar.com.sac.model.Quote;
import java.math.BigDecimal;


public class Volume implements Formula {
   
   private Quote quote;

   public Volume( Quote quote ){
      this.quote = quote;
   }


   @Override
   public BigDecimal calculate() {
      return new BigDecimal( quote.getVolume() );
   }

}
