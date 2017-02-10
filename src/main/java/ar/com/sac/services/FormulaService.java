package ar.com.sac.services;

import ar.com.sac.model.Quote;
import ar.com.sac.model.formulas.ExponentialMovingAverage;
import ar.com.sac.model.formulas.MACD;
import ar.com.sac.model.formulas.MACDHistogram;
import ar.com.sac.model.formulas.MACDSignalLine;
import ar.com.sac.model.formulas.Price;
import ar.com.sac.model.formulas.RelativeStrengthIndex;
import ar.com.sac.model.formulas.SimpleMovingAverage;
import ar.com.sac.model.formulas.StochasticOscillatorD;
import ar.com.sac.model.formulas.StochasticOscillatorK;
import ar.com.sac.model.formulas.Volume;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormulaService {
   @Autowired
   private StockService stockService;
   
   public BigDecimal getSMA(int period, String symbol) throws IOException{
      List<Quote> quotes;
      quotes = stockService.getHistory( symbol );
      SimpleMovingAverage sma = new SimpleMovingAverage( period, quotes );
      return sma.calculate();
   }
   
   public BigDecimal getEMA(int period, String symbol) throws IOException{
      List<Quote> quotes;
      quotes = stockService.getHistory( symbol );
      ExponentialMovingAverage ema = new ExponentialMovingAverage( period, quotes );
      return ema.calculate();
   }
   
   public BigDecimal getRSI(int period, String symbol) throws IOException{
      List<Quote> quotes = stockService.getHistory( symbol );
      RelativeStrengthIndex rsi = new RelativeStrengthIndex( period, quotes );
      return rsi.calculate();
   }
   
   public BigDecimal getPrice( String symbol ) throws IOException{
      Quote quote = stockService.getStock( symbol ).getLastQuote();
      return new Price( quote ).calculate();
   }
   
   public BigDecimal getVolume( String symbol ) throws IOException{
      Quote quote = stockService.getStock( symbol ).getLastQuote();
      return new Volume( quote ).calculate();
   }
   
   public BigDecimal getMACD(int fastPeriod, int slowPeriod, String symbol) throws IOException{
      List<Quote> quotes = stockService.getHistory( symbol );
      MACD macd = new MACD( fastPeriod, slowPeriod, quotes );
      return macd.calculate();
   }
   
   public BigDecimal getMACDSignalLine(int fastPeriod, int slowPeriod, int signalPeriod, String symbol) throws IOException{
      List<Quote> quotes = stockService.getHistory( symbol );
      MACDSignalLine macdSignalLine = new MACDSignalLine( fastPeriod, slowPeriod, signalPeriod, quotes );
      return macdSignalLine.calculate();
   }
   
   public BigDecimal getMACDHistogram(int fastPeriod, int slowPeriod, int signalPeriod, String symbol) throws IOException{
      List<Quote> quotes = stockService.getHistory( symbol );
      MACDHistogram macdHistogram = new MACDHistogram( fastPeriod, slowPeriod, signalPeriod, quotes );
      return macdHistogram.calculate();
   }
   
   public BigDecimal getStochasticOscillatorD(int length, int period, String symbol) throws IOException{
      List<Quote> quotes = stockService.getHistory( symbol );
      StochasticOscillatorD osd = new StochasticOscillatorD( length, period, quotes );
      return osd.calculate();
   }
   
   public BigDecimal getStochasticOscillatorK(int length, String symbol) throws IOException{
      List<Quote> quotes = stockService.getHistory( symbol );
      StochasticOscillatorK osk = new StochasticOscillatorK( length, quotes );
      return osk.calculate();
   }
   

}
