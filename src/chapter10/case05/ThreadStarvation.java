package chapter10.case05;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadStarvation {

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public class TaskA implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("TaskA1");
            }

            executorService.execute(new TaskB());

            for (int i = 0; i < 10; i++) {
                System.out.println("TaskA2");
            }
        }

    }

    public class TaskB implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("TaskB1");
            }
            for (int i = 0; i < 10; i++) {
                System.out.println("TaskB2");
            }
        }

    }

    public static void main(String[] args) {
        ThreadStarvation threadStarvation = new ThreadStarvation();
        TaskA taskA = threadStarvation.new TaskA();
        threadStarvation.executorService.execute(taskA);
        threadStarvation.executorService.shutdown();
    }

}
