package ar.com.sac.controllers;

import ar.com.sac.services.YahooFinanceService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import yahoofinance.Stock;

@RestController
@RequestMapping("/stocks")
public class StocksController {
   
   @Autowired
   private YahooFinanceService stockService;
   
   @RequestMapping(value="{ticker}", method = RequestMethod.GET)
   public Stock get(@PathVariable("ticker") String ticker ) throws IOException {
      return stockService.getStock( ticker );
   }

}
