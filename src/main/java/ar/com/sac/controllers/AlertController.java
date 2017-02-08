package ar.com.sac.controllers;

import ar.com.sac.model.Alert;
import ar.com.sac.model.Notification;
import ar.com.sac.services.AlertService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
   public List<Alert> getAlerts( @RequestParam(value = "symbol", required=false) String symbol ) throws IOException {
      if(symbol == null){
         return alertService.getAlerts( false );
      }else{
         return alertService.getAlertsBySymbol( symbol );
      }
   }
   
   @RequestMapping(value="/{alertId}", method = RequestMethod.GET)
   public Alert getAlertById( @PathVariable("alertId") String alertId) throws IOException {
      return alertService.getAlertById( alertId );
   }
   
   //Using GET for be able to create a link to this action 
   @RequestMapping(value="/{alertId}/activate", method = RequestMethod.GET)
   public ResponseEntity<HttpStatus> activateAlert( @PathVariable("alertId") String alertId) throws IOException {
      alertService.activateAlert( alertId );
      return new ResponseEntity<HttpStatus> ( HttpStatus.OK );
   }
   
   //This action is by GET for be able to create a link to this action
   @RequestMapping(value="/{alertId}/deactivate", method = RequestMethod.GET)
   public ResponseEntity<HttpStatus> deactivateAlert( @PathVariable("alertId") String alertId) throws IOException {
      alertService.deactivateAlert( alertId );
      return new ResponseEntity<HttpStatus> ( HttpStatus.OK );
   }
   
   
   @RequestMapping(method = RequestMethod.POST)
   public ResponseEntity<HttpStatus> createAlert( @RequestBody Alert newAlert ) throws IOException {
      alertService.saveAlert( newAlert );
      return new ResponseEntity<HttpStatus> ( HttpStatus.OK );
   }
   
   @RequestMapping(method = RequestMethod.DELETE)
   public ResponseEntity<HttpStatus> deleteAlert(  @PathVariable("id") String alertId ) throws IOException {
      alertService.deleteAlertById(  alertId );
      return new ResponseEntity<HttpStatus> ( HttpStatus.OK );
   }
   
   @RequestMapping(method = RequestMethod.PUT)
   public ResponseEntity<HttpStatus> updateAlert( @RequestBody Alert alert ) throws IOException {
      alertService.updateAlert( alert );
      return new ResponseEntity<HttpStatus> ( HttpStatus.OK );
   }
   
   @RequestMapping(value= "/process", method = RequestMethod.GET)
   public List<Notification> process() throws IOException {
      return alertService.processAlerts();
   }

   @RequestMapping(value="/{alertId}/process", method = RequestMethod.GET)
   public List<Notification> processAlert( @PathVariable("alertId") String alertId) throws IOException {
      Alert alert = alertService.getAlertById( alertId );
      List<Alert> alerts = new ArrayList<>();
      alerts.add( alert );
      return alertService.processAlerts( alerts );
   }
}
