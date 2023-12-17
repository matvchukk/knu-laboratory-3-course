package lab_b;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class FileMonitor extends Thread {
    private final Garden garden;
    private final Lock lock;
    private final java.io.File file;

    public FileMonitor(Garden garden, ReadWriteLock lock) {
        this.garden = garden;
        this.lock = lock.readLock();
        this.file = new java.io.File("lab4/src/lab_b/result.txt");
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, false);
            writer.write("");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (Main.running) {
                Thread.sleep(new Random().nextInt(1000) + 1000);
                lock.lock();
                System.out.printf(Thread.currentThread().getName() + " unlocked\n");
                System.out.printf(Thread.currentThread().getName() + " working\n");
                Thread.sleep(500);
                var writer = new FileWriter(file, true);
                writer.write(garden.toString() + "\n");
                writer.flush();
                System.out.printf(Thread.currentThread().getName() + " unlocked\n");
                lock.unlock();
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
