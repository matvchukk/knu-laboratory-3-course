package task_b;

import java.util.Random;

public class Main {
    public static final int VISITORS_AMOUNT = 10;

    public static void main(String[] args) {
        Hairdresser hairdresser = new Hairdresser();

        for(int i = 0; i < VISITORS_AMOUNT; ++i){
            new Thread(new Client(Integer.toString(i), hairdresser)).start();
        }
    }

}