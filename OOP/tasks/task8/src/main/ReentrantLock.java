package main;
public class ReentrantLock {
    private final Object obj = new Object();
    private Thread owner = null;
    private int lockCount = 0;

    public void lock() throws InterruptedException {
        synchronized (obj) {
            if (owner == Thread.currentThread()) {
                lockCount++;
                return;
            }

            while (owner != null) {
                obj.wait();
            }

            owner = Thread.currentThread();
            lockCount = 1;
        }
    }

    public void unlock() {
        synchronized (obj) {
            if (owner != Thread.currentThread()) {
                throw new IllegalStateException("Lock usage error");
            }

            lockCount--;

            if (lockCount == 0) {
                owner = null;
                obj.notify();
            }
        }
    }
    public int getLockCount() {
        synchronized (obj) {
            return lockCount;
        }
    }

}
