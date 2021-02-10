package chapter03.case01;

public class NoVisibility {

    private boolean ready;
    private int number;

    private class ReaderThread extends Thread {

        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            if(0 == number) {
                System.out.println("number:\t" + number);
            }
        }

    }

    public static void main(String[] args) {
        while (true) {
            NoVisibility noVisibility = new NoVisibility();
            ReaderThread readerThread = noVisibility.new ReaderThread();
            readerThread.start();

            noVisibility.number = 42;
            noVisibility.ready = true;
        }
    }

}
