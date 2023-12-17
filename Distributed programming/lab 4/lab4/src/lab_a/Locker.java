package lab_a;

import java.util.concurrent.atomic.AtomicInteger;

public class Locker {
    private final AtomicInteger writeLocker;
    private final AtomicInteger readLocker;

    public Locker() {
        writeLocker = new AtomicInteger(0);
        readLocker = new AtomicInteger(0);
    }

    public synchronized void setReadLock() {
        while (readLocker.get() == 1) {
            try {
                wait(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        synchronized (readLocker) {
            readLocker.set(1);
        }
    }

    public synchronized void setReadUnlock() {
        synchronized (readLocker) {
            readLocker.set(0);
        }
        notifyAll();
    }

    public synchronized void setWriteLock() {
        setReadLock();
        while (writeLocker.get() == 1) {
            try {
                wait(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        synchronized (writeLocker) {
            writeLocker.set(1);
        }
    }

    public synchronized void setWriteUnlock() {
        synchronized (writeLocker) {
            writeLocker.set(0);
        }
        notifyAll();
    }
}
