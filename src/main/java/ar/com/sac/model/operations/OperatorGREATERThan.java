package ar.com.sac.model.operations;

import java.util.ArrayList;
import java.util.List;

public class OperatorGREATERThan implements Operator {
   private List<OperationTerm> terms = new ArrayList<OperationTerm>();
   
   public void addOperationTerm(OperationTerm term){
      terms.add( term );
   }
   
   public boolean evaluate() {
      return terms.get( 0 ).getValue() > terms.get( 1 ).getValue();
   }

}
