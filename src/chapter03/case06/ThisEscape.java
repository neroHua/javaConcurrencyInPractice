package chapter03.case06;

import java.awt.*;
import java.util.EventListener;

public class ThisEscape {

    public ThisEscape(EventSource eventSource) {
        eventSource.registerListener(
                new EventListener() {
                    public void onEvent(Event e) {
                        doSomething(e);
                    }

                    private void doSomething(Event e) {
                    }
                });
    }

    public class EventSource {

        public void registerListener(EventListener eventListener) {
        }

    }

}
