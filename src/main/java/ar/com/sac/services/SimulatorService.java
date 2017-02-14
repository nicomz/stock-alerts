package ar.com.sac.services;

import ar.com.sac.model.simulator.SimulationResults;
import ar.com.sac.model.simulator.SimulatorParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application.properties")
public class SimulatorService {
   
   @Autowired
   private StockService stockService;
   @Autowired
   private ExpressionService expressionService;
   
   public SimulationResults runSimulation(SimulatorParameters parameters){
      return new Simulation( parameters, stockService, expressionService ).run();
   }

}
