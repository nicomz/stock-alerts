package ar.com.sac.model.geneticAlgorithm;

import ar.com.sac.model.simulator.SimulationResults;

public class GeneticAlgorithmResults {
   
   private String bestBuyExpression;
   private String bestSellExpression;
   private SimulationResults bestSimulationResults;
   private long totalTime; //in ms
   
   /**
    * @return the bestBuyExpression
    */
   public synchronized String getBestBuyExpression() {
      return bestBuyExpression;
   }
   
   /**
    * @param bestBuyExpression the bestBuyExpression to set
    */
   public synchronized void setBestBuyExpression( String bestBuyExpression ) {
      this.bestBuyExpression = bestBuyExpression;
   }
   
   /**
    * @return the bestSellExpression
    */
   public synchronized String getBestSellExpression() {
      return bestSellExpression;
   }
   
   /**
    * @param bestSellExpression the bestSellExpression to set
    */
   public synchronized void setBestSellExpression( String bestSellExpression ) {
      this.bestSellExpression = bestSellExpression;
   }
   
   /**
    * @return the totalTime
    */
   public synchronized long getTotalTime() {
      return totalTime;
   }

   
   /**
    * @param totalTime the totalTime to set
    */
   public synchronized void setTotalTime( long totalTime ) {
      this.totalTime = totalTime;
   }

   
   /**
    * @return the bestSimulationResults
    */
   public synchronized SimulationResults getBestSimulationResults() {
      return bestSimulationResults;
   }

   
   /**
    * @param bestSimulationResults the bestSimulationResults to set
    */
   public synchronized void setBestSimulationResults( SimulationResults bestSimulationResults ) {
      this.bestSimulationResults = bestSimulationResults;
   }
   
}
