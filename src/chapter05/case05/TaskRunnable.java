package chapter05.case05;

import javafx.concurrent.Task;

import java.util.concurrent.BlockingQueue;

public class TaskRunnable implements Runnable {

    BlockingQueue<Task> queue;

    public void run() {
        try {
            processTask(queue.take());
        }
        catch (InterruptedException e) {
            // restore interrupted status
            Thread.currentThread().interrupt();
        }
    }

    private void processTask(Task take) {
    }

}
