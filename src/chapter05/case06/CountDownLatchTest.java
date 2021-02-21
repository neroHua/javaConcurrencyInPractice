package chapter05.case06;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

    private final int COUNT = 1;
    private final CountDownLatch countDownLatch = new CountDownLatch(COUNT);

    public class WorkingThread extends Thread {

        @Override
        public void run() {
            System.out.println("start doing job....");
            countDownLatch.countDown();
            System.out.println("finish doing job....");
        }

    }

    public class WaitingThread extends Thread {

        @Override
        public void run() {
            System.out.println("waiting for start......");
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("finish...");
        }
    }

    public static void main(String[] args) {
        CountDownLatchTest countDownLatchTest = new CountDownLatchTest();
        WaitingThread waitingThread01 = countDownLatchTest.new WaitingThread();
        WaitingThread waitingThread02 = countDownLatchTest.new WaitingThread();
        WorkingThread workingThread01 = countDownLatchTest.new WorkingThread();
        WorkingThread workingThread02 = countDownLatchTest.new WorkingThread();

        waitingThread01.setName("waitingThread01");
        waitingThread01.start();
        waitingThread02.setName("waitingThread02");
        waitingThread02.start();

        workingThread01.setName("workingThread01");
        workingThread01.start();
        workingThread02.setName("workingThread02");
        workingThread02.start();
    }
}
