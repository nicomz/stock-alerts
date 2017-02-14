package ar.com.sac.services;

import ar.com.sac.model.IStockWrapper;
import ar.com.sac.model.Quote;
import ar.com.sac.model.SimulatorStockWrapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * This is a "STUB" Service, useful for replacing real stockService in simulations controlling the quotes
 */
public class StockSimulatorService implements IStockService {
   
   private List<Quote> quotes = new ArrayList<>();

   public void setSimulationQuotes (List<Quote> quotes){
      this.quotes  = quotes;
   }

   @Override
   public IStockWrapper getStock( String symbol ) throws IOException {
      IStockWrapper stock = new SimulatorStockWrapper( symbol, quotes.get( 0 ) );
      return stock;
   }

   @Override
   public List<Quote> getHistory( String symbol ) throws IOException {
      return quotes;
   }

}
