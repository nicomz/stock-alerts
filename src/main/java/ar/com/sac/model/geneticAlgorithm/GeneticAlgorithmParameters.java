package ar.com.sac.model.geneticAlgorithm;

import ar.com.sac.model.simulator.SimulatorParameters;

public class GeneticAlgorithmParameters {
   
   private SimulatorParameters simulatorParameters;
   private int populationSize;
   private int numberOfEvolutions;

   public GeneticAlgorithmParameters(){
      simulatorParameters = SimulatorParameters.createDefault();
      populationSize = 500;
      numberOfEvolutions = 200;
   }
   /**
    * @return the simulatorParameters
    */
   public synchronized SimulatorParameters getSimulatorParameters() {
      return simulatorParameters;
   }

   
   /**
    * @param simulatorParameters the simulatorParameters to set
    */
   public synchronized void setSimulatorParameters( SimulatorParameters simulatorParameters ) {
      this.simulatorParameters = simulatorParameters;
   }

   
   /**
    * @return the populationSize
    */
   public synchronized int getPopulationSize() {
      return populationSize;
   }

   
   /**
    * @param populationSize the populationSize to set
    */
   public synchronized void setPopulationSize( int populationSize ) {
      this.populationSize = populationSize;
   }
   public int getNumberOfEvolutions() {
      return numberOfEvolutions;
   }
   public void setNumberOfEvolutions( int numberOfEvolutions ) {
      this.numberOfEvolutions = numberOfEvolutions;
   }
   
   

}
