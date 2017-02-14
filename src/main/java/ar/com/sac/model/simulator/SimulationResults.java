package ar.com.sac.model.simulator;

import java.util.ArrayList;
import java.util.List;

public class SimulationResults {
   
   private List<SimulatorRecord> records = new ArrayList<>();
   
   public void addRecord(SimulatorRecord record){
      records.add( record );
   }
   
   /**
    * @return the records
    */
   public synchronized List<SimulatorRecord> getRecords() {
      return records;
   }
   

}
