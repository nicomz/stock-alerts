package ar.com.sac.model.operations;


public class OperationConstantValue implements OperationTerm {
   
   private double value;

   public OperationConstantValue(double value){
      this.value = value;
   }

   public double getValue() {
      return value;
   }

}
