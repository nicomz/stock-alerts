package ar.com.sac.model.geneticAlgorithm;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;

public interface GenesBuilder {
   
   public void setFirstIndex( int index );
   
   public int getFirstIndex();
   
   public int getLastIndex();
   
   public int getSize();
   
   public Gene[] getSampleGenes( Configuration jgapConfig ) throws InvalidConfigurationException;
  
   public String translatePart( IChromosome chromosome );

}
