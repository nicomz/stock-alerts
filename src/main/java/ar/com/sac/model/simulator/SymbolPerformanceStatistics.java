package ar.com.sac.model.simulator;


public class SymbolPerformanceStatistics {
   private String symbol;
   private double performance;
   private int positiveSales;
   private int negativeSales;
   private int buyingOpportunitiesMissed;
   
   /**
    * @return the symbol
    */
   public synchronized String getSymbol() {
      return symbol;
   }
   
   /**
    * @param symbol the symbol to set
    */
   public synchronized void setSymbol( String symbol ) {
      this.symbol = symbol;
   }
   
   /**
    * @return the performance
    */
   public synchronized double getPerformance() {
      return performance;
   }
   
   /**
    * @param performance the performance to set
    */
   public synchronized void setPerformance( double performance ) {
      this.performance = performance;
   }

   
   /**
    * @return the positiveSales
    */
   public synchronized int getPositiveSales() {
      return positiveSales;
   }

   
   /**
    * @return the negativeSales
    */
   public synchronized int getNegativeSales() {
      return negativeSales;
   }
   
   public synchronized void incPositiveSales(){
      positiveSales++;
   }
   
   public synchronized void incNegativeSales(){
      negativeSales++;
   }

   
   /**
    * @return the buyingOpportunitiesMissed
    */
   public synchronized int getBuyingOpportunitiesMissed() {
      return buyingOpportunitiesMissed;
   }
   
   public synchronized void incBuyingOpportunitiesMissed(){
      buyingOpportunitiesMissed++;
   }
   
   
}
