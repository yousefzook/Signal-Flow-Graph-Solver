package view;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private static MainPanel panel;

	public MainPanel() {
		panel = this;
		frame = MainFrame.getFrame();
		intitializePanel();
	}

	public static MainPanel getPanel() {
		return panel;
	}

	private void intitializePanel() {
		setLayout(new BorderLayout());
		setBackground(Color.white);
		frame.add(this);
		// JLabel contentPane = new JLabel();
		// contentPane.setLayout(new BorderLayout());
		// frame.setContentPane(contentPane);
		// setSize(800, 900);
		// frame.getContentPane().add(this);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// frame.setUndecorated(true);
		frame.setVisible(true);
	}
}
