package ar.com.sac.services;

import ar.com.sac.model.ExponentialMovingAverage;
import ar.com.sac.model.Quote;
import ar.com.sac.model.RelativeStrengthIndex;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormulaService {
   @Autowired
   private StockService stockService;
   
   public BigDecimal getEMA(int period, String symbol) throws IOException{
      List<Quote> quotes;
      quotes = stockService.getHistory( symbol );
      ExponentialMovingAverage ema = new ExponentialMovingAverage( period, quotes );
      return ema.calculate();
   }
   
   public BigDecimal getRSI(int period, String symbol) throws IOException{
      List<Quote> quotes;
      quotes = stockService.getHistory( symbol );
      RelativeStrengthIndex rsi = new RelativeStrengthIndex( period, quotes );
      return rsi.calculate();
   }
   
   public BigDecimal getPrice( String symbol ) throws IOException{
      Quote quote = stockService.getStock( symbol ).getLastQuote();
      return quote.getClose();
   }

}
