package lab_a;

public class CustomBarrier {
    private final int partiesAtStart;
    private int partiesAwait;

    public CustomBarrier(int parties) {
        this.partiesAtStart = parties;
        this.partiesAwait = parties;
    }

    public synchronized void await() throws InterruptedException {
        partiesAwait--;
        if(partiesAwait > 0) {
            this.wait();
        }

        partiesAwait = partiesAtStart;
        notifyAll();
    }
}
