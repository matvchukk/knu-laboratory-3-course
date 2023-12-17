package lab_b;

import java.util.concurrent.CyclicBarrier;

public class Main {
    private static final int NUMBER_OF_THREADS = 4;

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(NUMBER_OF_THREADS);
        ThreadController threadController = new ThreadController(NUMBER_OF_THREADS);

        StringHandler firstChanger = new StringHandler("ABCDCDAABCD", barrier, threadController, 1);
        StringHandler secondChanger = new StringHandler("AAACAACBBAC", barrier, threadController, 2);
        StringHandler thirdChanger = new StringHandler("ACDCADCACDC", barrier, threadController, 3);
        StringHandler fourthChanger = new StringHandler("CDABBABCDAB", barrier, threadController, 4);

        firstChanger.start();
        secondChanger.start();
        thirdChanger.start();
        fourthChanger.start();
    }
}
