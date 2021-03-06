package blog.case01;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * producer stop to wait for consumer notice when producer count reach the max
 * 生产者在数量达到最大值时，停止并等待消费者唤醒
 * consumer stop to wait for producer notice when producer count reach the min
 * 消费者在数量达到最小值时，停止并等待生产者唤醒
 *
 * although this implement looks strange....
 * 尽管下面的实现看起来和奇怪...
 *
 * the reason for use AtomicInteger instead of a int or Integer is stale data
 * 使用原子整型而不使用基本类型整型和包装类型整型的原因是：过期数据
 */
public class IntrinsicLock {

    public static final int COUNT_MAX = 100;
    public static final int COUNT_MIN = 20;
    public static final Object lock = new Object();

    public class ProducerThread extends Thread {

        public AtomicInteger count;

        public ProducerThread(AtomicInteger count) {
            this.count = count;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    while (count.get() >= COUNT_MAX) {
                        try {
                            System.out.println(Thread.currentThread().getName() + ":\t producer reach the max: start to wait");
                            lock.wait();
                            System.out.println(Thread.currentThread().getName() + ":\t producer wake up");
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }

                    count.incrementAndGet();

                    System.out.println(Thread.currentThread().getName() + ":\t producer working");
                    if (count.get() >= COUNT_MAX) {
                        lock.notifyAll();
                        System.out.println(Thread.currentThread().getName() + ":\t producer reach the max: notice consumer to consume");
                    }
                }
            }
        }

    }

    public class ConsumerThread extends Thread {

        public AtomicInteger count;

        public ConsumerThread(AtomicInteger count) {
            this.count = count;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    while (count.get() <= COUNT_MIN) {
                        try {
                            System.out.println(Thread.currentThread().getName() + ":\t consumer reach the min: start to wait");
                            lock.wait();
                            System.out.println(Thread.currentThread().getName() + ":\t consumer wake up");
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }

                    count.decrementAndGet();
                    System.out.println(Thread.currentThread().getName() + ":\t consumer working");

                    if (count.get() <= COUNT_MIN) {
                        System.out.println(Thread.currentThread().getName() + ":\t consumer reach the min: notice producer to produce");
                        lock.notifyAll();
                    }
                }
            }
        }

    }

    public static void main(String[] args) {
        IntrinsicLock intrinsicLock = new IntrinsicLock();
        AtomicInteger count = new AtomicInteger(60);

        ProducerThread producerThread = intrinsicLock.new ProducerThread(count);
        producerThread.setName("produce");

        ConsumerThread consumerThread = intrinsicLock.new ConsumerThread(count);
        consumerThread.setName("consumer");

        producerThread.start();
        consumerThread.start();
    }

}
