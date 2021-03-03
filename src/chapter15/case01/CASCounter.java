package chapter15.case01;

import common.ThreadSafe;

@ThreadSafe
public class CASCounter {

    private SimulatedCAS value;

    public int getValue() {
        return value.get();
    }

    public int increment() {
        int v;
        do {
            v = value.get();
        }
        while (v != value.compareAndSwap(v, v + 1));

        return v + 1;
    }

}
