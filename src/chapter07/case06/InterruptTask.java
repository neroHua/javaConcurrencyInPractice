package chapter07.case06;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InterruptTask {

    private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);

    public static void timedRun(final Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        RethrowableTask task = new RethrowableTask(r);
        final Thread taskThread = new Thread(task);
        taskThread.start();

        scheduledExecutorService.schedule(new Runnable() {

            public void run() {
                taskThread.interrupt();
            }

        }, timeout, unit);

        taskThread.join(unit.toMillis(timeout));
        task.rethrow();
    }

    public static class RethrowableTask implements Runnable {

        private volatile Throwable t;

        private Runnable r;

        public RethrowableTask(Runnable r) {
            this.r = r;
        }

        public void run() {
            try {
                r.run();
            } catch (Throwable t) {
                this.t = t;
            }
        }

        void rethrow() {
            if (t != null) {
                throw new RuntimeException(t);
            }
        }

    }

}
