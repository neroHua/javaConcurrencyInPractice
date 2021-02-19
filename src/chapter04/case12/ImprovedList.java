package chapter04.case12;

import common.ThreadSafe;

import java.util.List;

@ThreadSafe
public abstract class ImprovedList<T> implements List<T> {

    private final List<T> list;

    public ImprovedList(List<T> list) {
        this.list = list;
    }

    public synchronized boolean putIfAbsent(T x) {
        boolean contains = list.contains(x);
        if (contains) {
            list.add(x);
        }

        return !contains;
    }

    public synchronized void clear() {
        list.clear();
    }

}
