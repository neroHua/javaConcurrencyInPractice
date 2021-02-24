package chapter09.case06;

import com.sun.javafx.runtime.async.BackgroundExecutor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executor;

public class CancelLongTimeTask {

    Executor backgroundExec = BackgroundExecutor.getExecutor();
    Button startButton = new Button();
    Button cancelButton = new Button();
    Label label = new Label();

    public void runInBackground(final Runnable task) {
        startButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                final CancelListener listener = new CancelListener();
                listener.task = new BackgroundTask<Void>() {

                    public Void compute() {
                        while (moreWork() && !isCancelled()) {
                            doSomeWork();
                        }
                        return null;
                    }

                    public void onCompletion(boolean cancelled, String s, Throwable exception) {
                        cancelButton.removeActionListener(listener);
                        label.setText("done");
                    }

                };
                cancelButton.addActionListener(listener);
                backgroundExec.execute(task);
            }

        });
    }

    private void doSomeWork() {
    }

    private boolean isCancelled() {
        return false;
    }

    private boolean moreWork() {
        return false;
    }

    class CancelListener implements ActionListener {

        BackgroundTask<?> task;

        public void actionPerformed(ActionEvent event) {
            if (task != null) {
                task.cancel(true);
            }
        }

    }

    class BackgroundTask<T> {

        public void cancel(boolean b) {
        }

    }

}
