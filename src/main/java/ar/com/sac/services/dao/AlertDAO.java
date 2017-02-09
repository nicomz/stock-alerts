package ar.com.sac.services.dao;

import ar.com.sac.model.Alert;
import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

@Repository
public class AlertDAO extends AbstractDAO<Alert, String>{
   
   public AlertDAO( ) {
      super( Alert.class );
   }

   public List<Alert> getAlerts( boolean onlyActive){
      String query = "Select a From Alert a";
      if( onlyActive ){
         query += " WHERE active is true";
      }
      query += " ORDER BY a.symbol";
      return getEntityManager().createQuery(query, Alert.class).getResultList();
   }

   public List<Alert> getAlertsBySymbol( String symbol ) {
      String queryStr = "Select a From Alert a WHERE symbol = :symbol";
      TypedQuery<Alert> query = getEntityManager().createQuery(queryStr, Alert.class);
      query.setParameter( "symbol", symbol );
      return query.getResultList();
   }

}
