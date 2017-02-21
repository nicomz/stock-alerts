package ar.com.sac.services;

import ar.com.sac.model.IStockWrapper;
import ar.com.sac.model.Quote;
import ar.com.sac.model.StockWrapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yahoofinance.histquotes.HistoricalQuote;

@Service
public class StockService implements IStockService{
   
   @Autowired
   private YahooFinanceService yahooFinanceService;

   public IStockWrapper getStock( String symbol ) throws IOException{
      return new StockWrapper( yahooFinanceService.getStock( symbol ) );
   }
   
   public List<Quote> getHistory( String symbol ) throws IOException{
      List<HistoricalQuote> history = yahooFinanceService.getHistory( symbol );
      List<Quote> quotes = historyToQuotes( history );
      return quotes;
   }
   
   public List<Quote> getHistory( String symbol, Calendar from, Calendar to ) throws IOException{
      List<HistoricalQuote> history = yahooFinanceService.getHistory( symbol, from, to );
      List<Quote> quotes = historyToQuotes( history );
      return quotes;
   }
   
   public  Map<String, List<Quote>> getHistory( String[] symbols, Calendar from, Calendar to ) throws IOException{
      Map<String, List<HistoricalQuote>> historyMap = yahooFinanceService.getHistory( symbols, from, to );
      Map<String, List<Quote>> resultMap = new HashMap<>();
      for( String symbol : historyMap.keySet() ){
         resultMap.put( symbol, historyToQuotes( historyMap.get( symbol ) ));
      }
      return resultMap;
   }

   private List<Quote> historyToQuotes( List<HistoricalQuote> history ) {
      List<Quote> quotes = new ArrayList<Quote>( history.size() );
      for(HistoricalQuote h : history){
         quotes.add( new Quote(h) );
      }
      return quotes;
   }
}
