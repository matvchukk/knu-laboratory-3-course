package Module;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import static Module.task3JavaMain.RANDOM;
public class task3JavaClient extends Thread{
    private final int operatorIndex;
    private final int index;
    private static int nextInt = 1;

    {
        index = nextInt;
        nextInt++;
    }

    private final AtomicBoolean gotPhoneCall = new AtomicBoolean(false);
    private final BlockingQueue<task3JavaClient> customersQueue;

    public task3JavaClient(BlockingQueue<task3JavaClient> customersQueue, int operatorIndex) {
        this.customersQueue = customersQueue;
        this.operatorIndex = operatorIndex;
    }


    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("New client (#" + index + ") joined the queue");
                customersQueue.add(this);

                int sleep = RANDOM.nextInt(3000);
                Thread.sleep(sleep);

                if (gotPhoneCall.get()) {
                    task3JavaMain.remainingCustomers--;
                    break;
                } else {
                    boolean present = customersQueue.remove(this);
                    if (!present) {
                        break;
                    }

                    System.out.println("Client #" + index + " left the queue");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (task3JavaMain.remainingCustomers == 0) {
            try {
                customersQueue.put(new task3JavaClientMarker());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void setGotPhoneCall() {
        this.gotPhoneCall.getAndSet(true);
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        task3JavaClient client = (task3JavaClient) o;
        return index == client.index;
    }
}
