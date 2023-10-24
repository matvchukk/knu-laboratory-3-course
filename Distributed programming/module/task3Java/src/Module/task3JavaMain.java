package Module;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class task3JavaMain {
    public static final BlockingQueue<task3JavaClient> WAITING_TASK_3_JAVA_CLIENTS = new LinkedBlockingDeque<>();
    public static final Random RANDOM = new Random();
    public static int remainingCustomers;

    public static void main(String[] args) {
        int customers = 20;
        int operators = 2;
        remainingCustomers = customers;

        for (int i = 0; i < operators; i++) {
            new task3JavaOperator(WAITING_TASK_3_JAVA_CLIENTS).start();
        }

        List<task3JavaClient> clientList = new ArrayList<>();
        for (int i = 0; i < customers; i++) {
            task3JavaClient client = new task3JavaClient(WAITING_TASK_3_JAVA_CLIENTS, i % operators + 1);
            clientList.add(client);
            client.start();
        }


        for (task3JavaClient client : clientList) {
            try {
                client.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            WAITING_TASK_3_JAVA_CLIENTS.put(new task3JavaClientMarker());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}