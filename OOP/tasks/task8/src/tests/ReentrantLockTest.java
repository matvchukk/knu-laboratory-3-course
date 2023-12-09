import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import main.ReentrantLock;

public class ReentrantLockTest {

    @Test
    public void testLockUnlock() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();

        reentrantLock.lock();
        assertEquals(1, reentrantLock.getLockCount());

        reentrantLock.unlock();
        assertEquals(0, reentrantLock.getLockCount());
    }

    @Test
    public void testReentrantLock() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();

        reentrantLock.lock();
        assertEquals(1, reentrantLock.getLockCount());

        reentrantLock.lock();
        assertEquals(2, reentrantLock.getLockCount());

        reentrantLock.unlock();
        assertEquals(1, reentrantLock.getLockCount());

        reentrantLock.unlock();
        assertEquals(0, reentrantLock.getLockCount());
    }
}