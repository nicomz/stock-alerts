package ar.com.sac.services;

import ar.com.sac.model.simulator.SimulatorParameters;
import ar.com.sac.model.simulator.SimulatorResults;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application.properties")
public class SimulatorService {
   
   public SimulatorResults runSimulation(SimulatorParameters parameters){
      return new SimulatorResults();
   }

}
