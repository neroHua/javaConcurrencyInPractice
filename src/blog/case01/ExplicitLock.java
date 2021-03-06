package blog.case01;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
public class ExplicitLock {

    public static final int COUNT_MAX = 100;
    public static final int COUNT_MIN = 20;
    public static final Lock lock = new ReentrantLock();
    public static final Condition condition = lock.newCondition();

    public class ProducerThread extends Thread {

        public AtomicInteger count;

        public ProducerThread(AtomicInteger count) {
            this.count = count;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    lock.lock();

                    while (count.get() >= COUNT_MAX) {
                        try {
                            System.out.println(Thread.currentThread().getName() + ":\t producer reach the max: start to wait");
                            condition.await();
                            System.out.println(Thread.currentThread().getName() + ":\t producer wake up");
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }

                    count.incrementAndGet();
                    System.out.println(Thread.currentThread().getName() + ":\t producer working");

                    if (count.get() >= COUNT_MAX) {
                        condition.signalAll();
                        System.out.println(Thread.currentThread().getName() + ":\t producer reach the max: notice consumer to consume");
                    }
                }
                finally {
                    lock.unlock();
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
                try {
                    lock.lock();

                    while (count.get() <= COUNT_MIN) {
                        try {
                            System.out.println(Thread.currentThread().getName() + ":\t consumer reach the min: start to wait");
                            condition.await();
                            System.out.println(Thread.currentThread().getName() + ":\t consumer wake up");
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }

                    count.decrementAndGet();
                    System.out.println(Thread.currentThread().getName() + ":\t consumer working");

                    if (count.get() <= COUNT_MIN) {
                        System.out.println(Thread.currentThread().getName() + ":\t consumer reach the min: notice producer to produce");
                        condition.signalAll();
                    }
                }
                finally {
                    lock.unlock();
                }
            }
        }

    }

    public static void main(String[] args) {
        ExplicitLock explicitLock = new ExplicitLock();
        AtomicInteger count = new AtomicInteger(60);

        ProducerThread producerThread = explicitLock.new ProducerThread(count);
        producerThread.setName("produce");

        ConsumerThread consumerThread = explicitLock.new ConsumerThread(count);
        consumerThread.setName("consumer");

        producerThread.start();
        consumerThread.start();
    }

}

