package chapter07.case05;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SchedulingInterruptThread {

    private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);

    public static void timedRun(Runnable runnable, long timeout, TimeUnit timeUnit) {
        Thread currentThread = Thread.currentThread();
        scheduledExecutorService.schedule(new Runnable() {

            @Override
            public void run() {
                currentThread.interrupt();
            }

        }, timeout, timeUnit);

        runnable.run();
    }

}
