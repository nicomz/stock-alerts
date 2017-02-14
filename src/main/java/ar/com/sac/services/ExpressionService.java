package ar.com.sac.services;

import ar.com.sac.model.Quote;
import ar.com.sac.model.formulas.ExponentialMovingAverage;
import ar.com.sac.model.formulas.MACD;
import ar.com.sac.model.formulas.MACDHistogram;
import ar.com.sac.model.formulas.MACDSignalLine;
import ar.com.sac.model.formulas.Price;
import ar.com.sac.model.formulas.RelativeStrengthIndex;
import ar.com.sac.model.formulas.SimpleMovingAverage;
import ar.com.sac.model.formulas.StochasticOscillatorD;
import ar.com.sac.model.formulas.StochasticOscillatorK;
import ar.com.sac.model.formulas.Volume;
import ar.com.sac.model.operations.OperationConstantValue;
import ar.com.sac.model.operations.OperationFormula;
import ar.com.sac.model.operations.OperationTerm;
import ar.com.sac.model.operations.Operator;
import ar.com.sac.model.operations.OperatorAND;
import ar.com.sac.model.operations.OperatorGREATERThan;
import ar.com.sac.model.operations.OperatorLESSThan;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ExpressionService {
   
   public Operator parseExpression( String expression, IStockService stockService ) {
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
      }else if(expression.startsWith( "SMA" )){
         String[] params = expression.split( "," );
         List<Quote> quotes;
         try {
            quotes = stockService.getHistory( params[1].replace( ")","" ) );
         } catch (Exception e) {
            quotes = new ArrayList<Quote>();
            e.printStackTrace();
         }
         SimpleMovingAverage sma = new SimpleMovingAverage( Integer.parseInt( params[0].substring( 4 ) ), quotes );
         result = new OperationFormula( sma );
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
      }else if(expression.startsWith( "PRICE" )){
         String symbolParam = expression.substring( 6 ).replace( ")","" );
         Quote quote;
         try {
            quote = stockService.getStock( symbolParam ).getLastQuote();
         } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException( "No quote for symbol: " + symbolParam );
         }
         result = new OperationFormula( new Price( quote ) );
      }else if(expression.startsWith( "VOLUME" )){
         String symbolParam = expression.substring( 7 ).replace( ")","" );
         Quote quote;
         try {
            quote = stockService.getStock( symbolParam ).getLastQuote();
         } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException( "No quote for symbol: " + symbolParam );
         }
         result = new OperationFormula( new Volume( quote ) );
      }else if(expression.startsWith( "MACD_SIGNAL_LINE" )){
         String[] params = expression.split( "," );
         List<Quote> quotes;
         try {
            quotes = stockService.getHistory( params[3].replace( ")","" ) );
         } catch (Exception e) {
            quotes = new ArrayList<Quote>();
            e.printStackTrace();
         }
         MACDSignalLine macdSignal = new MACDSignalLine( Integer.parseInt( params[0].substring( 17 ) ), Integer.parseInt( params[1] ), Integer.parseInt( params[2] ), quotes );
         result = new OperationFormula( macdSignal );
      }else if(expression.startsWith( "MACD_HISTOGRAM" )){
         String[] params = expression.split( "," );
         List<Quote> quotes;
         try {
            quotes = stockService.getHistory( params[3].replace( ")","" ) );
         } catch (Exception e) {
            quotes = new ArrayList<Quote>();
            e.printStackTrace();
         }
         MACDHistogram macdHistogram = new MACDHistogram( Integer.parseInt( params[0].substring( 15 ) ), Integer.parseInt( params[1] ), Integer.parseInt( params[2] ), quotes );
         result = new OperationFormula( macdHistogram );
      }else if(expression.startsWith( "MACD" )){
         String[] params = expression.split( "," );
         List<Quote> quotes;
         try {
            quotes = stockService.getHistory( params[2].replace( ")","" ) );
         } catch (Exception e) {
            quotes = new ArrayList<Quote>();
            e.printStackTrace();
         }
         MACD macd = new MACD( Integer.parseInt( params[0].substring( 5 ) ), Integer.parseInt( params[1] ), quotes );
         result = new OperationFormula( macd );
      }else if(expression.startsWith( "STOCHASTIC_K" )){
         String[] params = expression.split( "," );
         List<Quote> quotes;
         try {
            quotes = stockService.getHistory( params[1].replace( ")","" ) );
         } catch (Exception e) {
            quotes = new ArrayList<Quote>();
            e.printStackTrace();
         }
         StochasticOscillatorK sok = new StochasticOscillatorK( Integer.parseInt( params[0].substring( 13 ) ), quotes );
         result = new OperationFormula( sok );
      }else if(expression.startsWith( "STOCHASTIC_D" )){
         String[] params = expression.split( "," );
         List<Quote> quotes;
         try {
            quotes = stockService.getHistory( params[2].replace( ")","" ) );
         } catch (Exception e) {
            quotes = new ArrayList<Quote>();
            e.printStackTrace();
         }
         StochasticOscillatorD sod = new StochasticOscillatorD( Integer.parseInt( params[0].substring( 13 )), Integer.parseInt( params[1] ) , quotes );
         result = new OperationFormula( sod );
      }else{
         result = new OperationConstantValue( Double.parseDouble( expression ) );
      }
      return result;
   }

}
