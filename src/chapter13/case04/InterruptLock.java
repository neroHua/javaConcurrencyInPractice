package chapter13.case04;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class InterruptLock {

    Lock lock;

    public boolean trySendOnSharedLine(String message, long timeout, TimeUnit unit) throws InterruptedException {
        long nanosToLock = unit.toNanos(timeout) - estimatedNanosToSend(message);

        try {
            if (!lock.tryLock(nanosToLock, NANOSECONDS)) {
                return false;
            }
            return sendOnSharedLine(message);
        }
        finally {
            lock.unlock();
        }
    }

    private long estimatedNanosToSend(String message) {
        return 1000;
    }

    private boolean sendOnSharedLine(String message) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            return cancellableSendOnSharedLine(message);
        }
        finally {
            lock.unlock();
        }
    }

    private boolean cancellableSendOnSharedLine(String message) {
        return false;
    }

}
