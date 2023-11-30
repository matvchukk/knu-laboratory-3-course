public class sProcess implements Comparable{
  public int cputime;
  public int ioblocking;
  public int cpudone;
  public int ionext;
  public int numblocked;
  public int priority;
  public int processIndex;
  public boolean isBlocked;
  public int blockingTime=100;
  public static int index=0;
  public sProcess (int cputime, int ioblocking,int priority ,int cpudone, int ionext, int numblocked) {
    this.cputime = cputime;
    this.ioblocking = ioblocking;
    this.priority = priority;
    this.cpudone = cpudone;
    this.ionext = ionext;
    this.numblocked = numblocked;
    this.processIndex = index;
    index++;
  }
  @Override
  public int compareTo(java.lang.Object o){
    sProcess otherProcess = (sProcess)o;
    return otherProcess.priority - this.priority;
  }
}