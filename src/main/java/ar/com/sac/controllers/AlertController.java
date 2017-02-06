package ar.com.sac.controllers;

import ar.com.sac.model.Alert;
import ar.com.sac.model.Notification;
import ar.com.sac.services.AlertService;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@RestController
@RequestMapping("/alerts")
public class AlertController {
   
   @Autowired
   private AlertService alertService;
   
   @RequestMapping(method = RequestMethod.GET)
   public List<Alert> getAlerts() throws IOException {
      return alertService.getAlerts( false );
   }
   
   @RequestMapping(method = RequestMethod.POST)
   public ResponseEntity<HttpStatus> createAlert( @RequestBody Alert newAlert ) throws IOException {
      alertService.saveAlert( newAlert );
      return new ResponseEntity<HttpStatus> ( HttpStatus.OK );
   }
   
   @RequestMapping(method = RequestMethod.DELETE)
   public ResponseEntity<HttpStatus> createAlert(  @RequestParam("id") String alertId ) throws IOException {
      alertService.deleteAlertById(  alertId );
      return new ResponseEntity<HttpStatus> ( HttpStatus.OK );
   }
   
   @RequestMapping(value= "/process", method = RequestMethod.GET)
   public List<Notification> process() throws IOException {
      return alertService.processAlerts();
   }

}
