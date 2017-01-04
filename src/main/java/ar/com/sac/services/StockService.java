package ar.com.sac.services;

import ar.com.sac.model.IStockWrapper;
import ar.com.sac.model.StockWrapper;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {
   
   @Autowired
   private YahooFinanceService yahooFinanceService;

   public IStockWrapper getStock( String symbol ) throws IOException{
      return new StockWrapper( yahooFinanceService.getStock( symbol ) );
   }
}
