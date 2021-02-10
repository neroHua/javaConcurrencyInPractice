package chapter03.case02;

import common.GuardedBy;
import common.NotThreadSafe;
import common.ThreadSafe;

public class StaleData {

    @NotThreadSafe
    public class MutableInteger {

        private int value;

        public int get() {
            return value;
        }

        public void set(int value) {
            this.value = value;
        }

    }

    @ThreadSafe
    public class SynchronizedInteger {

        @GuardedBy("this")
        private int value;

        public synchronized int get() {
            return value;
        }

        public synchronized void set(int value) {
            this.value = value;
        }

    }

}
