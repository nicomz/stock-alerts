package ar.com.sac.controllers;

import ar.com.sac.services.EmailService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@RestController
@RequestMapping("/emails")
public class EmailController {
   
   @Autowired
   private EmailService emailService;
   
   @RequestMapping(value= "/test", method = RequestMethod.GET)
   public ResponseEntity<HttpStatus> sendTestEmail() throws IOException {
      HttpStatus status = HttpStatus.OK;
      try {
         emailService.generateAndSendTestEmail();
      } catch (Exception e) {
         status = HttpStatus.INTERNAL_SERVER_ERROR;
         e.printStackTrace();
      }
      return new ResponseEntity<HttpStatus> (status );
   }

}
