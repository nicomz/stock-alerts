package ar.com.sac.controllers;

import ar.com.sac.model.IStockWrapper;
import ar.com.sac.model.Quote;
import ar.com.sac.services.StockService;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@RestController
@RequestMapping("/stocks")
public class StocksController {
   
   @Autowired
   private StockService stockService;
   
   @RequestMapping(method = RequestMethod.GET)
   public IStockWrapper get(@RequestParam("symbol") String symbol ) throws IOException {
      return stockService.getStock( symbol );
   }
   
   @RequestMapping(value= "/history", method = RequestMethod.GET)
   public List<Quote> getHistory(@RequestParam("symbol") String symbol ) throws IOException {
      return stockService.getHistory( symbol );
   }

}
