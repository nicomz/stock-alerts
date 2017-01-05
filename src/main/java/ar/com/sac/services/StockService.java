package ar.com.sac.services;

import ar.com.sac.model.IStockWrapper;
import ar.com.sac.model.Quote;
import ar.com.sac.model.StockWrapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yahoofinance.histquotes.HistoricalQuote;

@Service
public class StockService {
   
   @Autowired
   private YahooFinanceService yahooFinanceService;

   public IStockWrapper getStock( String symbol ) throws IOException{
      return new StockWrapper( yahooFinanceService.getStock( symbol ) );
   }
   
   public List<Quote> getHistory( String symbol ) throws IOException{
      List<HistoricalQuote> history = yahooFinanceService.getHistory( symbol );
      List<Quote> quotes = new ArrayList<Quote>( history.size() );
      for(HistoricalQuote h : history){
         quotes.add( new Quote(h) );
      }
      return quotes;
   }
}
