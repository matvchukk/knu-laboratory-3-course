package task_b;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        final int CAPACITY = 5;
        final int NUM_DETAILS = 35;

        ProducerConsumerCounter pcc = new ProducerConsumerCounter(CAPACITY);

        Thread ivanovThread = new Thread(() -> {
            try {
                pcc.producedByIvanov(NUM_DETAILS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread petrovThread = new Thread(() -> {
            try {
                pcc.consumedByPetrov(NUM_DETAILS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread nechiporchukThread = new Thread(() -> {
            try {
                pcc.countedByNechiporchuk(NUM_DETAILS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        ivanovThread.start();
        petrovThread.start();
        nechiporchukThread.start();

        ivanovThread.join();
        petrovThread.join();
        nechiporchukThread.join();

    }
}