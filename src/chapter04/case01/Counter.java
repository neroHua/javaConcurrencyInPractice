package chapter04.case01;

import common.GuardedBy;
import common.ThreadSafe;

@ThreadSafe
public final class Counter {

    @GuardedBy("this")
    private long value = 0;


    public synchronized long getValue() {
        return value;
    }

    public synchronized long increment() {
        if (value == Integer.MAX_VALUE) {
            throw new IllegalStateException("counter overflow");
        }

        return ++value;
    }

}
