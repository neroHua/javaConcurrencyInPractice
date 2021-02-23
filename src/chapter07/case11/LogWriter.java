package chapter07.case11;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LogWriter {

    private final BlockingQueue<String> queue;
    private final LoggerThread logger;

    private volatile boolean shutdown = false;

    public LogWriter(PrintWriter writer) {
        this.queue = new LinkedBlockingQueue<String>(1024);
        this.logger = new LoggerThread(writer);
    }

    public void start() {
        logger.start();
    }

    public void shutdown() {
        this.shutdown = true;
    }

    public void log(String msg) throws InterruptedException {
        if (!shutdown) {
            queue.put(msg);
        }
    }

    private class LoggerThread extends Thread {

        private final PrintWriter writer;

        public LoggerThread(PrintWriter writer) {
            this.writer = writer;
        }

        public void run() {
            try {
                while (true) {
                    writer.println(queue.take());
                }
            } catch (InterruptedException ignored) {
            } finally {
                writer.close();
            }
        }

    }

}
