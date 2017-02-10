package ar.com.sac.model.formulas;

import ar.com.sac.model.Quote;
import java.math.BigDecimal;


/**
 * Price represents current stock price as a formula, i.e. PRICE(FRAN.BA)
 * @author Sergio Cormio
 *
 */
public class Price implements Formula {
   
   private Quote quote;

   public Price( Quote quote ){
      this.quote = quote;
   }

   public BigDecimal calculate() {
      return quote.getClose();
   }

}
