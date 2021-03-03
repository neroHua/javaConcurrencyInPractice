package chapter15.case02;

import common.Immutable;

import java.util.concurrent.atomic.AtomicReference;

public class CASNumberRange {

    private final AtomicReference<IntPair> values = new AtomicReference<IntPair>(new IntPair(0, 0));

    public int getLower() {
        return values.get().lower;
    }

    public int getUpper() {
        return values.get().upper;
    }

    public void CASLower(int i) {
        while (true) {
            IntPair oldv = values.get();
            if (i > oldv.upper) {
                throw new IllegalArgumentException("Can't set lower to " + i + " > upper");
            }

            IntPair newv = new IntPair(i, oldv.upper);
            if (values.compareAndSet(oldv, newv)) {
                return;
            }
        }
    }

    public void CASUpper(int i) {
        while (true) {
            IntPair oldv = values.get();
            if (i < oldv.lower) {
                throw new IllegalArgumentException("Can't set lower to " + i + " > upper");
            }

            IntPair newv = new IntPair(oldv.lower, i);
            if (values.compareAndSet(oldv, newv)) {
                return;
            }
        }
    }

    @Immutable
    private static class IntPair {

        // Invariant: lower <= upper
        final int lower;
        final int upper;

        public IntPair(int lower, int upper) {
            this.lower = lower;
            this.upper = upper;
        }

    }

}
