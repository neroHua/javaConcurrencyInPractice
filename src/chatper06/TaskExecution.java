package chapter06;

import java.util.concurrent.*;

/**
 * task execution
 * 任务执行
 *
 * Executing Tasks in Threads
 * 任务在线程中执行
 *
 * A thread pool, as its name suggests, manages a homogeneous pool of worker threads.
 * 线程池管理者一系列的工作线程
 * A thread pool is tightly bound to a work queue holding tasks waiting to be executed.
 * 一个线程池跟一个工作队列仅仅的绑定在一起,工作队列中存放着等待执行的任务
 * Worker threads have a simple life: request the next task from the work queue, execute it, and go back to waiting for another task.
 * 工作线程有个简单的生命周期：从工作队列中获取下一个任务，执行，然后掉回头去获取和执行下一个线程
 *
 * FutureTask.run use LockSupport.unpark
 * LockSupport.unpark use sun.misc.Unsafe.unpark to unblock thread
 * FutureTask.get use LockSupport.park
 * LockSupport.park use sun.misc.Unsafe.park to block thread
 *
 */

public class TaskExecution {

    public static class TaskRunnable implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                System.out.println("aaaaaaaaaaaaaaaaaa");
            }
        }

    }

    public static class TaskCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                System.out.println("aaaaaaaaaaaaaaaaaa");
            }

            return "aaaaaaaaaaaaa";
        }

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Future<?> runnableFuture = executorService.submit(new TaskRunnable());
        runnableFuture.get();

        Future<String> callableFuture = executorService.submit(new TaskCallable());
        callableFuture.get();
    }
}
