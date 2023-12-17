package lab_b;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class StringHandler extends Thread {
    private final Random random = new Random();
    private String curString;
    private final CyclicBarrier barrier;
    private final ThreadController threadController;
    private boolean running = true;
    private int counter;
    private final int indexOfThread;

    public StringHandler(String str, CyclicBarrier barrier, ThreadController threadController, int index){
        this.curString = str;
        this.barrier = barrier;
        this.threadController = threadController;
        this.counter = countAbMentioning(str);
        this.indexOfThread = index;
    }

    @Override
    public void run(){
        while(running) {
            int index = random.nextInt(curString.length());
            switch (curString.charAt(index)) {
                case 'A': {
                    curString = curString.substring(0, index) + 'C' + curString.substring(index + 1);
                    counter--;
                    break;
                }
                case 'B': {
                    curString = curString.substring(0, index) + 'D' + curString.substring(index + 1);
                    counter--;
                    break;
                }
                case 'C': {
                    curString = curString.substring(0, index) + 'A' + curString.substring(index + 1);
                    counter++;
                    break;
                }
                case 'D': {
                    curString = curString.substring(0, index) + 'B' + curString.substring(index + 1);
                    counter++;
                    break;
                }
            }
            threadController.getInfo(counter);
            System.out.println("Thread #" + this.indexOfThread + " " + curString + " " + counter);
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            if(this.indexOfThread == 1) {
                System.out.println();
            }
            running = threadController.isRunning();
        }
    }

    private int countAbMentioning(String str) {
        int count = 0;
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == 'A' || str.charAt(i) == 'B'){
                count++;
            }
        }
        return count;
    }
}
