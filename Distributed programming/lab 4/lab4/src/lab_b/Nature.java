package lab_b;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class Nature extends Thread {
    private final Garden garden;
    private final Lock lock;

    public Nature(Garden garden, ReadWriteLock rwLock) {
        this.garden = garden;
        this.lock = rwLock.writeLock();
    }

    @Override
    public void run() {
        try {
            while (Main.running) {
                Thread.sleep(new Random().nextInt(1000) + 1000);
                lock.lock();
                System.out.printf(Thread.currentThread().getName() + " locked\n");
                System.out.printf(Thread.currentThread().getName() + " working\n");
                garden.gardenIsWithering();
                System.out.printf(Thread.currentThread().getName() + " unloked\n");
                lock.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}