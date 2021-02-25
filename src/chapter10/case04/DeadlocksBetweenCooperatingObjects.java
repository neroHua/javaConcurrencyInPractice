package chapter10.case04;

import common.GuardedBy;

import java.util.HashSet;
import java.util.Set;

public class DeadlocksBetweenCooperatingObjects {

    class Taxi {

        @GuardedBy("this")
        private Point location;
        @GuardedBy("this")
        private Point destination;

        private final Dispatcher dispatcher;

        public Taxi(Dispatcher dispatcher) {
            this.dispatcher = dispatcher;
        }

        public synchronized Point getLocation() {
            return location;
        }

        public synchronized void setLocation(Point location) {
            this.location = location;
            if (location.equals(destination)) {
                dispatcher.notifyAvailable(this);
            }
        }

    }

    class Dispatcher {

        @GuardedBy("this")
        private final Set<Taxi> taxis;
        @GuardedBy("this")
        private final Set<Taxi> availableTaxis;

        public Dispatcher() {
            taxis = new HashSet<Taxi>();
            availableTaxis = new HashSet<Taxi>();
        }

        public synchronized void notifyAvailable(Taxi taxi) {
            availableTaxis.add(taxi);
        }

        public synchronized Image getImage() {
            Image image = new Image();
            for (Taxi t : taxis) {
                image.drawMarker(t.getLocation());
            }
            return image;
        }

    }

    private class Point {
    }

    private class Image {

        public void drawMarker(Point location) {
        }

    }

}
