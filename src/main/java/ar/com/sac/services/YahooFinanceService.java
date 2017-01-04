package ar.com.sac.services;

import java.io.IOException;
import java.util.List;
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

}
