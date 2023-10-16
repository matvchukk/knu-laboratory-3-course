package task_a;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Bees extends Thread{
    private Thread[] threads;
    private int id;
    private final Forest FOREST;

    public final Integer NUMBER_OF_THREADS;
    private final AtomicBoolean POOH_IS_FOUNDED;

    private final AtomicInteger CURRENT_ROW;


    public Bees(int id, Forest forest) {
        this.id = id;
        this.FOREST = forest;
        this.NUMBER_OF_THREADS = (int)Math.sqrt(forest.sizeOfForest);
        this.threads = new Thread[NUMBER_OF_THREADS];
        POOH_IS_FOUNDED = new AtomicBoolean(false);
        CURRENT_ROW = new AtomicInteger(0);
    }

    @Override
    public void run() {
        while(!POOH_IS_FOUNDED.get() && CURRENT_ROW.get() < FOREST.sizeOfForest) {
            int row = CURRENT_ROW.getAndIncrement();
            check(row);
        }
    }

    public void search () {
        for(int i = 0; i < NUMBER_OF_THREADS; i++){
            threads[i] = new Bees(i, FOREST);
            threads[i].start();
        }
        for(int i = 0; i < NUMBER_OF_THREADS; i++){
            try{
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void check(int row) {
        if(POOH_IS_FOUNDED.get()) { return; }
        for(int i = 0; i < FOREST.sizeOfForest; i++){
            System.out.println(Thread.currentThread().getName() + " group of bees in row: " + row + " in column: " + i);
            if(FOREST.forestArea[row][i] == 1){
                System.out.println(Thread.currentThread().getName() + " pooh was founded in row: " + row + " in column: " + i);
                POOH_IS_FOUNDED.set(true);
                break;
            }
        }
    }
}