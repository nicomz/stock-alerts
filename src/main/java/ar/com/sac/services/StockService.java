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
   
   /**
    * Return a year of quotes from a symbol
    */
   public List<Quote> getHistory( String symbol ) throws IOException{
      Calendar yearAgo = Calendar.getInstance();
      yearAgo.add( Calendar.YEAR, -1 );
      return getHistory( symbol, yearAgo, Calendar.getInstance() );
   }
   
   public List<Quote> getHistory( String symbol, Calendar from, Calendar to ) throws IOException{
      List<HistoricalQuote> history = yahooFinanceService.getHistory( symbol, from, to );
      List<Quote> quotes = historyToQuotes( history );

      //take the last quote from current day
      Quote todayQuote = new Quote(yahooFinanceService.getStock( symbol ).getQuote());
      if(quotes.get( 0 ).getDate().get( Calendar.DATE ) != todayQuote.getDate().get( Calendar.DATE )){
         quotes.add( 0, todayQuote );
      }
      
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
