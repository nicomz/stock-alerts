package ar.com.sac.services.dao;

import ar.com.sac.model.Quote;
import ar.com.sac.model.QuoteId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

@Repository
public class QuoteDAO extends AbstractDAO<Quote, QuoteId> {

   public QuoteDAO( ) {
      super( Quote.class );
   }

   public List<Quote> findByRange( String symbol, Calendar from, Calendar to ) {
      return findByRangeInBulk(new String[]{ symbol }, from, to).get( symbol );
   }

   public List<String> getLoadedSymbols() {
      String queryStr = "Select DISTINCT q.id.symbol From Quote q";
      queryStr += " ORDER BY q.id.symbol";
      return getEntityManager().createQuery(queryStr, String.class).getResultList();
   }

   public Map<String, List<Quote>> findByRangeInBulk( String[] symbols, Calendar from, Calendar to ) {
      Map<String, List<Quote>> resultMap = new HashMap<String, List<Quote>>();
      String queryStr = "Select q From Quote q";
      queryStr += " WHERE q.id.symbol IN :symbols" ;
      queryStr += " AND q.id.date >= :from AND q.id.date <= :to";
      queryStr += " ORDER BY q.id.date DESC";
      TypedQuery<Quote> query = getEntityManager().createQuery(queryStr, Quote.class);
      query.setParameter( "symbols", Arrays.asList( symbols ) );
      query.setParameter( "from", from );
      query.setParameter( "to", to );
      List<Quote> list = query.getResultList();
      for(Quote quote: list){
         putQuoteInMap( quote, resultMap );
      }
      return resultMap;
   }

   private void putQuoteInMap( Quote quote, Map<String, List<Quote>> map ) {
      List<Quote> internalList = map.get( quote.getSymbol() );
      if(internalList == null){
         internalList = new ArrayList<>();
         map.put( quote.getSymbol(), internalList );
      }
      internalList.add( quote );
   }
   
}
