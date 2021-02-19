package chapter04.case09;

import common.ThreadSafe;

import java.util.Vector;

@ThreadSafe
public class BetterVector<E> extends Vector<E> {

    public synchronized boolean putIfAbsent(E x) {
        boolean absent = !contains(x);

        if (absent) {
            add(x);
        }

        return absent;
    }

}
