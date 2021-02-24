package chapter09.case03;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.sun.javafx.runtime.async.BackgroundExecutor;

public class LongTimeTask {

	public static void main(String[] args) {
		JFrame hws = new JFrame("Hello World Swing");
		JLabel hw = new JLabel("Hello World!");
		hws.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		hws.setVisible(true);
		hws.setSize(320, 240);
		hws.getContentPane().add(hw);
		hw.setHorizontalAlignment(SwingConstants.CENTER);
		
		final JButton jButton = new JButton("chick");
		
		jButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jButton.setEnabled(false);
				jButton.setText("busy");

				BackgroundExecutor.getExecutor().execute(new Runnable() {
					
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
									jButton.setEnabled(true);
									jButton.setText("chick");
								}

							});
						}
					}

				});
			}
			
		});

		JPanel contentPane = new JPanel( );
		contentPane.add(jButton);

	    hws.setContentPane(contentPane);	
	}
}
