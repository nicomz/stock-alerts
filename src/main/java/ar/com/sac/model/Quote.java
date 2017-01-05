package ar.com.sac.model;

import java.math.BigDecimal;
import java.util.Calendar;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.quotes.stock.StockQuote;

public class Quote {
   
   private String symbol;
   
   private Calendar date;
   
   private BigDecimal open;
   private BigDecimal low;
   private BigDecimal high;
   private BigDecimal close;
   
   private Long volume;
   
   public Quote(){
   }
   
   public Quote(StockQuote stockQuote){
      this.symbol = stockQuote.getSymbol();
      this.date = stockQuote.getLastTradeTime();
      this.open = stockQuote.getOpen();
      this.low = stockQuote.getDayLow();
      this.high = stockQuote.getDayHigh();
      //the current quote has no close price
      this.close = stockQuote.getPrice();
      this.volume = stockQuote.getVolume();
   }
   
   public Quote(HistoricalQuote historicalQuote){
      this.symbol = historicalQuote.getSymbol();
      this.date = historicalQuote.getDate();
      this.open = historicalQuote.getOpen();
      this.low = historicalQuote.getLow();
      this.high = historicalQuote.getHigh();
      this.close = historicalQuote.getClose();
      this.volume = historicalQuote.getVolume();
   }
   
   public Calendar getDate(){
      return date;
   }

   
   public synchronized String getSymbol() {
      return symbol;
   }

   
   public synchronized BigDecimal getOpen() {
      return open;
   }

   
   public synchronized BigDecimal getLow() {
      return low;
   }

   
   public synchronized BigDecimal getHigh() {
      return high;
   }

   
   public synchronized BigDecimal getClose() {
      return close;
   }

   
   public synchronized Long getVolume() {
      return volume;
   }

   
   public synchronized void setSymbol( String symbol ) {
      this.symbol = symbol;
   }

   
   public synchronized void setDate( Calendar date ) {
      this.date = date;
   }

   
   public synchronized void setOpen( BigDecimal open ) {
      this.open = open;
   }

   
   public synchronized void setLow( BigDecimal low ) {
      this.low = low;
   }

   
   public synchronized void setHigh( BigDecimal high ) {
      this.high = high;
   }

   
   public synchronized void setClose( BigDecimal close ) {
      this.close = close;
   }

   
   public synchronized void setVolume( Long volume ) {
      this.volume = volume;
   }
   
}
