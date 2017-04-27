package ar.com.sac.model.geneticAlgorithm;

import java.util.Arrays;
import org.jgap.Gene;
import org.jgap.IChromosome;

public abstract class AbstractGenesBuilder implements GenesBuilder {
   
   private int firstIndex = 0;

   @Override
   public void setFirstIndex( int index ) {
      firstIndex = index;
   }
   
   @Override
   public int getFirstIndex() {
      return firstIndex;
   }

   @Override
   public int getLastIndex() {
      return firstIndex + getSize() - 1;
   }
   
   @Override
   public String translatePart( IChromosome chromosome ) {
      //OPERATION_PERFORMANCE_PERCENTAGE>3
      Gene[] genesSubSet = Arrays.copyOfRange( chromosome.getGenes(), getFirstIndex(), getLastIndex() + 1 );
      //
      if( (Boolean) genesSubSet[0].getAllele() == false ){
         return "";
      }
      
      return translateAfterFlag( genesSubSet );
   }
   
   public abstract String translateAfterFlag( Gene[] genesSubSet );


}
