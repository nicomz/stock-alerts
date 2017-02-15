package ar.com.sac.model.operations;

import java.util.ArrayList;
import java.util.List;

public class OperatorGREATERThan implements Operator {
   private List<OperationTerm> terms = new ArrayList<OperationTerm>();
   
   public void addOperationTerm(OperationTerm term){
      terms.add( term );
   }
   
   public boolean evaluate() {
      boolean result = true;
      for( int i = 0 ; i < terms.size() -1 ; i++ ){
         if(!( terms.get( i ).getValue() > terms.get( i+1 ).getValue() )){
            result = false;
            break;
         }
      }
      return result;
   }
   
   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append( terms.get( 0 ) );
      sb.append( ">" );
      sb.append( terms.get( 1 ) );
      return sb.toString();
   }

}
