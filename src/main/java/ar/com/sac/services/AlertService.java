package ar.com.sac.services;

import ar.com.sac.model.Alert;
import ar.com.sac.model.ExponentialMovingAverage;
import ar.com.sac.model.Notification;
import ar.com.sac.model.Quote;
import ar.com.sac.model.RelativeStrengthIndex;
import ar.com.sac.model.operations.OperationConstantValue;
import ar.com.sac.model.operations.OperationFormula;
import ar.com.sac.model.operations.OperationTerm;
import ar.com.sac.model.operations.Operator;
import ar.com.sac.model.operations.OperatorAND;
import ar.com.sac.model.operations.OperatorGREATERThan;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlertService {
   
   @Autowired
   private StockService stockService;
   

   public List<Notification> processAlerts(){
      //Create HARDCODED alerts
      Alert alert = new Alert();
      alert.setActive( true );
      alert.setSendEmail( false );
      alert.setName( "Buy signal - MIRG" );
      alert.setDescription( "MIRG has thrown a buy signal." );
      alert.setExpression( "EMA(5,MIRG.BA)>EMA(100,MIRG.BA)&&RSI(14,MIRG.BA)>50" );

      List<Notification> notifications = new ArrayList<Notification>();
      processAlert(alert, notifications);
      return notifications;
   }

   private void processAlert( Alert alert, List<Notification> notifications ) {
      Operator operator = parseExpression( alert.getExpression() );
      if(operator.evaluate()){
         Notification notification = new Notification();
         notification.setCreationDate( new Date() );
         notification.setAlert( alert );
         notifications.add( notification  );
      }
   }

   private Operator parseExpression( String expression ) {
      String[] andSplit = expression.split( "&&" );
      Operator result;
      if(andSplit.length == 1){
         OperatorGREATERThan greater = processAND( expression );
         result = greater;
      }else{
         OperatorAND and = new OperatorAND();
         for(int i = 0 ; i < andSplit.length; i++){
            and.addTerm( processAND( andSplit[i] ) );
         }
         result = and;
      }
      
      return result;
   }

   private OperatorGREATERThan processAND( String expression ) {
      String[] greaterSplit = expression.split( ">" );
      if(greaterSplit.length == 1){
         throw new RuntimeException( "Invalid Expression: " + expression );
      }
      OperatorGREATERThan greater = new OperatorGREATERThan();
      for(int i = 0 ; i < greaterSplit.length; i++){
         greater.addOperationTerm( processOperationTerm( greaterSplit[i] ) );
      }
      return greater;
   }

   private OperationTerm processOperationTerm( String expression ) {
      OperationTerm result;
      if(expression.startsWith( "EMA" )){
         String[] params = expression.split( "," );
         List<Quote> quotes;
         try {
            quotes = stockService.getHistory( params[1].replace( ")","" ) );
         } catch (Exception e) {
            quotes = new ArrayList<Quote>();
            e.printStackTrace();
         }
         ExponentialMovingAverage ema = new ExponentialMovingAverage( Integer.parseInt( params[0].substring( 4 ) ), quotes );
         result = new OperationFormula( ema );
      }else if(expression.startsWith( "RSI" )){
         String[] params = expression.split( "," );
         List<Quote> quotes;
         try {
            quotes = stockService.getHistory( params[1].replace( ")","" ) );
         } catch (Exception e) {
            quotes = new ArrayList<Quote>();
            e.printStackTrace();
         }
         RelativeStrengthIndex rsi = new RelativeStrengthIndex( Integer.parseInt( params[0].substring( 4 ) ), quotes );
         result = new OperationFormula( rsi );
      }else{
         result = new OperationConstantValue( Double.parseDouble( expression ) );
      }
      return result;
   }

}
