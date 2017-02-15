package ar.com.sac.model.simulator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SimulationResults {
   
   private double finalCapitalBalance;
   private double finalLiquity;
   private Collection<SymbolPerformanceStatistics> symbolPerformances;
   
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

   
   /**
    * @param finalLiquity the finalLiquity to set
    */
   public synchronized void setFinalLiquity( double finalLiquity ) {
      this.finalLiquity = finalLiquity;
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
