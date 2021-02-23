package chapter07.case04;

import java.util.concurrent.BlockingQueue;

public class NonCancelableTask {

    public Task getNextTask(BlockingQueue<Task> queue) {
        boolean interrupted = false;

        try {
            while (true) {
                try {
                    return queue.take();
                }
                catch (InterruptedException e) {
                    interrupted = true;
                }
            }
        }
        finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }

    }

    private class Task {
    }

}
