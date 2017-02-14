package ar.com.sac.services;

import ar.com.sac.model.IStockWrapper;
import ar.com.sac.model.Quote;
import java.io.IOException;
import java.util.List;

public interface IStockService {
   
   public IStockWrapper getStock( String symbol ) throws IOException;
   
   public List<Quote> getHistory( String symbol )  throws IOException;

}
