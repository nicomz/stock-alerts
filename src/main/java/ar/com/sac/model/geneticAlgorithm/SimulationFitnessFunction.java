package ar.com.sac.model.geneticAlgorithm;

import ar.com.sac.model.simulator.SimulationResults;
import ar.com.sac.model.simulator.SimulatorParameters;
import ar.com.sac.services.SimulatorService;
import org.jgap.FitnessFunction;
import org.jgap.IChromosome;


public class SimulationFitnessFunction extends FitnessFunction {

   private static final long serialVersionUID = -5525105921268112168L;
   private SimulatorParameters simulationParameters;
   private SimulatorService simulatorService;
   private ChromosomeTranslator chromosomeTranslator;
   
   public SimulationFitnessFunction( ChromosomeTranslator chromosomeTranslator, SimulatorParameters simulationParameters, SimulatorService simulatorService ){
      this.chromosomeTranslator = chromosomeTranslator;
      this.simulationParameters = simulationParameters;
      this.simulatorService = simulatorService;
   }

   /**
    * It must always return a positive number 
    */
   @Override
   protected double evaluate( IChromosome  chromosome  ) {
      return getSimulationResults( chromosome ).getFinalCapitalBalance();
   }

   public SimulationResults getSimulationResults( IChromosome chromosome ) {
      simulationParameters.setBuyExpression( chromosomeTranslator.getBuyExpression( chromosome ) );
      simulationParameters.setSellExpression( chromosomeTranslator.getSellExpression( chromosome ) );
      return simulatorService.runSimulation( simulationParameters );
      
   }

}
