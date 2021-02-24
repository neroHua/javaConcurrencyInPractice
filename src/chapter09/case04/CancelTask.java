package chapter09.case04;

import com.sun.javafx.runtime.async.BackgroundExecutor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Future;

public class CancelTask {

	static Future<?> messageFuture;

	public static void main(String[] args) {
		JFrame hws = new JFrame("Hello World Swing");
		JLabel hw = new JLabel("Hello World!");
		hws.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		hws.setVisible(true);
		hws.setSize(320, 240);
		hws.getContentPane().add(hw);
		hw.setHorizontalAlignment(SwingConstants.CENTER);
		
		final JButton jButtonStart = new JButton("message");
		final JButton jButtonCancel = new JButton("cancell");

		jButtonStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jButtonStart.setEnabled(false);
				jButtonStart.setText("busy");

				messageFuture = BackgroundExecutor.getExecutor().submit(new Runnable() {
					
					@Override
					public void run() {
						try {
							// do some thing need a lot time to do
							Thread.currentThread().sleep(1000);
						}
						catch (InterruptedException e) {
							Thread.currentThread().interrupt();
						}
						finally {
							BackgroundExecutor.getExecutor().execute(new Runnable() {
								
								@Override
								public void run() {
									jButtonStart.setEnabled(true);
									jButtonStart.setText("message");
								}

							});
						}
					}

				});
			}
			
		});
		
		jButtonCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (null == messageFuture) {
					throw new IllegalStateException();
				}
				messageFuture.cancel(true);
			}

		});

		JPanel contentPane = new JPanel();
		contentPane.add(jButtonStart);
		contentPane.add(jButtonCancel);

	    hws.setContentPane(contentPane);	
	}
}
