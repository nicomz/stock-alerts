package ar.com.sac.model.operations;

import java.util.ArrayList;
import java.util.List;

public class OperatorAND implements Operator {
   
   private List<Operator> terms = new ArrayList<Operator>();
   
   public void addTerm(Operator term){
      terms.add( term );
   }
   
   public boolean evaluate() {
      boolean result = true;
      for(Operator term: terms){
         if(!term.evaluate()){
            result = false;
            break;
         }
      }
      return result;
   }

}
