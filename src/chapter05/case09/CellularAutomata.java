package chapter05.case09;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier
 * 循环挡板
 *
 * using:
 * 使用
 *
 * 1. construct with count which equals to the number of working thread and with waiting 'thread'
 * 1. 创建对象同时设置数量初值等于工作线程的数量,和等待的线程
 *
 * 2. start working thread and waiting thread will work once all working thread finish and count will be reset
 * 2. 启动工作线程, 等待线程会在所有工作线程完成后自动工作, 计数器会被重置
 *
 * 3. code
 * 3. 编码
 *      invoking CyclicBarrier.await when finish working thread
 *      在工作线程完成的地方调用CyclicBarrier.await
 *      construct cyclicBarrier with waiting thread which waiting the working thread and waiting thread will start to work when the count down to 0
 *      构造循环挡板时同时创建等待工作线程完成的线程。等待线程会在计数器等于0的时候，开始工作
 *
 * CyclicBarrier.await use LockSupport.unpark to unblock thread
 * LockSupport.unpark use sun.misc.Unsafe.unpark to unblock thread
 *
 * CyclicBarrier.await use LockSupport.park to block thread
 * LockSupport.park use sun.misc.Unsafe.park to block thread
 *
 */
public class CellularAutomata {

    private final Board mainBoard;
    private final CyclicBarrier barrier;
    private final Worker[] workers;

    public CellularAutomata(Board board) {
        this.mainBoard = board;
        int count = Runtime.getRuntime().availableProcessors();
        this.barrier = new CyclicBarrier(count,
                new Runnable() {

                    @Override
                    public void run() {
                        mainBoard.commitNewValues();
                    }

                });
        this.workers = new Worker[count];

        for (int i = 0; i < count; i++) {
            workers[i] = new Worker(mainBoard.getSubBoard(count, i));
        }
    }

    public void start() {
        for (int i = 0; i < workers.length; i++) {
            new Thread(workers[i]).start();
        }

        mainBoard.waitForConvergence();
    }

    private class Board {

        public void commitNewValues() {

        }

        public void waitForConvergence() {

        }

        public Board getSubBoard(int count, int i) {
            return null;
        }

        public boolean hasConverged() {
            return false;
        }

        public int getMaxX() {
            return 0;
        }

        public int getMaxY() {
            return 0;
        }

        public void setNewValue(int x, int y, Object computeValue) {
        }

    }

    private class Worker implements Runnable {

        private final Board board;

        public Worker(Board board) {
            this.board = board;
        }

        public void run() {
            while (!board.hasConverged()) {
                for (int x = 0; x < board.getMaxX(); x++) {
                    for (int y = 0; y < board.getMaxY(); y++) {
                        board.setNewValue(x, y, computeValue(x, y));
                    }
                }

                try {
                    barrier.await();
                } catch (InterruptedException ex) {
                    return;
                } catch (BrokenBarrierException ex) {
                    return;
                }
            }
        }

        private Object computeValue(int x, int y) {
            return null;
        }

    }

}
