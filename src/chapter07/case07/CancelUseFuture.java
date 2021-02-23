package chapter07.case07;

import java.util.concurrent.*;

public class CancelUseFuture {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(4);

    public static void timedRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
        Future<?> task = executorService.submit(r);
        try {
            task.get(timeout, unit);
        } catch (TimeoutException e) {
            // task will be cancelled below
            throw e;
        } catch (ExecutionException e) {
            // exception thrown in task; rethrow
            throw new RuntimeException(e.getCause());
        } finally {
            // Harmless if task already completed
            task.cancel(true); // interrupt if running
        }
    }

}
