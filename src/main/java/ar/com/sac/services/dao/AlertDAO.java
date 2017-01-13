package ar.com.sac.services.dao;

import ar.com.sac.model.Alert;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class AlertDAO extends AbstractDAO{
   
   public List<Alert> getAlerts(){
      /*List<Alert> alerts = new ArrayList<Alert>();
      //Create HARDCODED alerts
      Alert alert = new Alert();
      alert.setId( "MIRGOR BUY SIGNAL" );
      alert.setActive( true );
      alert.setSendEmail( false );
      alert.setName( "Buy signal - MIRG" );
      alert.setDescription( "MIRG has thrown a buy signal." );
      alert.setExpression( "EMA(5,MIRG.BA)>EMA(20,MIRG.BA)&&RSI(14,MIRG.BA)>50" );
      alerts.add( alert );
      //PRICE
      alert = new Alert();
      alert.setId( "MIRGOR TARGET SIGNAL" );
      alert.setActive( true );
      alert.setSendEmail( false );
      alert.setName( "PRICE OF MIRGOR ON TARGET" );
      alert.setDescription( "MIRG's price is too high. Sell signal." );
      alert.setExpression( "PRICE(MIRG.BA)>460" );
      alerts.add( alert );
      
      alert = new Alert();
      alert.setId( "SIDERAR RESISTANCE BROKEN" );
      alert.setActive( true );
      alert.setSendEmail( false );
      alert.setName( "PRICE OF SIDERAR RISING" );
      alert.setDescription( "ERAR's price has boken a RESISTANCE." );
      alert.setExpression( "PRICE(ERAR.BA)>10.5" );
      alerts.add( alert );
      
      
      return alerts;*/
     /* Criteria criteria = getSession().createCriteria(Alert.class);
      return (List<Alert>) criteria.list();*/
      List<Alert> employees = getManager().createQuery("Select a From Alert a", Alert.class).getResultList();
      return employees;
   }

}
