import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoadingFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8469762178441263341L;
	/**
	 * 
	 */
	JButton button;

	public LoadingFrame() {
		button = new JButton("ENTER");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Application entered");
			}

		});
		setLayout(new BorderLayout());
		add(button, BorderLayout.CENTER);
	}

	public void startLoading() {
		final Component glassPane = getGlassPane();
		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		final JLabel label = new JLabel();
		panel.add(label, BorderLayout.SOUTH);
		setGlassPane(panel);
		panel.setVisible(true);
		panel.setOpaque(false);
		button.setEnabled(false);

		Thread thread = new Thread() {
			@Override
			public void run() {
				for (int i = 5; i > 0; i--) {
					label.setText("Loading ... " + i);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// loading finished
				setGlassPane(glassPane);
				button.setEnabled(true);
			}
		};
		thread.start();
	}

	/*public static void main(String[] args) {
		LoadingFrame frame = new LoadingFrame();
	}*/
}