package ar.com.sac.model.operations;

import ar.com.sac.model.Formula;

public class OperationFormula implements OperationTerm {
   
   private Formula formula;

   public OperationFormula(Formula formula){
      this.formula = formula;
   }

   public double getValue() {
      return formula.calculate().doubleValue();
   }

}
