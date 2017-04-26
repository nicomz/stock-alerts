package ar.com.sac.model.geneticAlgorithm;

import java.util.Arrays;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.BooleanGene;
import org.jgap.impl.IntegerGene;

public class ChromosomeTranslator {
   
      private Configuration jgapConfig;
      private int sellIndex = 4;

      public ChromosomeTranslator( Configuration jgapConfig ){
         this.jgapConfig = jgapConfig;
      }
   

      public IChromosome getSampleChromosome(){
         Gene[] sampleGenes = new Gene[ 8 ];

         try {
            //buy
            sampleGenes[0] = new BooleanGene(jgapConfig, true );  // Flag
            sampleGenes[1] = new IntegerGene(jgapConfig, 1, 30 );  // Length
            sampleGenes[2] = new IntegerGene(jgapConfig, 1, 15 );  // Period
            sampleGenes[3] = new BooleanGene(jgapConfig, false );  // Operator
            //sell
            sampleGenes[4] = new BooleanGene(jgapConfig, true );  // Flag
            sampleGenes[5] = new IntegerGene(jgapConfig, 1, 30 );  // Length
            sampleGenes[6] = new IntegerGene(jgapConfig, 1, 1 );  // Period
            sampleGenes[7] = new BooleanGene(jgapConfig, true );  // Operator

            return new Chromosome(jgapConfig, sampleGenes );
         } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            throw new RuntimeException( e );
         }
      }
      
      public String getBuyExpression( IChromosome chromosome ){
         Gene[] genes = chromosome.getGenes();
         return translateGenes( genes, 0, sellIndex );
      }
      
      public String getSellExpression( IChromosome chromosome ){
         Gene[] genes = chromosome.getGenes();
         return translateGenes( genes, sellIndex, genes.length );
      }

      private String translateGenes( Gene[] genes, int from, int to ) {
         Gene[] genesSubSet = Arrays.copyOfRange( genes, from, to );
         //
         if( (Boolean) genesSubSet[0].getAllele() == false ){
            return "";
         }
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
