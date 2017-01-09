package ar.com.sac.model;

import java.io.Serializable;

public interface IStockWrapper extends Serializable {
   
   public String getSymbol();
   
   public Quote getLastQuote();
   
}
