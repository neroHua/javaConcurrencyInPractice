package chapter12.case01;

import org.junit.Assert;
import org.junit.Test;

public class TestBoundedBuffer {

    @Test
    public void testIsEmptyWhenConstructed() {
        BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);

        Assert.assertTrue(bb.isEmpty());
        Assert.assertFalse(bb.isFull());
    }

    @Test
    public void testIsFullAfterPuts() throws InterruptedException {
        BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
        for (int i = 0; i < 10; i++) {
            bb.put(i);
        }

        Assert.assertTrue(bb.isEmpty());
        Assert.assertFalse(bb.isFull());
    }

    @Test
    public void testTakeBlocksWhenEmpty() {
        final int LOCKUP_DETECT_TIMEOUT = 100;
        final BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);

        Thread taker = new Thread() {
            public void run() {
                try {
                    int unused = bb.take();

                    // if we get here, it's an error
                    Assert.fail();
                } catch (InterruptedException success) {
                }
            }};

        try {
            taker.start();
            Thread.sleep(LOCKUP_DETECT_TIMEOUT);
            taker.interrupt();
            taker.join(LOCKUP_DETECT_TIMEOUT);
            Assert.assertFalse(taker.isAlive());
        } catch (Exception unexpected) {
            Assert.fail();
        }
    }

    @Test
    public void testLeak() throws InterruptedException {
        final int CAPACITY = 10;
        final int THRESHOLD = 20;
        BoundedBuffer<Big> bb = new BoundedBuffer<Big>(CAPACITY);

        int heapSize1 = 0;
        for (int i = 0; i < CAPACITY; i++) {
            bb.put(new Big());
        }
        for (int i = 0; i < CAPACITY; i++) {
            bb.take();
        }
        int heapSize2 = 1;

        Assert.assertTrue(Math.abs(heapSize1 - heapSize2) < THRESHOLD);
    }

    public class Big {

        double[] data = new double[100000];

    }
}
