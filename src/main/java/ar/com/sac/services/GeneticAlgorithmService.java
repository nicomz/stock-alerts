package ar.com.sac.services;

import ar.com.sac.model.geneticAlgorithm.ChromosomeTranslator;
import ar.com.sac.model.geneticAlgorithm.GeneticAlgorithmParameters;
import ar.com.sac.model.geneticAlgorithm.GeneticAlgorithmResults;
import ar.com.sac.model.geneticAlgorithm.SimulationFitnessFunction;
import org.jgap.Configuration;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application.properties")
public class GeneticAlgorithmService {
   
   @Autowired
   private SimulatorService simulatorService;

   public GeneticAlgorithmResults runGeneticAlgorithm(GeneticAlgorithmParameters parameters) throws InvalidConfigurationException{
      // Start with a DefaultConfiguration, which comes setup with the
      // most common settings.
      // -------------------------------------------------------------
      Configuration conf = new DefaultConfiguration();
      
   // Care that the fittest individual of the current population is 
      // always taken to the next generation. 
      // Consider: With that, the pop. size may exceed its original 
      // size by one sometimes! 
      // ------------------------------------------------------------- 
      conf.setPreservFittestIndividual(true); 

      ChromosomeTranslator chromosomeTranslator = new ChromosomeTranslator( conf );

      // Set the fitness function we want to use, which is our
      // MinimizingMakeChangeFitnessFunction that we created earlier.
      // We construct it with the target amount of change provided
      // by the user.
      // ------------------------------------------------------------
      SimulationFitnessFunction fitnessFunction = new SimulationFitnessFunction( chromosomeTranslator, parameters.getSimulatorParameters(), simulatorService );
      conf.setFitnessFunction( fitnessFunction );
      

      conf.setSampleChromosome( chromosomeTranslator.getSampleChromosome() );

      // Finally, we need to tell the Configuration object how many
      // Chromosomes we want in our population. The more Chromosomes,
      // the larger the number of potential solutions (which is good
      // for finding the answer), but the longer it will take to evolve
      // the population each round. We'll set the population size to
      // 500 here.
      // --------------------------------------------------------------
      conf.setPopulationSize( parameters.getPopulationSize() );
      
      Genotype population = Genotype.randomInitialGenotype( conf );
      
      
      
      long startTime = System.currentTimeMillis(); 
      for (int i = 0; i < parameters.getNumberOfEvolutions(); i++) {
//          printGeneration(i, population, chromosomeTranslator);
          population.evolve(); 
      } 
      long endTime = System.currentTimeMillis(); 
      System.out.println("Total evolution time: " + (endTime - startTime) + " ms"); 

      IChromosome bestSolution = population.getFittestChromosome();
      System.out.println("--------------------------");
      System.out.println( "Best Performance: $" + bestSolution.getFitnessValue() );
      System.out.println("Best buy Expression: " + chromosomeTranslator.getBuyExpression( bestSolution ));
      System.out.println("Best sell Expression: " + chromosomeTranslator.getSellExpression( bestSolution ));
      
      GeneticAlgorithmResults results = new GeneticAlgorithmResults();
      results.setTotalTime( endTime - startTime );
      //Run simulation one more time with best solution in order to get results
      results.setBestSimulationResults( fitnessFunction.getSimulationResults( bestSolution ) );
      results.setBestBuyExpression( chromosomeTranslator.getBuyExpression( bestSolution ) );
      results.setBestSellExpression( chromosomeTranslator.getSellExpression( bestSolution ) );
      return results;
   }

   private void printGeneration( int iteration, Genotype population, ChromosomeTranslator chromosomeTranslator) {
      System.out.println("------------Generation " + iteration +"--------------");
      System.out.println( "Population: " );
      int i = 1;
      for(IChromosome chromosome : population.getPopulation().getChromosomes()){
         System.out.println( "Chromosome number " + i + " :" );
         System.out.println( "Performance: $" + chromosome.getFitnessValue() );
         System.out.println("Buy Expression: " + chromosomeTranslator.getBuyExpression( chromosome ));
         System.out.println("Sell Expression: " + chromosomeTranslator.getSellExpression( chromosome ));
         i++;
      }
      IChromosome bestSolution = population.getFittestChromosome();
      System.out.println( "\nBest Chromosome in Population: " );
      System.out.println( "Best Performance: $" + bestSolution.getFitnessValue() );
      System.out.println("Best buy Expression: " + chromosomeTranslator.getBuyExpression( bestSolution ));
      System.out.println("Best sell Expression: " + chromosomeTranslator.getSellExpression( bestSolution ));
   }
   
   

}
