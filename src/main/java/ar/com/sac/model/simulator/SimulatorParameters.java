package ar.com.sac.model.simulator;

import java.util.Calendar;

public class SimulatorParameters {
   
   private double initialCapital;
   private double commissionPercentage;
   private double positionMinimumValue;
   private double positionPercentage;
   private double positionMaximumValue;
   private String buyExpression;
   private String sellExpression;
   private double stopLossPercentage;
   private String[] symbols;
   private int yearFrom = Calendar.getInstance().get(Calendar.YEAR) - 5;
   private int yearTo = Calendar.getInstance().get(Calendar.YEAR);
   
   /**
    * @return the initialCapital
    */
   public synchronized double getInitialCapital() {
      return initialCapital;
   }
   
   /**
    * @param initialCapital the initialCapital to set
    */
   public synchronized void setInitialCapital( double initialCapital ) {
      this.initialCapital = initialCapital;
   }
   
   /**
    * @return the commissionPercentage
    */
   public synchronized double getCommissionPercentage() {
      return commissionPercentage;
   }
   
   /**
    * @param commissionPercentage the commissionPercentage to set
    */
   public synchronized void setCommissionPercentage( double commissionPercentage ) {
      this.commissionPercentage = commissionPercentage;
   }
   
   /**
    * @return the positionMinimumValue
    */
   public synchronized double getPositionMinimumValue() {
      return positionMinimumValue;
   }
   
   /**
    * @param positionMinimumValue the positionMinimumValue to set
    */
   public synchronized void setPositionMinimumValue( double positionMinimumValue ) {
      this.positionMinimumValue = positionMinimumValue;
   }
   
   /**
    * @return the positionPercentage
    */
   public synchronized double getPositionPercentage() {
      return positionPercentage;
   }
   
   /**
    * @param positionPercentage the positionPercentage to set
    */
   public synchronized void setPositionPercentage( double positionPercentage ) {
      this.positionPercentage = positionPercentage;
   }
   
   /**
    * @return the positionMaximumValue
    */
   public synchronized double getPositionMaximumValue() {
      return positionMaximumValue;
   }
   
   /**
    * @param positionMaximumValue the positionMaximumValue to set
    */
   public synchronized void setPositionMaximumValue( double positionMaximumValue ) {
      this.positionMaximumValue = positionMaximumValue;
   }
   
   /**
    * @return the buyExpression
    */
   public synchronized String getBuyExpression() {
      return buyExpression;
   }
   
   /**
    * @param buyExpression the buyExpression to set
    */
   public synchronized void setBuyExpression( String buyExpression ) {
      this.buyExpression = buyExpression;
   }
   
   /**
    * @return the sellExpression
    */
   public synchronized String getSellExpression() {
      return sellExpression;
   }
   
   /**
    * @param sellExpression the sellExpression to set
    */
   public synchronized void setSellExpression( String sellExpression ) {
      this.sellExpression = sellExpression;
   }
   
   /**
    * @return the stopLossPercentage
    */
   public synchronized double getStopLossPercentage() {
      return stopLossPercentage;
   }
   
   /**
    * @param stopLossPercentage the stopLossPercentage to set
    */
   public synchronized void setStopLossPercentage( double stopLossPercentage ) {
      this.stopLossPercentage = stopLossPercentage;
   }
   
   /**
    * @return the symbols
    */
   public synchronized String[] getSymbols() {
      return symbols;
   }
   
   /**
    * @param symbols the symbols to set
    */
   public synchronized void setSymbols( String[] symbols ) {
      this.symbols = symbols;
   }

   
   /**
    * @return the yearFrom
    */
   public synchronized int getYearFrom() {
      return yearFrom;
   }

   
   /**
    * @param yearFrom the yearFrom to set
    */
   public synchronized void setYearFrom( int yearFrom ) {
      this.yearFrom = yearFrom;
   }

   
   /**
    * @return the yearTo
    */
   public synchronized int getYearTo() {
      return yearTo;
   }

   
   /**
    * @param yearTo the yearTo to set
    */
   public synchronized void setYearTo( int yearTo ) {
      this.yearTo = yearTo;
   }

   
}
