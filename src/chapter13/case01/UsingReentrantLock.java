package chapter13.case01;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

public class UsingReentrantLock {

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();

        try {
            reentrantLock.lock();
            doSomething1();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            reentrantLock.unlock();
        }

        try {
            reentrantLock.lock();
            doSomething2();
        }
        finally {
            reentrantLock.unlock();
        }

    }

    public static void doSomething1() throws IOException {
        throw new IOException();
    }

    public static void doSomething2() throws RuntimeException{
        throw new RuntimeException();
    }

}
