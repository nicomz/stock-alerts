package ar.com.sac.model.geneticAlgorithm;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.BooleanGene;
import org.jgap.impl.IntegerGene;


public class MACDGenesBuilder extends AbstractGenesBuilder {

   @Override
   public int getSize() {
      return 5;
   }

   @Override
   public Gene[] getSampleGenes( Configuration jgapConfig ) throws InvalidConfigurationException {
      Gene[] sampleGenes = new Gene[ getSize() ];
      sampleGenes[0] = new BooleanGene(jgapConfig, true );  // Flag
      sampleGenes[1] = new IntegerGene(jgapConfig, 1, 50 );  // slowPeriod 12
      sampleGenes[2] = new IntegerGene(jgapConfig, 1, 50 );  // Period 26
      sampleGenes[3] = new IntegerGene(jgapConfig, 1, 20 );  // signalPeriod 9
      sampleGenes[4] = new BooleanGene(jgapConfig, false );  // Operator
      return sampleGenes;
   }

   @Override
   public String translateAfterFlag( Gene[] genesSubSet ) {
      /*
      private int slowPeriod; //12
      private int fastPeriod; //26
      private int signalPeriod; //9
      */
      //MACD(12,26,MIRG.BA)<MACD_SIGNAL_LINE(12,26,9,MIRG.BA)
      StringBuilder sb = new StringBuilder();
      sb.append( "MACD(" );
      int slowPeriod = (Integer) genesSubSet[1].getAllele();
      sb.append( slowPeriod );
      sb.append( "," );
      int fastPeriod = (Integer) genesSubSet[2].getAllele();
      sb.append( fastPeriod );
      sb.append( ",[SYMBOL])" );
      
      if((Boolean) genesSubSet[4].getAllele()){
         sb.append( "<" );
      }else{
         sb.append( ">" );
      }
      sb.append( "MACD_SIGNAL_LINE(" );
      sb.append( slowPeriod );
      sb.append( "," );
      sb.append( fastPeriod );
      sb.append( "," );
      int signalPeriod = (Integer) genesSubSet[3].getAllele();
      sb.append( signalPeriod );
      sb.append( ",[SYMBOL])" );
      return sb.toString();
   }

}
