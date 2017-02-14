package ar.com.sac.services;

import ar.com.sac.model.Quote;
import ar.com.sac.model.operations.Operator;
import ar.com.sac.model.simulator.SimulationResults;
import ar.com.sac.model.simulator.SimulatorParameters;
import ar.com.sac.model.simulator.SimulatorRecord;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Simulation {
   
   private SimulatorParameters parameters;
   private StockService stockService;
   private ExpressionService expressionService;
   private SimulationResults simulationResults = new SimulationResults();
   private StockSimulatorService stockSimulatorService = new StockSimulatorService();
   
   
   //simulation variables
   private Map<String, List<Quote>> symbolToQuotesMap  = new HashMap<>();
   private Map<String, SimulatorRecord> positionsMap = new HashMap<>();
   private int previousAnalysisDays = 35;
   private SimulatorRecord lastSimulatorRecord;
   private int minQuoteSize = Integer.MAX_VALUE;

   public Simulation( SimulatorParameters parameters, StockService stockService, ExpressionService expressionService ){
      this.parameters = parameters;
      this.stockService = stockService;
      this.expressionService = expressionService;
   }
   
   public SimulationResults run(){
      
      try {
         initSimulationVariables();
         List<Quote> quotesAux;
         
         for( int i = previousAnalysisDays; i < minQuoteSize; i++ ){
            for(String symbol : parameters.getSymbols()){
               quotesAux = symbolToQuotesMap.get( symbol );
               stockSimulatorService.setSimulationQuotes( quotesAux.subList( quotesAux.size() - i, quotesAux.size() ));
               if(!tryBuy( symbol )){
                  if(!trySell( symbol )){
                     tryStopLoss( symbol );
                  }
               }
            }
         }
         return simulationResults;
      } catch (IOException e) {
         e.printStackTrace();
         throw new RuntimeException( "Error running simulation" );
      }
   }

   private void initSimulationVariables() throws IOException {
      lastSimulatorRecord = new SimulatorRecord();
      lastSimulatorRecord.setId( 0 );
      lastSimulatorRecord.setCapitalBalance( parameters.getInitialCapital() );
      lastSimulatorRecord.setLiquity( parameters.getInitialCapital() );
      lastSimulatorRecord.setOrderType( "Initial Investment" );
      simulationResults.addRecord( lastSimulatorRecord );

      List<Quote> quotesAux;
      Calendar from = new GregorianCalendar(parameters.getYearFrom(),0,1);
      Calendar to = new GregorianCalendar(parameters.getYearTo(),11,31);
      for(String symbol : parameters.getSymbols()){
         quotesAux = stockService.getHistory( symbol, from, to );
         if( quotesAux.size() < minQuoteSize ){
            minQuoteSize = quotesAux.size();
         }
         symbolToQuotesMap.put( symbol, quotesAux );
      }
   }

   private boolean tryStopLoss( String symbol ) throws IOException {
//      stockSimulatorService.getStock( symbol ).getLastQuote();
      boolean sold = false;
      if( positionsMap.get( symbol ) == null ) {
         return false; //There is NOT a position on this symbol
      }
      
      Quote lastQuote = stockSimulatorService.getStock( symbol ).getLastQuote();
      SimulatorRecord positionRecord = positionsMap.get( symbol );
      
      double maxCapitalToLoss = lastSimulatorRecord.getCapitalBalance() * parameters.getStopLossPercentage() / 100d;
      double currentValue = lastQuote.getClose().doubleValue() * positionRecord.getOrderAmount();
      if( (positionRecord.getOrderTotalCost() - currentValue) > maxCapitalToLoss ){
         SimulatorRecord sellRecord = sell( symbol );
         sellRecord.setOrderType( "Sell on StopLoss" );
         
         simulationResults.addRecord( sellRecord );
         positionsMap.put( symbol, null );
         lastSimulatorRecord = sellRecord;
         sold = true;
      }
      
      return sold;
   }

   private boolean trySell( String symbol ) throws IOException {
      if( positionsMap.get( symbol ) == null ) {
         return false; //There is NOT a position on this symbol
      }
      boolean sold = false;
      Operator operator = expressionService.parseExpression( parameters.getSellExpression(), stockSimulatorService );
      if( operator.evaluate() ){
         SimulatorRecord sellRecord = sell( symbol );
        
         simulationResults.addRecord( sellRecord );
         positionsMap.put( symbol, null );
         lastSimulatorRecord = sellRecord;
         sold = true;
      }
      return sold;
   }

   private SimulatorRecord sell( String symbol ) throws IOException {
      Quote lastQuote = stockSimulatorService.getStock( symbol ).getLastQuote();
      SimulatorRecord positionRecord = positionsMap.get( symbol );
      double sellAux = lastQuote.getClose().doubleValue() * positionRecord.getOrderAmount();
      double commission = sellAux * parameters.getCommissionPercentage() / 100d;
      double totalEarned = sellAux - commission;
      
      SimulatorRecord sellRecord = new SimulatorRecord();
      sellRecord.setRelatedRecordId( positionRecord.getId() );
      sellRecord.setId( lastSimulatorRecord.getId() + 1 );
      sellRecord.setOrderAmount( positionRecord.getOrderAmount() );
      sellRecord.setOrderPrice( lastQuote.getClose().doubleValue() );
      sellRecord.setOrderDate( lastQuote.getDate() );
      sellRecord.setLiquity( lastSimulatorRecord.getLiquity() + totalEarned );
      sellRecord.setOrderTotalCost( totalEarned );
      sellRecord.setOrderSymbol( symbol );
      sellRecord.setOrderType( "Sell" );
      sellRecord.setCapitalBalance( lastSimulatorRecord.getCapitalBalance() + totalEarned - (positionRecord.getOrderAmount() * positionRecord.getOrderPrice()) );
      sellRecord.setOperationPerformance( sellRecord.getOrderTotalCost() - positionRecord.getOrderTotalCost() );
      return sellRecord;
   }

   private boolean tryBuy( String symbol ) throws IOException {
      if( positionsMap.get( symbol ) != null ) {
         return false; //already has position on this symbol
      }
      boolean bought = false;
      Operator operator = expressionService.parseExpression( parameters.getBuyExpression(), stockSimulatorService );
      
      if( operator.evaluate() ){
         if(lastSimulatorRecord.getLiquity() >= parameters.getPositionMinimumValue()){
            double buyPrice = calculateBuyPrice();
            Quote lastQuote = stockSimulatorService.getStock( symbol ).getLastQuote();
            double stockPrice = lastQuote.getClose().doubleValue();
            int amount = (int)(buyPrice / stockPrice);
            double stocksValue = amount * stockPrice;
            double commission =  stocksValue * parameters.getCommissionPercentage() / 100d;
            double orderValue = stocksValue + commission;
            //make the buy
            SimulatorRecord buyRecord = new SimulatorRecord();
            buyRecord.setId( lastSimulatorRecord.getId() + 1 );
            buyRecord.setLiquity( lastSimulatorRecord.getLiquity() - orderValue );
            buyRecord.setOrderAmount( amount );
            buyRecord.setOrderPrice( stockPrice );
            buyRecord.setOrderSymbol( symbol );
            buyRecord.setOrderDate( lastQuote.getDate() );
            buyRecord.setOrderTotalCost( orderValue );
            buyRecord.setOrderType( "Buy" );
            buyRecord.setCapitalBalance( lastSimulatorRecord.getCapitalBalance() - commission );
            
            simulationResults.addRecord( buyRecord );
            positionsMap.put( symbol, buyRecord );
            lastSimulatorRecord = buyRecord;
            bought = true;
         }
      }
      return bought;
   }

   private double calculateBuyPrice() {
      double percentage = parameters.getPositionPercentage();
      double aux = (percentage / 100d) * lastSimulatorRecord.getCapitalBalance();
      if(aux > parameters.getPositionMaximumValue()){
         aux = parameters.getPositionMaximumValue();
      }else if( aux < parameters.getPositionMinimumValue()){
         aux = parameters.getPositionMinimumValue();
      }
      
      if( lastSimulatorRecord.getLiquity() < aux ){
         aux = parameters.getPositionMinimumValue();
      }
      
      double estimatedComission = aux * parameters.getCommissionPercentage() /100d;
      
      return aux - estimatedComission;
   }

}
