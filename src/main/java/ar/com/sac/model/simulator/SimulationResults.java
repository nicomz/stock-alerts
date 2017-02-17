package ar.com.sac.model.simulator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SimulationResults {
   
   private double finalCapitalBalance;
   private double finalLiquity;
   private double totalPerformance;
   private double totalPerformancePercentage;
   private double totalBuyingOpportunitiesMissed;
   private Collection<SymbolPerformanceStatistics> symbolPerformances;
   private int quantityOfOperations;
   
   private List<SimulatorRecord> records = new ArrayList<>();
   
   public void addRecord(SimulatorRecord record){
      records.add( record );
   }
   
   /**
    * @return the records
    */
   public synchronized List<SimulatorRecord> getRecords() {
      return records;
   }

   
   /**
    * @return the finalCapitalBalance
    */
   public synchronized double getFinalCapitalBalance() {
      return finalCapitalBalance;
   }

   
   /**
    * @param finalCapitalBalance the finalCapitalBalance to set
    */
   public synchronized void setFinalCapitalBalance( double finalCapitalBalance ) {
      this.finalCapitalBalance = finalCapitalBalance;
   }

   
   /**
    * @return the finalLiquity
    */
   public synchronized double getFinalLiquity() {
      return finalLiquity;
   }
   
   public int getQuantityOfOperations() {
      return quantityOfOperations;
   }

   public void setQuantityOfOperations( int quantityOfOperations ) {
      this.quantityOfOperations = quantityOfOperations;
   }

   
   /**
    * @return the totalPerformance
    */
   public synchronized double getTotalPerformance() {
      return totalPerformance;
   }

   
   /**
    * @param totalPerformance the totalPerformance to set
    */
   public synchronized void setTotalPerformance( double totalPerformance ) {
      this.totalPerformance = totalPerformance;
   }

   
   /**
    * @return the totalPerformancePercentage
    */
   public synchronized double getTotalPerformancePercentage() {
      return totalPerformancePercentage;
   }

   
   /**
    * @param totalPerformancePercentage the totalPerformancePercentage to set
    */
   public synchronized void setTotalPerformancePercentage( double totalPerformancePercentage ) {
      this.totalPerformancePercentage = totalPerformancePercentage;
   }

   
   /**
    * @param finalLiquity the finalLiquity to set
    */
   public synchronized void setFinalLiquity( double finalLiquity ) {
      this.finalLiquity = finalLiquity;
   }
   
   /**
    * @return the totalBuyingOpportunitiesMissed
    */
   public synchronized double getTotalBuyingOpportunitiesMissed() {
      return totalBuyingOpportunitiesMissed;
   }

   
   /**
    * @param totalBuyingOpportunitiesMissed the totalBuyingOpportunitiesMissed to set
    */
   public synchronized void setTotalBuyingOpportunitiesMissed( double totalBuyingOpportunitiesMissed ) {
      this.totalBuyingOpportunitiesMissed = totalBuyingOpportunitiesMissed;
   }

   
   public synchronized void setSymbolPerformances( Collection<SymbolPerformanceStatistics> values ) {
      symbolPerformances = values;
   }

   /**
    * @return the symbolPerformances
    */
   public synchronized Collection<SymbolPerformanceStatistics> getSymbolPerformances() {
      return symbolPerformances;
   }

   

}
