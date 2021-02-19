package chapter04.case03;

import common.GuardedBy;
import common.ThreadSafe;

@ThreadSafe
public class PrivateLock {

    private final Object lock = new Object();

    @GuardedBy("this")
    Widget widget;

    public void someMethod() {
       synchronized (lock) {
           // access or modify the state of widget
       }
    }

    private class Widget {

    }

}
