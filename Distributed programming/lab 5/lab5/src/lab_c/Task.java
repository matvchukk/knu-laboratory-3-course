package lab_c;

import java.util.Arrays;

public class Task {
    private Thread[] threads;
    private int[] rookies;
    private CustomBarrier customBarrier;

    public Task(int teamsAmount, int rookiesAmount){
        RookieTeam.fillFinishedArray(teamsAmount);
        rookies = new int[rookiesAmount];
        customBarrier = new CustomBarrier(teamsAmount);
        threads = new Thread[teamsAmount];
        fillRookies();
        startThreads();
        System.out.println(Arrays.toString(rookies));
    }

    private void fillRookies(){
        // 1 - turned right, -1 - turned left
        for (int i = 0; i < rookies.length; i++) {
            if (Math.random() < 0.5) {
                rookies[i] = 1;
            } else {
                rookies[i] = -1;
            }
        }
    }

    private void startThreads(){
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new RookieTeam(rookies, i, 50 * i, 50 * (i + 1), customBarrier));
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

}
