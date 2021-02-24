package chapter09.case02;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class ColorChange {

	public static void main(String[] args) {
		JFrame hws = new JFrame("Hello World Swing");
		JLabel hw = new JLabel("Hello World!");
		hws.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		hws.setVisible(true);
		hws.setSize(320, 240);
		hws.getContentPane().add(hw);
		hw.setHorizontalAlignment(SwingConstants.CENTER);
		
		final Random random = new Random();
		final JButton jButton = new JButton("change color");
		
		jButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jButton.setBackground(new Color(random.nextInt()));
			}
			
		});

		JPanel contentPane=new JPanel( );
		contentPane.add(jButton);

	    hws.setContentPane(contentPane);	
	}
}
