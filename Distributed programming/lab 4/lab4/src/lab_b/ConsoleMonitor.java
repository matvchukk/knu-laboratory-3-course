package lab_b;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class ConsoleMonitor extends Thread {
    private final Garden garden;
    private final Lock lock;

    public ConsoleMonitor(Garden garden, ReadWriteLock lock) {
        this.garden = garden;
        this.lock = lock.readLock();
    }

    @Override
    public void run() {
        try {
            while (Main.running) {
                Thread.sleep(new Random().nextInt(1000) + 1000);
                lock.lock();
                System.out.printf(Thread.currentThread().getName() + " locked\n");
                Thread.sleep(500);
                System.out.println(garden.toString());
                System.out.printf(Thread.currentThread().getName() + " unlocked\n");
                lock.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
