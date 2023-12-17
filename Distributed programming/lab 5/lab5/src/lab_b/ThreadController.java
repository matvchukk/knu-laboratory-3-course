package lab_b;

import java.util.Arrays;

public class ThreadController {
    private boolean running = true;
    private int counterOfThreads = 0;
    private final int numberOfThreads;
    private final int[] threadsData;
    private boolean allThreadsArrived = false;

    public ThreadController(int threadNum) {
        numberOfThreads = threadNum;
        threadsData = new int[threadNum];
    }

    public boolean isRunning() {
        return running;
    }

    public void checkAndTerminateIfNecessary() {
        boolean requiredStringsAreFound = true;
        Arrays.sort(threadsData);
        for (int i = 1; i < threadsData.length - 2; i++) {
            if (threadsData[i] != threadsData[i + 1]) {
                requiredStringsAreFound = false;
                break;
            }
        }
        if (requiredStringsAreFound) {
            if (threadsData[0] == threadsData[1] || threadsData[threadsData.length - 1] == threadsData[1]) {
                running = false;
            }
        }
    }

    public synchronized void getInfo(int data) {
        threadsData[counterOfThreads] = data;
        counterOfThreads++;
        if (counterOfThreads == numberOfThreads) {
            notifyAll();
            allThreadsArrived = true;
        }
        while (!allThreadsArrived) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        counterOfThreads--;
        if (counterOfThreads == 0) {
            checkAndTerminateIfNecessary();
            allThreadsArrived = false;
        }
    }
}

