package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class OutputPort extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea area;
	private static OutputPort outputPort;

	public static OutputPort getOutputPort() {
		if (outputPort == null)
			outputPort = new OutputPort();
		return outputPort;
	}

	private OutputPort() {
		super("Output");
		build();
		display();
	}

	public void emptyFields() {
		area.setText("");
		setVisible(true);
	}

	public void setOutput(String op) {
		area.setText(op);
		setVisible(true);
	}

	private void build() {
		area = new JTextArea(20, 40);
		area.setFont(new Font("Consolas", Font.PLAIN, 18));
		area.setEditable(false);
		area.setBorder(BorderFactory.createEmptyBorder(5,10,5,5));
	}

	private void display() {
		FlowLayout layout = new FlowLayout();
		setLayout(layout);
		setSize(1200, 1000);
		JScrollPane info = new JScrollPane(area);
		info.createVerticalScrollBar();
		JLabel contentPane = new JLabel();
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		getContentPane().add(info);
		setVisible(true);
	}

}
