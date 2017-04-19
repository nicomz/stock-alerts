package ar.com.sac.services.dao;

import ar.com.sac.model.Quote;
import ar.com.sac.model.QuoteId;
import org.springframework.stereotype.Repository;

@Repository
public class QuoteDAO extends AbstractDAO<Quote, QuoteId> {

   public QuoteDAO( ) {
      super( Quote.class );
   }
   
}
