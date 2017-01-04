package ar.com.sac.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class PingController {
   
   @RequestMapping(method = RequestMethod.GET)
   public ResponseEntity<String> ping() {
      return new ResponseEntity<String>( "stock-alerts ping!", HttpStatus.OK );
   }

}
