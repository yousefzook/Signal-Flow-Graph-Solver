package view;

import java.awt.GridLayout;
import javax.swing.JFrame;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static MainFrame frame;

	public static MainFrame getFrame() {
		if (frame == null) {
			frame = new MainFrame();
			frame.initialGUI();
		}
		return frame;
	}

	private MainFrame() {
		super("SignalFlowGraph");
	}

	private void initialGUI() {
		setLayout(new GridLayout(1, 2, 0, 0));
		new InputPanel();
		new MainPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}
}
