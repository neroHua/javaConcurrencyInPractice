package chapter05.case06;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch
 * 向下数的锁
 *
 * using:
 * 使用
 *
 * 1. construct with count which equals to the number of working thread
 * 1. 创建对象同时设置数量初值等于工作线程的数量
 *
 * 2. start working thread and thread waiting for working thread finish
 * 2. 启动工作线程和等待工作线程完成的线程
 *
 * 3. code
 * 3. 编码
 *      invoking CountDownLatch.countDown when finish working thread
 *      在工作线程完成的地方调用CountDownLatch.countDown
 *      invoking CountDownLatch.await before the code you want to run after working thread finished
 *      在等待工作线程完成的线程等待的地方之前调用CountDownLatch.await
 *
 * CountDownLatch.countDown use LockSupport.unpark to unblock thread
 * LockSupport.unpark use sun.misc.Unsafe.unpark to unblock thread
 *
 * CountDownLatch.await use LockSupport.park to block thread
 * LockSupport.park use sun.misc.Unsafe.park to block thread
 *
 */
public class TestHarness {

    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {

        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread() {

                @Override
                public void run() {
                    try {
                        startGate.await();
                        try {
                            task.run();
                        }
                        finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException ignored) {
                        Thread.currentThread().interrupt();
                    }
                }

            };

            t.start();
        }

        long start = System.nanoTime();

        startGate.countDown();
        endGate.await();

        long end = System.nanoTime();
        return end - start;
    }

}
