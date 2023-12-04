import org.junit.Assert;
import org.junit.Test;
import main.Counter;
import main.ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

public class ThreadPoolTest {
    private final Counter counter = new Counter();

    @Test
    public void checkSpeedUp() throws Exception {
        int cores = Runtime.getRuntime().availableProcessors();
        long timeWithMaxCores = timeWithCores(cores);
        long timeWithOneCore = timeWithCores(1);
        double speedUp = (double) timeWithOneCore / timeWithMaxCores;
        Assert.assertTrue(speedUp > cores * 0.8);
    }

    private long timeWithCores(int cores) throws Exception {
        ThreadPool threadPool = new ThreadPool(cores);
        long start = System.nanoTime();
        List<Future<Double>> futures = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            final int j = i;
            futures.add(
                    CompletableFuture.supplyAsync(
                            () -> counter.count(j),
                            threadPool
                    ));
        }
        double value = 0;
        for (Future<Double> future : futures) {
            value += future.get();
        }
        return System.nanoTime() - start;
    }
}