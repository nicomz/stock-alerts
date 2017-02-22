package ar.com.sac.services;

import ar.com.sac.model.Quote;
import ar.com.sac.model.formulas.Average;
import ar.com.sac.model.formulas.ExponentialMovingAverage;
import ar.com.sac.model.formulas.MACD;
import ar.com.sac.model.formulas.MACDHistogram;
import ar.com.sac.model.formulas.MACDSignalLine;
import ar.com.sac.model.formulas.Price;
import ar.com.sac.model.formulas.RelativeStrengthIndex;
import ar.com.sac.model.formulas.SimpleMovingAverage;
import ar.com.sac.model.formulas.StandardDeviation;
import ar.com.sac.model.formulas.StochasticOscillatorD;
import ar.com.sac.model.formulas.StochasticOscillatorK;
import ar.com.sac.model.formulas.Variance;
import ar.com.sac.model.formulas.Volume;
import ar.com.sac.model.operations.OperationConstantValue;
import ar.com.sac.model.operations.OperationFormula;
import ar.com.sac.model.operations.OperationTerm;
import ar.com.sac.model.operations.Operator;
import ar.com.sac.model.operations.OperatorAND;
import ar.com.sac.model.operations.OperatorGREATERThan;
import ar.com.sac.model.operations.OperatorLESSThan;
import ar.com.sac.model.simulator.SimulatorRecord;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ExpressionService {
   
   public String normalizeExpression( String expression ){
      return expression.replace( " ", "" ).toUpperCase();
   }
   
   private String replaceSimulationConstants( String expression, Simulation simulation) {
      expression = normalizeExpression(expression);
      expression = expression.replaceAll( "\\[SYMBOL\\]", simulation.getCurrentSymbol() );
      double operationPerfomance = 0d;
      double operationPerfomancePercentage = 0d;
      SimulatorRecord currentPosition = simulation.getPosition( simulation.getCurrentSymbol() );
      if( currentPosition != null ){
         double sellValue = simulation.getCurrentLastQuote().getClose().doubleValue() * currentPosition.getOrderAmount();
         double sellCommission = simulation.getParameters().getCommissionPercentage() * sellValue / 100d;
         operationPerfomance = (sellValue - currentPosition.getOrderTotalCost()) - sellCommission;
         operationPerfomancePercentage = operationPerfomance * 100d / currentPosition.getOrderTotalCost();
      }
      
      expression = expression.replaceAll( "\\[OPERATION_PERFORMANCE\\]", String.valueOf( operationPerfomance ));
      expression = expression.replaceAll( "\\[OPERATION_PERFORMANCE_PERCENTAGE\\]", String.valueOf( operationPerfomancePercentage ));
      return expression;
   }
   
   public Operator parseSimulatorExpression( String expression, Simulation simulation, IStockService stockService ) {
      expression = replaceSimulationConstants( expression, simulation );
      return parseExpression( expression, stockService );
   }
   
   public Operator parseExpression( String expression, IStockService stockService ) {
      expression = normalizeExpression(expression);
      String[] andSplit = expression.split( "&&" );
      Operator result;
      if(andSplit.length == 1){
         result = processANDTerm( expression, stockService );
      }else{
         OperatorAND and = new OperatorAND();
         for(int i = 0 ; i < andSplit.length; i++){
            and.addTerm( processANDTerm( andSplit[i], stockService ) );
         }
         result = and;
      }
      
      return result;
   }

   private Operator processANDTerm( String expression, IStockService stockService ) {
      Operator operator;
      if(expression.contains( ">" )){
         String[] greaterSplit = expression.split( ">" );
         OperatorGREATERThan greater = new OperatorGREATERThan();
         for(int i = 0 ; i < greaterSplit.length; i++){
            greater.addOperationTerm( processOperationTerm( greaterSplit[i], stockService ) );
         }
         operator = greater;
      }else if(expression.contains( "<" )){
         String[] lessSplit = expression.split( "<" );
         OperatorLESSThan less = new OperatorLESSThan();
         for(int i = 0 ; i < lessSplit.length; i++){
            less.addOperationTerm( processOperationTerm( lessSplit[i], stockService ) );
         }
         operator = less;
      }else{
         throw new RuntimeException( "Invalid Expression: " + expression );
      }
      return operator;
   }

   private OperationTerm processOperationTerm( String expression, IStockService stockService ) {
      OperationTerm result;
      List<Quote> quotes;
      Quote quote;
      String[] params = expression.split( "," );
      if(expression.startsWith( "EMA" )){
         quotes = getQuotes( stockService, params[1].replace( ")","" ) );
         ExponentialMovingAverage ema = new ExponentialMovingAverage( Integer.parseInt( params[0].substring( 4 ) ), quotes );
         result = new OperationFormula( ema );
      }else if(expression.startsWith( "SMA" )){
         quotes = getQuotes( stockService, params[1].replace( ")","" ) );
         SimpleMovingAverage sma = new SimpleMovingAverage( Integer.parseInt( params[0].substring( 4 ) ), quotes );
         result = new OperationFormula( sma );
      }else if(expression.startsWith( "RSI" )){
         quotes = getQuotes( stockService, params[1].replace( ")","" ) );
         RelativeStrengthIndex rsi = new RelativeStrengthIndex( Integer.parseInt( params[0].substring( 4 ) ), quotes );
         result = new OperationFormula( rsi );
      }else if(expression.startsWith( "PRICE" )){
         try {
            quote = stockService.getStock( expression.substring( 6 ).replace( ")","" ) ).getLastQuote();
         } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException( "Could not calculate price from symbol: " + expression.substring( 6 ).replace( ")","" ) );
         }
         result = new OperationFormula( new Price( quote ) );
      }else if(expression.startsWith( "VOLUME" )){
         try {
            quote = stockService.getStock( expression.substring( 7 ).replace( ")","" ) ).getLastQuote();
         } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException( "Could not calculate volume from symbol: " + expression.substring( 7 ).replace( ")","" ) );
         }
         result = new OperationFormula( new Volume( quote ) );
      }else if(expression.startsWith( "MACD_SIGNAL_LINE" )){
         quotes = getQuotes( stockService, params[3].replace( ")","" ) );
         MACDSignalLine macdSignal = new MACDSignalLine( Integer.parseInt( params[0].substring( 17 ) ), Integer.parseInt( params[1] ), Integer.parseInt( params[2] ), quotes );
         result = new OperationFormula( macdSignal );
      }else if(expression.startsWith( "MACD_HISTOGRAM" )){
         quotes = getQuotes( stockService, params[3].replace( ")","" ) );
         MACDHistogram macdHistogram = new MACDHistogram( Integer.parseInt( params[0].substring( 15 ) ), Integer.parseInt( params[1] ), Integer.parseInt( params[2] ), quotes );
         result = new OperationFormula( macdHistogram );
      }else if(expression.startsWith( "MACD" )){
         quotes = getQuotes( stockService, params[2].replace( ")","" ) );
         MACD macd = new MACD( Integer.parseInt( params[0].substring( 5 ) ), Integer.parseInt( params[1] ), quotes );
         result = new OperationFormula( macd );
      }else if(expression.startsWith( "STOCHASTIC_K" )){
         quotes = getQuotes( stockService, params[1].replace( ")","" ) );
         StochasticOscillatorK sok = new StochasticOscillatorK( Integer.parseInt( params[0].substring( 13 ) ), quotes );
         result = new OperationFormula( sok );
      }else if(expression.startsWith( "STOCHASTIC_D" )){
         quotes = getQuotes( stockService, params[2].replace( ")","" ) );
         StochasticOscillatorD sod = new StochasticOscillatorD( Integer.parseInt( params[0].substring( 13 )), Integer.parseInt( params[1] ) , quotes );
         result = new OperationFormula( sod );
      }else if(expression.startsWith( "AVERAGE" )){
         quotes = getQuotes(stockService, params[1].replace( ")","" ));
         result = new OperationFormula( new Average( Integer.parseInt( params[0].substring( 8 ) ), quotes ) );
      }else if(expression.startsWith( "VARIANCE" )){
         quotes = getQuotes(stockService, params[1].replace( ")","" ));
         result = new OperationFormula( new Variance( Integer.parseInt( params[0].substring( 9 ) ), quotes ) );
      }else if(expression.startsWith( "STANDARD_DEVIATION" )){
         quotes = getQuotes(stockService, params[1].replace( ")","" ));
         result = new OperationFormula( new StandardDeviation( Integer.parseInt( params[0].substring( 19 ) ), quotes ) );
      }else{
         result = new OperationConstantValue( Double.parseDouble( expression ) );
      }
      return result;
   }
   
   private List<Quote> getQuotes(IStockService stockService,String symbol){
      List<Quote> quotes;
      try {
         quotes = stockService.getHistory( symbol );
      } catch (Exception e) {
         quotes = new ArrayList<Quote>();
         e.printStackTrace();
      }
      return quotes;
   }

}
