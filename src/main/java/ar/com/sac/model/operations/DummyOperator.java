package ar.com.sac.model.operations;

/**
 * Always return false
 * @author Sergio Cormio
 *
 */
public class DummyOperator implements Operator {

   @Override
   public boolean evaluate() {
      return false;
   }

}
