package chapter02.case06;

public class Reentrancy {

    public class Widget {
        public synchronized void doSomething() {
        }
    }

    public class LoggingWidget extends Widget {
        public synchronized void doSomething() {
            System.out.println(toString() + ": calling doSomething");
            super.doSomething();
        }
    }

}
