package ar.com.sac.services;

import ar.com.sac.model.IStockWrapper;
import ar.com.sac.model.Quote;
import ar.com.sac.model.StockWrapper;
import ar.com.sac.services.dao.QuoteDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yahoofinance.Stock;
import yahoofinance.histquotes.HistoricalQuote;

@Service
@EnableScheduling
@PropertySource("classpath:application.properties")
@Transactional
public class StockService implements IStockService{
   
   @Autowired
   private QuoteDAO quoteDAO;
   
   @Autowired
   private YahooFinanceService yahooFinanceService;
   
   @Value("${stocks.db.enable}")
   private Boolean usingDB;

   public IStockWrapper getStock( String symbol ) throws IOException{
      return new StockWrapper( yahooFinanceService.getStock( symbol ) );
   }
   
   public Map<String,IStockWrapper> getStocks( String[] symbols ) throws IOException{
      Map<String,IStockWrapper> resultMap = new HashMap<>();
      Map<String, Stock> yahooMap = yahooFinanceService.getStocks( symbols );
      for(String symbol :  yahooMap.keySet()){
         resultMap.put( symbol, new StockWrapper( yahooMap.get( symbol ) ) );
      }
      return resultMap;
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
      List<Quote> quotes = quoteDAO.findByRange( symbol, from, to );
      
      if( usingDB ){
         quotes = quoteDAO.findByRange( symbol, from, to );
      }else{
         List<HistoricalQuote> history = yahooFinanceService.getHistory( symbol, from, to );
         quotes = historyToQuotes( history );
      }

      //take the last quote from current day
      Quote todayQuote = new Quote(yahooFinanceService.getStock( symbol ).getQuote());
      if(quotes.size() == 0 || quotes.get( 0 ).getDate().get( Calendar.DATE ) != todayQuote.getDate().get( Calendar.DATE )){
         quotes.add( 0, todayQuote );
      }
      
      return quotes;
   }
   
   public  Map<String, List<Quote>> getHistory( String[] symbols, Calendar from, Calendar to ) throws IOException{
      Map<String, List<Quote>> resultMap = new HashMap<>();
      if( usingDB ){
         resultMap = quoteDAO.findByRangeInBulk( symbols, from, to );
      }else{
         Map<String, List<HistoricalQuote>> historyMap = yahooFinanceService.getHistory( symbols, from, to );
         for( String symbol : historyMap.keySet() ){
            resultMap.put( symbol, historyToQuotes( historyMap.get( symbol ) ));
         }
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
   
   @Transactional
   public void importQuotes(Collection<Quote> quotes){
      for(Quote quote : quotes){
         quoteDAO.update( quote );
      }
   }
   
   public List<String> getSymbols(){
      return quoteDAO.getLoadedSymbols();
   }
   
   /**
    * Update DB with last quote of every ticker loaded
    */
   @Scheduled(cron = "${stocks.dailyUpdateDB.cron}")
   public void updateDBJob(){
      System.out.println( "Update DataBase JOB: " + new Date() );
      List<String> symbols = getSymbols();
      try {
         Map<String, IStockWrapper> map = getStocks( symbols.toArray( new String[symbols.size()] ) );
         for(IStockWrapper stock : map.values()){
            quoteDAO.update( stock.getLastQuote() );
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
