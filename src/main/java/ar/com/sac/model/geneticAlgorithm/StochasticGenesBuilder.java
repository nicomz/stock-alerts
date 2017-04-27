package ar.com.sac.model.geneticAlgorithm;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.BooleanGene;
import org.jgap.impl.IntegerGene;


public class StochasticGenesBuilder extends AbstractGenesBuilder {

   @Override
   public int getSize() {
      return 4;
   }

   @Override
   public Gene[] getSampleGenes( Configuration jgapConfig  ) throws InvalidConfigurationException {
      Gene[] sampleGenes = new Gene[ getSize() ];
      sampleGenes[0] = new BooleanGene(jgapConfig, true );  // Flag
      sampleGenes[1] = new IntegerGene(jgapConfig, 1, 30 );  // Length
      sampleGenes[2] = new IntegerGene(jgapConfig, 1, 15 );  // Period
      sampleGenes[3] = new BooleanGene(jgapConfig, false );  // Operator
      return sampleGenes;
   }

   @Override
   public String translateAfterFlag( Gene[] genesSubSet ) {
      StringBuilder sb = new StringBuilder();
      sb.append( "STOCHASTIC_K(" );
      int length = (Integer) genesSubSet[1].getAllele();
      sb.append( length );
      sb.append( ",[SYMBOL])" );
      if((Boolean) genesSubSet[3].getAllele()){
         sb.append( "<" );
      }else{
         sb.append( ">" );
      }
      sb.append( "STOCHASTIC_D(" );
      sb.append( length );
      sb.append( "," );
      int period = (Integer) genesSubSet[2].getAllele();
      sb.append( period );
      sb.append( ",[SYMBOL])" );
      //STOCHASTIC_K(14,GOOGL)>STOCHASTIC_D(14,3,GOOGL)
      return sb.toString();
   }


}
