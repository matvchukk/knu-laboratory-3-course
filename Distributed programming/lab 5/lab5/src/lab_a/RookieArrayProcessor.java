package lab_a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class RookieArrayProcessor extends Thread {
    private static final AtomicBoolean finished = new AtomicBoolean(false);
    private static final List<Boolean> finishedPart = new ArrayList<>();

    private final int[] recruits;
    private final CustomBarrier customBarrier;

    private final int partIndex;
    private final int leftIndex;
    private final int rightIndex;

    public RookieArrayProcessor(int[] recruits, CustomBarrier customBarrier, int partIndex, int leftIndex, int rightIndex) {
        this.recruits = recruits;
        this.customBarrier = customBarrier;
        this.partIndex = partIndex;
        this.leftIndex = leftIndex;
        this.rightIndex = rightIndex;
    }

    public void run() {
        while (!finished.get()) {
            boolean currentPartFinished = finishedPart.get(partIndex);
            if (!currentPartFinished) {
                if (partIndex == 0) {
                    System.out.println(Arrays.toString(recruits));
                }
                boolean isFormatted = true;
                for (int i = leftIndex; i < rightIndex - 1; i++) {
                    if (recruits[i] != recruits[i+1]){
                        if(recruits[i] == 1) {
                            recruits[i] = -1;
                        } else {
                            recruits[i] = 1;
                        }
                        isFormatted = false;
                    }
                }
                if(isFormatted) {
                    finish();
                }
            }
            try {
                customBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void finish() {
//        System.out.println("Part " + partIndex + " has finished sorting.");
        finishedPart.set(partIndex, true);
        for (boolean part: finishedPart) {
            if (!part) {
                return;
            }
        }
        finished.set(true);
    }

    public static void fillFinishedArray(int numberOfParts) {
        for (int i = 0; i < numberOfParts; i++) {
            finishedPart.add(false);
        }
    }
}
