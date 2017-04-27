package ar.com.sac.model.geneticAlgorithm;

import java.util.ArrayList;
import java.util.List;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;

public class ChromosomeTranslator {
   
      private Configuration jgapConfig;
      private List<GenesBuilder> buyExpressionGenesBuilders = new ArrayList<>();
      private List<GenesBuilder> sellExpressionGenesBuilders = new ArrayList<>();

      public ChromosomeTranslator( Configuration jgapConfig ){
         this.jgapConfig = jgapConfig;
         //Set up gene builders
         buyExpressionGenesBuilders.add( new EMAGenesBuilder() );
         buyExpressionGenesBuilders.add( new StochasticGenesBuilder() );
         buyExpressionGenesBuilders.add( new MACDGenesBuilder() );
         
         
         sellExpressionGenesBuilders.add( new EMAGenesBuilder() );
         sellExpressionGenesBuilders.add( new StochasticGenesBuilder() );
         sellExpressionGenesBuilders.add( new MACDGenesBuilder() );
         sellExpressionGenesBuilders.add( new OperationPerformancePercentageGenesBuilder() );
         setIndexes();
      }
   

      private void setIndexes() {
         int currentIndex = 0;
         for(GenesBuilder builder : buyExpressionGenesBuilders){
            builder.setFirstIndex( currentIndex );
            currentIndex += builder.getSize();
         }
         
         for(GenesBuilder builder : sellExpressionGenesBuilders){
            builder.setFirstIndex( currentIndex );
            currentIndex += builder.getSize();
         }
      }


      public IChromosome getSampleChromosome(){
         Gene[] sampleGenes = new Gene[ calculateChromosomeSize() ];

         try {
            //buy
            int i = 0;
            Gene[] auxGenes;
            for(GenesBuilder builder : buyExpressionGenesBuilders){
               auxGenes = builder.getSampleGenes( jgapConfig );
               for(int j=0; j<builder.getSize(); j++){
                  sampleGenes[i] = auxGenes[j];
                  i++;
               }
            }
            //sell
            for(GenesBuilder builder : sellExpressionGenesBuilders){
               auxGenes = builder.getSampleGenes( jgapConfig );
               for(int j=0; j<builder.getSize(); j++){
                  sampleGenes[i] = auxGenes[j];
                  i++;
               }
            }

            return new Chromosome(jgapConfig, sampleGenes );
         } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            throw new RuntimeException( e );
         }
      }
      
      private int calculateChromosomeSize() {
         int size = 0;
         for(GenesBuilder builder : buyExpressionGenesBuilders){
            size += builder.getSize();
         }
         
         for(GenesBuilder builder : sellExpressionGenesBuilders){
            size += builder.getSize();
         }
         return size;
      }


      public String getBuyExpression( IChromosome chromosome ){
         return translateGenesBuilders( chromosome, buyExpressionGenesBuilders );
      }
      
      public String getSellExpression( IChromosome chromosome ){
         return translateGenesBuilders( chromosome, sellExpressionGenesBuilders );
      }
      
      private String translateGenesBuilders( IChromosome chromosome, List<GenesBuilder> builders ){
         StringBuilder sb = new StringBuilder();
         String auxPart;
         int i = 0;
         for(GenesBuilder builder : builders){
            auxPart = builder.translatePart( chromosome );
            if(auxPart.length() > 0){
               if( i != 0 && sb.length() > 0){
                  sb.append( "&&" );
               }
               sb.append( auxPart );
            }
            i++;
         }
         return sb.toString();
      }

}
