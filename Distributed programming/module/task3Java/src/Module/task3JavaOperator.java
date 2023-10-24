package Module;

import java.util.concurrent.BlockingQueue;

public class task3JavaOperator extends Thread{
    private final int index;
    private static int nextInt = 1;

    {
        index = nextInt;
        nextInt++;
    }

    private final BlockingQueue<task3JavaClient> customersQueue;

    public task3JavaOperator(BlockingQueue<task3JavaClient> customersQueue) {
        this.customersQueue = customersQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                task3JavaClient client = customersQueue.take();
                if (client instanceof task3JavaClientMarker) {
                    break;
                }
                client.setGotPhoneCall();

                System.out.println("Operator #" + index + " is helping Client #" + client.getIndex());

                Thread.sleep(2000);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
