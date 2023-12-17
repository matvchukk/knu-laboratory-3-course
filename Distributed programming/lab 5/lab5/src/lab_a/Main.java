package lab_a;

import java.security.SecureRandom;
import java.util.Arrays;

public class Main {
    private static final SecureRandom random = new SecureRandom();
    private static final int SIZE = 200;
    private static final int NUMBER_OF_PARTS = 3;
    private static final RookieArrayProcessor[] threads = new RookieArrayProcessor[NUMBER_OF_PARTS];
    private static final int[] recruits = new int[SIZE];
    private static final CustomBarrier CUSTOM_BARRIER = new CustomBarrier(NUMBER_OF_PARTS);

    public static void main(String[] args) {
        RookieArrayProcessor.fillFinishedArray(NUMBER_OF_PARTS);
        fillRecruitsArray();
        createAndStartThreads();
        System.out.println("Result: " + Arrays.toString(recruits));
    }

    private static void fillRecruitsArray() {
        for (int i = 0; i < SIZE; i++) {
            int randomValue = Math.random() < 0.5 ? -1 : 1;
            recruits[i] = randomValue;
        }
    }

    private static void createAndStartThreads() {
        for (int i = 0; i < threads.length; i++) {
            if (i == 0) {
                threads[i] = new RookieArrayProcessor(recruits, CUSTOM_BARRIER, i,
                        0, (i + 1) * (SIZE / NUMBER_OF_PARTS) + 1);
            } else if (i == threads.length - 1) {
                threads[i] = new RookieArrayProcessor(recruits, CUSTOM_BARRIER, i,
                        (i) * (SIZE / NUMBER_OF_PARTS) - 1, (i + 1) * (SIZE / NUMBER_OF_PARTS));
            } else {
                threads[i] = new RookieArrayProcessor(recruits, CUSTOM_BARRIER, i,
                        (i) * (SIZE / NUMBER_OF_PARTS) - 1, (i + 1) * (SIZE / NUMBER_OF_PARTS) + 1);
            }
        }
        for (RookieArrayProcessor thread : threads) {
            thread.start();
        }
        for (RookieArrayProcessor thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
