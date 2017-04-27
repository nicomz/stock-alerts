package ar.com.sac.model.geneticAlgorithm;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.BooleanGene;
import org.jgap.impl.IntegerGene;


public class OperationPerformancePercentageGenesBuilder extends AbstractGenesBuilder {

   @Override
   public int getSize() {
      return 3;
   }

   @Override
   public Gene[] getSampleGenes( Configuration jgapConfig ) throws InvalidConfigurationException {
      Gene[] sampleGenes = new Gene[ getSize() ];
      sampleGenes[0] = new BooleanGene(jgapConfig, true );  // Flag
      sampleGenes[1] = new BooleanGene(jgapConfig, false );  // Operator
      sampleGenes[2] = new IntegerGene(jgapConfig, 0, 100 );  // Percentage
      return sampleGenes;
   }

   @Override
   public String translateAfterFlag( Gene[] genesSubSet ) {
      //[OPERATION_PERFORMANCE_PERCENTAGE]>3
      StringBuilder sb = new StringBuilder();
      sb.append( "[OPERATION_PERFORMANCE_PERCENTAGE]" );
      if((Boolean) genesSubSet[1].getAllele()){
         sb.append( "<" );
      }else{
         sb.append( ">" );
      }
      int percentage = (Integer) genesSubSet[2].getAllele();
      sb.append( percentage );
      return sb.toString();
   }


}
