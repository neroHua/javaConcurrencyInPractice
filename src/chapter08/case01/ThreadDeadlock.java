package chapter08.case01;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadDeadlock {

    ExecutorService exec = Executors.newSingleThreadExecutor();

    public class RenderPageTask implements Callable<String> {

        public String call() throws Exception {
            Future<String> header, footer;
            header = (Future<String>) exec.submit(new LoadFileTask("header.html"));
            footer = (Future<String>) exec.submit(new LoadFileTask("footer.html"));
            String page = renderBody();
            // Will deadlock -- task waiting for result of subtask
            return header.get() + page + footer.get();
        }

        private String renderBody() {
            return null;
        }

    }

    private class LoadFileTask implements Runnable {

        String s;

        public LoadFileTask(String s) {
            this.s = s;
        }

        @Override
        public void run() {
        }

    }
}
