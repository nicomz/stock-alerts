package ar.com.sac.services;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

@Service
public class YahooFinanceService {
   
   public Stock getStock( String symbol ) throws IOException{
      return YahooFinance.get( symbol, true );
   }
   
   /**
    * Returns a year of daily quotes in DESC order. It doesn't retrieve the last price
    * @param symbol
    * @return
    * @throws IOException
    */
   public List<HistoricalQuote> getHistory( String symbol ) throws IOException{
      Stock stock = YahooFinance.get( symbol );
      return stock.getHistory( Interval.DAILY );
   }
   
   /**
    * Returns a year of daily quotes in DESC order. It doesn't retrieve the last price
    * @param symbol
    * @return
    * @throws IOException
    */
   public List<HistoricalQuote> getHistory( String symbol, Calendar from, Calendar to ) throws IOException{
      Stock stock = YahooFinance.get( symbol, from, to, Interval.DAILY );
      return stock.getHistory( from, to, Interval.DAILY );
   }
   
   /**
    * Always returns a map with its keySet full of the symbols passed as parameters
    * @param symbols
    * @param from
    * @param to
    * @return
    * @throws IOException
    */
   public Map<String, List<HistoricalQuote>> getHistory( String[] symbols, Calendar from, Calendar to ) throws IOException{
      Map<String, Stock> map = YahooFinance.get( symbols, from, to, Interval.DAILY );
      Map<String, List<HistoricalQuote>> resultMap = new HashMap<String, List<HistoricalQuote>>();
      for(String symbol : symbols){
         Stock stock = map.get( symbol );
         resultMap.put( symbol, stock.getHistory( from, to, Interval.DAILY ) );
      }
      return resultMap;
   }

}
