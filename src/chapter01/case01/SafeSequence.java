package chapter01.case01;

import common.GuardedBy;
import common.ThreadSafe;
import common.ThreadUnsafeException;

public class SafeSequence {

    private int value;

    public int getValue() {
        return value;
    }

    /**
     *   public synchronized int getAndSetNextValue01();
     *     Code:
     *        0: aload_0
     *        1: dup
     *        2: getfield      #2                  // Field value:I
     *        5: dup_x1
     *        6: iconst_1
     *        7: iadd
     *        8: putfield      #2                  // Field value:I
     *       11: ireturn
     *
     */
    @ThreadSafe
    @GuardedBy("this")
    public synchronized int getAndSetNextValue01() {
        return value++;
    }

    /**
     *   public int getAndSetNextValue02();
     *     Code:
     *        0: aload_0
     *        1: dup
     *        2: astore_1
     *        3: monitorenter
     *        4: aload_0
     *        5: dup
     *        6: getfield      #2                  // Field value:I
     *        9: dup_x1
     *       10: iconst_1
     *       11: iadd
     *       12: putfield      #2                  // Field value:I
     *       15: aload_1
     *       16: monitorexit
     *       17: ireturn
     *       18: astore_2
     *       19: aload_1
     *       20: monitorexit
     *       21: aload_2
     *       22: athrow
     *     Exception table:
     *        from    to  target type
     *            4    17    18   any
     *           18    21    18   any
     *
     */
    @ThreadSafe
    @GuardedBy("this")
    public int getAndSetNextValue02() {
        synchronized (this) {
            return value++;
        }
    }

    public static class ThreadA extends Thread {

        private static Object lock = new Object();

        private SafeSequence unsafeSequence;

        public ThreadA(SafeSequence unsafeSequence) {
            this.unsafeSequence = unsafeSequence;
        }

        @Override
        @GuardedBy("lock")
        public void run() {
            while(true) {
                int value = 0;
                int nextValue = 0;

                synchronized (lock) {
                    value = unsafeSequence.getAndSetNextValue01();
                    nextValue = unsafeSequence.getValue();
                }

                if(value + 1 != nextValue) {
                    throw new ThreadUnsafeException("thread unsafe:\t" + value + "\t" + nextValue);
                }
            }
        }
    }

    public static void main(String[] args) {
        SafeSequence unsafeSequence = new SafeSequence();
        ThreadA threadA1 = new ThreadA(unsafeSequence);
        ThreadA threadA2 = new ThreadA(unsafeSequence);

        threadA1.start();
        threadA2.start();
    }

}
