package chapter01.case01;

import common.NotThreadSafe;
import common.ThreadUnsafeException;

public class UnsafeSequence {
    private int value;

    public int getValue() {
        return value;
    }

    /**
     *   public int getAndSetNextValue();
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
     *      description:
     *         0: // Push local variable 0 (this)
     *         1: // Duplicate the top value on the operand stack and push the duplicated value onto the operand stack.
     *         2: // Fetch field from object
     *         6: // Push the int constant <i> (-1, 0, 1, 2, 3, 4 or 5) onto the operand stack.
     *         7: // Add int
     *         8: // Set field in object
     *        11: // Return int from method
     *
     *      Operand stack:
     *          0: ....., this
     *          1: ....., this, this
     *          2: ....., this, this.value
     *          5: ....., this.value, this, this.value
     *          6: ....., this.value, this, this.value, 1
     *          7: ....., this.value, this, this.value + 1
     *          8: ....., this.value
     *         11: .....,
     *
     */
    @NotThreadSafe
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
