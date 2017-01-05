package ar.com.sac.controllers;

import ar.com.sac.services.FormulaService;
import java.io.IOException;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@RestController
@RequestMapping("/formulas")
public class FormulaController {
   @Autowired
   private FormulaService formulaService;
   
   @RequestMapping(value= "/ema", method = RequestMethod.GET)
   public ResponseEntity<BigDecimal> getHistory(@RequestParam("period") int period, @RequestParam("symbol") String symbol ) throws IOException {
      return new ResponseEntity<BigDecimal>( formulaService.getEMA( period, symbol ), HttpStatus.OK );
   }
}
