package chapter01.case01;

import common.NotThreadSafe;
import common.ThreadUnsafeException;

@NotThreadSafe
public class UnsafeSequence {
    private int value;

    public int getValue() {
        return value;
    }

    public int getAndSetNextValue() {
        return value++;
    }

    public static class ThreadA extends Thread {

        private UnsafeSequence unsafeSequence;

        public ThreadA(UnsafeSequence unsafeSequence) {
            this.unsafeSequence = unsafeSequence;
        }

        @Override
        public void run() {
            while(true) {
                int value = unsafeSequence.getAndSetNextValue();
                int nextValue = unsafeSequence.getValue();

                if(value + 1 != nextValue) {
                    throw new ThreadUnsafeException("thread unsafe:\t" + value + "\t" + nextValue);
                }
            }
        }
    }

    public static void main(String[] args) {
        UnsafeSequence unsafeSequence = new UnsafeSequence();
        ThreadA threadA1 = new ThreadA(unsafeSequence);
        ThreadA threadA2 = new ThreadA(unsafeSequence);

        threadA1.start();
        threadA2.start();
    }

}
