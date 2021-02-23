package chapter07.case18;

public class TypicalWorker extends Thread {

    @Override
    public void run() {
        Throwable thrown = null;
        try {
            while (!isInterrupted()) {
                runTask(getTaskFromWorkQueue());
            }
        } catch (Throwable e) {
            thrown = e;
        } finally {
            threadExited(this, thrown);
        }
    }

    private void threadExited(TypicalWorker typicalWorker, Throwable thrown) {
    }

    private void runTask(Task task) {
        task.run();
    }

    private Task getTaskFromWorkQueue() {
        return null;
    }

    private class Task {

        public void run() {
        }

    }

}
