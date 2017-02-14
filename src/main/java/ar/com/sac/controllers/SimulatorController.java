package ar.com.sac.controllers;

import ar.com.sac.model.simulator.SimulationResults;
import ar.com.sac.model.simulator.SimulatorParameters;
import ar.com.sac.services.SimulatorService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@RestController
@RequestMapping("/simulator")
public class SimulatorController {
   
   @Autowired
   private SimulatorService simulatorService;
   
   @RequestMapping(method = RequestMethod.POST)
   public ResponseEntity<SimulationResults> runSimulation( @RequestBody SimulatorParameters parameters ) throws IOException {
      return new ResponseEntity<SimulationResults>( simulatorService.runSimulation( parameters ), HttpStatus.OK );
   }

}
