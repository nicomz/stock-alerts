package ar.com.sac.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UtilTest {
   
 //desc order, APBR.BA to 04 ene 2017
   private static double[] prices = {86.45,85.2,79.9,82.2,82.2,81.2,79,78.9,78.7,78.1,79.95,78.5,77.9,81,82.6,83.55,87.35,87.9,
                       86.35,86.6,84.2,85.35,84.25,86.65,78.1,80.25,82.05,81.45,82,79.75,76.25,75.75,77.5,79.2,
                       75.2,72.65,77.3};
   
   private static List<Quote> quotes = new ArrayList<Quote>();
   
   public static List<Quote> getQuotes(){
      if(quotes.size() > 0){
         return quotes;
      }
      
      generateQuotes();
      return quotes;
   }
   
   private static void generateQuotes(){
      Quote quote;
      for(int i = 0; i < prices.length; i++){
         quote = new Quote();
         quote.setClose( new BigDecimal(prices[i]) );
         quote.setId( new QuoteId("APBR.BA", null) );
         quotes.add( quote );
      }
   }

}
