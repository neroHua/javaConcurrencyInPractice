package chapter03.case07;

import java.awt.*;
import java.util.EventListener;

public class SafeListener {

    private final EventListener listener;

    private SafeListener() {
        listener = new EventListener() {
            public void onEvent(Event e) {
                doSomething(e);
            }

            private void doSomething(Event e) {
            }
        };
    }

    public static SafeListener newInstance(EventSource source) {
        SafeListener safe = new SafeListener();
        source.registerListener(safe.listener);
        return safe;
    }

    public class EventSource {

        public void registerListener(EventListener eventListener) {
        }

    }

}
