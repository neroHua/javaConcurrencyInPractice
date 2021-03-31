package blog.case02;

import java.util.concurrent.atomic.AtomicInteger;
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
 *
 * this class is used to DEBUG sync queue
 * 这个类用来调试同步队列
 *
 */
public class ExplicitLockSyncQueue {

    public static final Lock lock = new ReentrantLock();

    public class ProducerThread extends Thread {

        public AtomicInteger count;

        public ProducerThread(AtomicInteger count) {
            this.count = count;
        }

        @Override
        public void run() {
            while (true) {
                for (int i = 0; i < 1000; i++) {
                    i++;
                }
                try {
                    lock.lock();
                    for (int i = 0; i < 1000; i++) {
                        i++;
                    }
                    count.incrementAndGet();
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
                for (int i = 0; i < 1000; i++) {
                    i++;
                }
                try {
                    lock.lock();
                    for (int i = 0; i < 1000; i++) {
                        i++;
                    }
                    count.decrementAndGet();
                }
                finally {
                    lock.unlock();
                }
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        ExplicitLockSyncQueue explicitLock = new ExplicitLockSyncQueue();
        AtomicInteger count = new AtomicInteger(60);

        ProducerThread producerThread = explicitLock.new ProducerThread(count);
        producerThread.setName("produce");

        ConsumerThread consumerThread = explicitLock.new ConsumerThread(count);
        consumerThread.setName("consumer");

        producerThread.start();
        consumerThread.start();
    }

}

