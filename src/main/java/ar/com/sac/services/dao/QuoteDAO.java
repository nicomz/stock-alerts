package ar.com.sac.services.dao;

import ar.com.sac.model.Quote;
import ar.com.sac.model.QuoteId;
import java.util.Calendar;
import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

@Repository
public class QuoteDAO extends AbstractDAO<Quote, QuoteId> {

   public QuoteDAO( ) {
      super( Quote.class );
   }

   public List<Quote> findByRange( String symbol, Calendar from, Calendar to ) {
      String queryStr = "Select q From Quote q";
      queryStr += " WHERE q.id.symbol = :symbol" ;
      queryStr += " AND q.id.date >= :from AND q.id.date <= :to";
      queryStr += " ORDER BY q.id.date DESC";
      TypedQuery<Quote> query = getEntityManager().createQuery(queryStr, Quote.class);
      query.setParameter( "symbol", symbol );
      query.setParameter( "from", from );
      query.setParameter( "to", to );
      return query.getResultList();
   }

   public List<String> getLoadedSymbols() {
      String queryStr = "Select DISTINCT q.id.symbol From Quote q";
      queryStr += " ORDER BY q.id.symbol";
      return getEntityManager().createQuery(queryStr, String.class).getResultList();
   }
   
}
