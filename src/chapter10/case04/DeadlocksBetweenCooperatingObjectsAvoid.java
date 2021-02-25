package chapter10.case04;

import common.GuardedBy;

import java.util.HashSet;
import java.util.Set;

public class DeadlocksBetweenCooperatingObjectsAvoid {

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
            boolean reachedDestination;
            synchronized (this) {
                this.location = location;
                reachedDestination = location.equals(destination);
            }

            if (reachedDestination) {
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

        public Image getImage() {
            Set<Taxi> copy;
            synchronized (this) {
                copy = new HashSet<Taxi>(taxis);
            }

            Image image = new Image();
            for (Taxi t : copy) {
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
