// Run() is called from Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.

import java.util.Vector;
import java.io.*;

public class SchedulingAlgorithm {

  public static Results run(int runtime, Vector processVector, Results result) {
    int i = 0;
    int comptime = 0;
    int currentProcess = 0;
    int previousProcess = 0;
    int size = processVector.size();
    int completed = 0;
    MultipleQueuesScheduler scheduler = new MultipleQueuesScheduler(processVector);
    String resultsFile = "Summary-Processes";
    result.schedulingType = "Batch (Preemptive)";
    result.schedulingName = "Multiple queues";
    try {
      PrintStream out = new PrintStream(new FileOutputStream(resultsFile));
      sProcess process = scheduler.getNextProcess();
      out.println("Process: " + process.processIndex + " with priority "+process.priority +" registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + ")");
      while (comptime < runtime) {
        for(int j=0;j<scheduler.blockedProcesses.size();j++){
          sProcess blockedProcess=scheduler.blockedProcesses.get(j);
          blockedProcess.blockingTime--;
          if(blockedProcess.blockingTime==0){
            blockedProcess.isBlocked=false;
            blockedProcess.blockingTime=100;
            scheduler.blockedProcesses.remove( j );
            out.println("Process: " + blockedProcess.processIndex +" with priority "+blockedProcess.priority + " unblocked... (" + blockedProcess.cputime + " " + blockedProcess.ioblocking + " " + blockedProcess.cpudone + ")");
            if(process!=null) {
              if (blockedProcess.priority <= process.priority) {
                continue;
              } else{
                out.println( "Process: " + process.processIndex + " with priority " + process.priority + " stopped working... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + ")" );
              }
            }
            process = scheduler.getNextProcess( );
            out.println( "Process: " + process.processIndex + " with priority " + process.priority + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + ")" );
          }
        }
        if (process == null) {
          continue;
        }
          if (process.cpudone == process.cputime) {
            completed++;
            out.println( "Process: " + process.processIndex + " with priority " + process.priority + " completed... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + ")" );
            if (completed == size) {
              result.compuTime = comptime;
              out.close( );
              return result;
            }
            process = scheduler.getNextProcess( );
            if(process==null){
              continue;
            }
            out.println( "Process: " + process.processIndex + " with priority " + process.priority + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + ")" );
          }




          if (process.ioblocking == process.ionext) {
            out.println("Process: " + process.processIndex + " with priority "+process.priority +" I/O blocked... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + ")");
            process.numblocked++;
            process.isBlocked=true;
            scheduler.blockedProcesses.add(process);
            process.ionext = 0;
            process = scheduler.getNextProcess();
            if(process==null){
              continue;
            }
            out.println("Process: " + process.processIndex +" with priority "+process.priority + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + ")");
          }

        process.cpudone++;
        if (process.ioblocking > 0) {
          process.ionext++;
        }

        comptime++;
      }
      out.close();
    } catch (IOException e) { /* Handle exceptions */ }
    result.compuTime = comptime;
    return result;
  }
//  public static Results Run(int runtime, Vector processVector, Results result) {
//    int i = 0;
//    int comptime = 0;
//    int currentProcess = 0;
//    int previousProcess = 0;
//    int size = processVector.size();
//    int completed = 0;
//    String resultsFile = "F:\\Source\\Repos\\OS-labs\\Lab3\\Summary-Processes";
//
//    result.schedulingType = "Batch (Nonpreemptive)";
//    result.schedulingName = "First-Come First-Served";
//    try {
//      //BufferedWriter out = new BufferedWriter(new FileWriter(resultsFile));
//      //OutputStream out = new FileOutputStream(resultsFile);
//      PrintStream out = new PrintStream(new FileOutputStream(resultsFile));
//      sProcess process = (sProcess) processVector.elementAt(currentProcess);
//      out.println("Process: " + currentProcess + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.cpudone + ")");
//      while (comptime < runtime) {
//        if (process.cpudone == process.cputime) {
//          completed++;
//          out.println("Process: " + currentProcess + " completed... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.cpudone + ")");
//          if (completed == size) {
//            result.compuTime = comptime;
//            out.close();
//            return result;
//          }
//          for (i = size - 1; i >= 0; i--) {
//            process = (sProcess) processVector.elementAt(i);
//            if (process.cpudone < process.cputime) {
//              currentProcess = i;
//            }
//          }
//          process = (sProcess) processVector.elementAt(currentProcess);
//          out.println("Process: " + currentProcess + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.cpudone + ")");
//        }
//        if (process.ioblocking == process.ionext) {
//          out.println("Process: " + currentProcess + " I/O blocked... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.cpudone + ")");
//          process.numblocked++;
//          process.ionext = 0;
//          previousProcess = currentProcess;
//          for (i = size - 1; i >= 0; i--) {
//            process = (sProcess) processVector.elementAt(i);
//            if (process.cpudone < process.cputime && previousProcess != i) {
//              currentProcess = i;
//            }
//          }
//          process = (sProcess) processVector.elementAt(currentProcess);
//          out.println("Process: " + currentProcess + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.cpudone + ")");
//        }
//        process.cpudone++;
//        if (process.ioblocking > 0) {
//          process.ionext++;
//        }
//        comptime++;
//      }
//      out.close();
//    } catch (IOException e) { /* Handle exceptions */ }
//    result.compuTime = comptime;
//    return result;
//  }
}
