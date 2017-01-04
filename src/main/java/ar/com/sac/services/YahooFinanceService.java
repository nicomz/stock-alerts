package ar.com.sac.services;

import java.io.IOException;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

@Service
public class YahooFinanceService {
   
   public Stock getStock( String ticker ) throws IOException{
      return YahooFinance.get( ticker );
   }

}
