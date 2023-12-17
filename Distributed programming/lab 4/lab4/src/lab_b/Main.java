package lab_b;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
    public static boolean running = true;
    public static void main(String[] args) {
        var garden = new Garden(15,15);
        var lock = new ReentrantReadWriteLock();

        // Nature
        Thread natureThread = new Nature(garden, lock);
        natureThread.setName("Nature thread");
        natureThread.start();

        // Gardener
        Thread gardenerThread = new Gardener(garden, lock);
        gardenerThread.setName("Gardener thread");
        gardenerThread.start();

        // FileMonitor
        Thread fileMonitorThread = new FileMonitor(garden, lock);
        fileMonitorThread.setName("File monitor thread");
        fileMonitorThread.start();

        // ConsoleMonitor
        Thread consoleMonitorThread = new ConsoleMonitor(garden, lock);
        consoleMonitorThread.setName("Console monitor thread");
        consoleMonitorThread.start();
    }
}
