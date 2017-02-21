package ar.com.sac.model.simulator;

import ar.com.sac.model.CalendarSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Calendar;

public class SimulatorRecord {
   
   private int id;
   private double capitalBalance;
   private int relatedRecordId;
   private double liquity;
   private String orderType; //Buy or Sell : B or S
   private String orderSymbol;
   private Calendar orderDate;
   private double orderAmount; //amount of stocks
   private double orderPrice;
   private double orderTotalCost;
   private double operationPerformance;
   private int operationDays;
   
   /*
    * @return the id
    */
   public synchronized int getId() {
      return id;
   }
   
   /**
    * @param id the id to set
    */
   public synchronized void setId( int id ) {
      this.id = id;
   }
   
   /**
    * @return the capitalBalance
    */
   public synchronized double getCapitalBalance() {
      return capitalBalance;
   }
   
   /**
    * @param capitalBalance the capitalBalance to set
    */
   public synchronized void setCapitalBalance( double capitalBalance ) {
      this.capitalBalance = capitalBalance;
   }
   
   /**
    * @return the relatedRecordId
    */
   public synchronized int getRelatedRecordId() {
      return relatedRecordId;
   }
   
   /**
    * @param relatedRecordId the relatedRecordId to set
    */
   public synchronized void setRelatedRecordId( int relatedRecordId ) {
      this.relatedRecordId = relatedRecordId;
   }
   
   /**
    * @return the liquity
    */
   public synchronized double getLiquity() {
      return liquity;
   }
   
   /**
    * @param liquity the liquity to set
    */
   public synchronized void setLiquity( double liquity ) {
      this.liquity = liquity;
   }
   
   /**
    * @return the orderType
    */
   public synchronized String getOrderType() {
      return orderType;
   }
   
   /**
    * @param orderType the orderType to set
    */
   public synchronized void setOrderType( String orderType ) {
      this.orderType = orderType;
   }
   
   /**
    * @return the orderSymbol
    */
   public synchronized String getOrderSymbol() {
      return orderSymbol;
   }
   
   /**
    * @param orderSymbol the orderSymbol to set
    */
   public synchronized void setOrderSymbol( String orderSymbol ) {
      this.orderSymbol = orderSymbol;
   }
   
   /**
    * @return the orderDate
    */
   @JsonSerialize(using = CalendarSerializer.class)
   public synchronized Calendar getOrderDate() {
      return orderDate;
   }
   
   /**
    * @param orderDate the orderDate to set
    */
   public synchronized void setOrderDate( Calendar orderDate ) {
      this.orderDate = orderDate;
   }
   
   /**
    * @return the orderAmount
    */
   public synchronized double getOrderAmount() {
      return orderAmount;
   }
   
   /**
    * @param orderAmount the orderAmount to set
    */
   public synchronized void setOrderAmount( double orderAmount ) {
      this.orderAmount = orderAmount;
   }
   
   /**
    * @return the orderPrice
    */
   public synchronized double getOrderPrice() {
      return orderPrice;
   }
   
   /**
    * @param orderPrice the orderPrice to set
    */
   public synchronized void setOrderPrice( double orderPrice ) {
      this.orderPrice = orderPrice;
   }
   
   /**
    * @return the orderTotalCost
    */
   public synchronized double getOrderTotalCost() {
      return orderTotalCost;
   }
   
   /**
    * @param orderTotalCost the orderTotalCost to set
    */
   public synchronized void setOrderTotalCost( double orderTotalCost ) {
      this.orderTotalCost = orderTotalCost;
   }

   public double getOperationPerformance() {
      return operationPerformance;
   }

   public void setOperationPerformance( double operationPerformance ) {
      this.operationPerformance = operationPerformance;
   }
   
   
   /**
    * @return the operationDays
    */
   public synchronized int getOperationDays() {
      return operationDays;
   }

   
   /**
    * @param operationDays the operationDays to set
    */
   public synchronized void setOperationDays( int operationDays ) {
      this.operationDays = operationDays;
   }

}
