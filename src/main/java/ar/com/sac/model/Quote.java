package ar.com.sac.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.quotes.stock.StockQuote;

@Entity
public class Quote {
   
   @EmbeddedId
   private QuoteId id;
   
   private BigDecimal open;
   private BigDecimal low;
   private BigDecimal high;
   private BigDecimal close;
   
   private Long volume;
   
   public Quote(){
   }
   
   public Quote(StockQuote stockQuote){
      setId( new QuoteId(stockQuote.getSymbol(), stockQuote.getLastTradeTime()));
      this.open = stockQuote.getOpen();
      this.low = stockQuote.getDayLow();
      this.high = stockQuote.getDayHigh();
      //the current quote has no close price
      this.close = stockQuote.getPrice();
      this.volume = stockQuote.getVolume();
   }
   
   public Quote(HistoricalQuote historicalQuote){
      setId( new QuoteId(historicalQuote.getSymbol(), historicalQuote.getDate()));
      this.open = historicalQuote.getOpen();
      this.low = historicalQuote.getLow();
      this.high = historicalQuote.getHigh();
      this.close = historicalQuote.getClose();
      this.volume = historicalQuote.getVolume();
   }
   
   @JsonSerialize(using = CalendarSerializer.class)
   public Calendar getDate(){
      return id.getDate();
   }

   
   public synchronized String getSymbol() {
      return id.getSymbol();
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

   public QuoteId getId() {
      return id;
   }

   public void setId( QuoteId id ) {
      this.id = id;
   }
   
}
