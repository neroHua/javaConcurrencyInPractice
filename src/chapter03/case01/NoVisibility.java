package chapter03.case01;

/**
 * visibility
 * 可见性
 *
 * if there is a guarantee : the reading thread will see a value written by another thread on a timely basis
 * 是否有一个保证：一个读的线程是否可以及时地看见被其他线程写的值
 *
 */
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
