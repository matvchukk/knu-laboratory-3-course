package task_b;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ProducerConsumerCounter {
    private final BlockingQueue<Integer> fromProducerToConsumer;
    private final BlockingQueue<Integer> fromConsumerToCounter;
    private static final Random random = new Random();

    public ProducerConsumerCounter(Integer capacity) {
        this.fromProducerToConsumer = new LinkedBlockingDeque<>(capacity);
        this.fromConsumerToCounter = new LinkedBlockingDeque<>(capacity);
    }

    public void producedByIvanov(int numItems) throws InterruptedException {
        int curDetail = 1;
        while (true) {
            System.out.println("Ivanov added item #" + curDetail);
            this.fromProducerToConsumer.put(curDetail++);

            if (curDetail == numItems + 1)
                break;

            justSleep();
        }
    }



    public void consumedByPetrov(int numItems) throws InterruptedException {
        while (true) {
            int removed = this.fromProducerToConsumer.take();
            numItems--;
            System.out.println("Petrov removed item #" + removed);
            this.fromConsumerToCounter.put(removed);

            // break if all details were consumed
            if (numItems == 0)
                break;

            justSleep();
        }
    }

    public void countedByNechiporchuk(int numItems) throws InterruptedException {
        int count = 1;
        while (true) {
            int removed = fromConsumerToCounter.take();
            System.out.println("Nechiporchuk counted item #" + count++);

            if (count == numItems + 1)
                break;

            justSleep();
        }
    }
private void justSleep() {
    try {
        Thread.sleep(random.nextInt(450) + 10);
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
}
}
