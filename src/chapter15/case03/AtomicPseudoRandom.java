package chapter15.case03;

import common.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class AtomicPseudoRandom {

    private AtomicInteger seed;

    AtomicPseudoRandom(int seed) {
        this.seed = new AtomicInteger(seed);
    }

    public int nextInt(int n) {
        while (true) {
            int s = seed.get();
            int nextSeed = calculateNext(s);
            if (seed.compareAndSet(s, nextSeed)) {
                int remainder = s % n;
                return remainder > 0 ? remainder : remainder + n;
            }
        }
    }

    private int calculateNext(int s) {
        return 0;
    }

}
