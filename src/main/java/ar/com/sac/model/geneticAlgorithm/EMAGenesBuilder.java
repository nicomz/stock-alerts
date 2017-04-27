package ar.com.sac.model.geneticAlgorithm;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.BooleanGene;
import org.jgap.impl.IntegerGene;


public class EMAGenesBuilder extends AbstractGenesBuilder {

   @Override
   public int getSize() {
      return 4;
   }

   @Override
   public Gene[] getSampleGenes( Configuration jgapConfig ) throws InvalidConfigurationException {
      Gene[] sampleGenes = new Gene[ getSize() ];
      sampleGenes[0] = new BooleanGene(jgapConfig, true );  // Flag
      sampleGenes[1] = new IntegerGene(jgapConfig, 1, 60 );  // Period1
      sampleGenes[2] = new IntegerGene(jgapConfig, 1, 60 );  // Period2
      sampleGenes[3] = new BooleanGene(jgapConfig, false );  // Operator
      return sampleGenes;
   }

   @Override
   public String translateAfterFlag( Gene[] genesSubSet ) {
      //EMA(5,GOOGL)>EMA(20,GOOGL)
      StringBuilder sb = new StringBuilder();
      sb.append( "EMA(" );
      int period1 = (Integer) genesSubSet[1].getAllele();
      sb.append( period1 );
      sb.append( ",[SYMBOL])" );
      if((Boolean) genesSubSet[3].getAllele()){
         sb.append( "<" );
      }else{
         sb.append( ">" );
      }
      sb.append( "EMA(" );
      int period2 = (Integer) genesSubSet[2].getAllele();
      sb.append( period2 );
      sb.append( ",[SYMBOL])" );
      return sb.toString();
   }

}
