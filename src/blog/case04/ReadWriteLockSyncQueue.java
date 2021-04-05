package blog.case03;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * this class is used to DEBUG sync queue
 * 这个类用来调试同步队列
 *
 */
public class ReadWriteLockSyncQueue {

    public static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    public static final Lock readLock = readWriteLock.readLock();
    public static final Lock writeLock = readWriteLock.writeLock();

    public class ReaderThread extends Thread {

        public int count;

        public ReaderThread(int count) {
            this.count = count;
        }

        @Override
        public void run() {
            while (true) {
                for (int i = 0; i < 1000; i++) {
                    i++;
                }
                try {
                    readLock.lock();
                    for (int i = 0; i < 1000; i++) {
                        i++;
                    }
                    System.out.println(count);
                }
                finally {
                    readLock.unlock();
                }

            }
        }

    }

    public class WriteThread extends Thread {

        public int count;

        public WriteThread(int count) {
            this.count = count;
        }

        @Override
        public void run() {

            while (true) {
                for (int i = 0; i < 1000; i++) {
                    i++;
                }
                try {
                    writeLock.lock();
                    for (int i = 0; i < 1000; i++) {
                        i++;
                    }
                    count++;
                }
                finally {
                    writeLock.unlock();
                }
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        ReadWriteLockSyncQueue explicitLock = new ReadWriteLockSyncQueue();
        int count = 0;

        ReaderThread readerThread = explicitLock.new ReaderThread(count);
        readerThread.setName("reader");

        WriteThread writeThread = explicitLock.new WriteThread(count);
        writeThread.setName("write");

        readerThread.start();
        writeThread.start();
    }

}

