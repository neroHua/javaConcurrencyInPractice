package blog.case05;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueTest {

    public final SynchronousQueue synchronousQueue;

    public SynchronousQueueTest(SynchronousQueue synchronousQueue) {
        this.synchronousQueue = synchronousQueue;
    }

    public class Producer01 extends Thread {

        private final SynchronousQueue<String> synchronousQueue;

        public Producer01(SynchronousQueue<String> synchronousQueue) {
            this.synchronousQueue = synchronousQueue;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    synchronousQueue.offer("producer01: " + i, 2000, TimeUnit.MILLISECONDS);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

    public class Producer02 extends Thread {

        private final SynchronousQueue synchronousQueue;

        public Producer02(SynchronousQueue<String> synchronousQueue) {
            this.synchronousQueue = synchronousQueue;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    synchronousQueue.put("producer02: " + i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

    public class Consumer01 extends Thread {

        private final SynchronousQueue<String> synchronousQueue;

        public Consumer01(SynchronousQueue<String> synchronousQueue) {
            this.synchronousQueue = synchronousQueue;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    String take = synchronousQueue.take();
                    System.out.println("consumer01: " + take);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

    public class Consumer02 extends Thread {

        private final SynchronousQueue<String> synchronousQueue;

        public Consumer02(SynchronousQueue<String> synchronousQueue) {
            this.synchronousQueue = synchronousQueue;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    String take = synchronousQueue.take();
                    System.out.println("consumer02: " + take);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

    public static void main(String[] args) {
        SynchronousQueue synchronousQueue = new SynchronousQueue(false);

        SynchronousQueueTest synchronousQueueTest = new SynchronousQueueTest(synchronousQueue);

        Producer01 producer01 = synchronousQueueTest.new Producer01(synchronousQueue);
        producer01.setName("producer01");
        Producer02 producer02 = synchronousQueueTest.new Producer02(synchronousQueue);
        producer02.setName("producer02");
        Consumer01 consumer01 = synchronousQueueTest.new Consumer01(synchronousQueue);
        consumer01.setName("consumer01");
        Consumer02 consumer02 = synchronousQueueTest.new Consumer02(synchronousQueue);
        consumer02.setName("consumer02");

        producer01.start();
        producer02.start();
        consumer01.start();
        consumer02.start();
    }

}
