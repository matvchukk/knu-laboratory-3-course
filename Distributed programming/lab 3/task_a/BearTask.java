package task_a;

import java.util.Random;

public class BearTask implements Runnable {

    private static final int DELAY = 300;

    private final Pot pot;

    public BearTask(Pot pot) {
        this.pot = pot;
    }

    @Override
    public void run() {
        while(!Thread.interrupted()) {
            while(!Thread.interrupted()) {
                try {
                    randomSleep();
                    int honey = pot.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void randomSleep() throws InterruptedException {
        Random random = new Random();
        Thread.sleep(random.nextInt(DELAY) + 200);
    }
}