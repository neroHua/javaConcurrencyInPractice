package chapter14.case06;

import common.ThreadSafe;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

@ThreadSafe
public class OneShotLatch {

    private final Sync sync = new Sync();

    public void signal() {
        sync.releaseShared(0);
    }

    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(0);
    }

    private class Sync extends AbstractQueuedSynchronizer {

        protected int tryAcquireShared(int ignored) {
            // Succeed if latch is open (state == 1), else fail
            return (getState() == 1) ? 1 : -1;
        }

        protected boolean tryReleaseShared(int ignored) {
            // Latch is now open
            setState(1);
            // Other threads may now be able to acquire
            return true;
        }

    }

}
