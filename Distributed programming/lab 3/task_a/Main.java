package task_a;

public class Main {
    public static final int CAPACITY = 30;
    public static final int BEE_AMOUNT = 10;

    public static void main(String[] args) {
        Pot pot = new Pot(CAPACITY);

        new Thread(new BearTask(pot)).start();
        for(int i = 1; i <= BEE_AMOUNT; ++i) new Thread(new BeesTask(Integer.toString(i), pot)).start();
    }
}