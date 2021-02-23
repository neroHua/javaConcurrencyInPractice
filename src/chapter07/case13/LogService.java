package chapter07.case13;

import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

public class LogService {

    PrintWriter writer;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void start() {
    }

    public void stop(long timeout, TimeUnit unit) throws InterruptedException {
        try {
            executorService.shutdown();
            executorService.awaitTermination(timeout, unit);
        } finally {
            writer.close();
        }
    }

    public void log(String msg) {
        try {
            executorService.execute(new WriteTask(msg));
        } catch (RejectedExecutionException ignored) {
        }
    }

    private class WriteTask implements Runnable {

        String msg;

        public WriteTask(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            writer.print(msg);
        }

    }

}
