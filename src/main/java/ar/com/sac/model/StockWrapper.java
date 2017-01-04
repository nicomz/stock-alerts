package ar.com.sac.model;

import yahoofinance.Stock;

public class StockWrapper implements IStockWrapper {
   /**
    * 
    */
   private static final long serialVersionUID = 1737205892976188475L;
   private Stock stock;

   public StockWrapper( Stock stock ){
      this.stock = stock;
   }

   public String getSymbol(){
      return stock.getSymbol();
   }

   
   public Stock getStock() {
      return stock;
   }
   
}
