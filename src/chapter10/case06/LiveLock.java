package chapter10.case06;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class LiveLock {

    private static Lock liveLock;

    public LiveLock(Lock liveLock) {
        this.liveLock = liveLock;
    }

    public static class LiveLockLock implements Lock {

        private static Set<Thread> threadSet;

        public LiveLockLock(Set<Thread> threadSet) {
            this.threadSet = threadSet;
        }

        @Override
        public void lock() {
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {
        }

        @Override
        public boolean tryLock() {
            threadSet.add(Thread.currentThread());
            if (threadSet.size() >= 2) {
                return false;
            }
            return true;
        }

        @Override
        public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            return false;
        }

        @Override
        public void unlock() {
        }

        @Override
        public Condition newCondition() {
            return null;
        }
    }

    public static class TaskA extends Thread {

        @Override
        public void run() {
            while (!liveLock.tryLock()) {
                System.out.println("TaskA try lock failed");
            }
        }

    }

    public static class TaskB extends Thread {

        @Override
        public void run() {
            while (!liveLock.tryLock()) {
                System.out.println("TaskB try lock failed");
            }
        }

    }

    public static void main(String[] args) {
        TaskA taskA = new TaskA();
        TaskB taskB = new TaskB();

        Set<Thread> threadSet = new HashSet<>();
        threadSet.add(taskA);
        threadSet.add(taskB);
        LiveLockLock liveLockLock = new LiveLockLock(threadSet);

        LiveLock liveLock = new LiveLock(liveLockLock);

        taskA.start();
        taskB.start();
    }
}
