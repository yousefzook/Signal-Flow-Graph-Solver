package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import Model.Facade;

public class InputPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel nodLabel, branchesLabel;
	private JTextField nodesField;
	private JTextArea branInfo;
	private JButton start;
	private JFrame frame;

	public InputPanel() {
		frame = MainFrame.getFrame();
		build();
	}

	private void build() {
		// node field
		nodesField = new JTextField();
		nodesField.setPreferredSize(new Dimension(40, 35));
		nodesField.setFont(new Font("Consolas", Font.PLAIN, 22));
		nodesField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
		// labels
		nodLabel = new JLabel("Enter Number of Nodes:");
		nodLabel.setFont(new Font("Arial", Font.BOLD, 22));
		nodLabel.setForeground(Color.WHITE);
		branchesLabel = new JLabel("Enter branches information ( frome, to, gain ):");
		branchesLabel.setFont(new Font("Arial", Font.BOLD, 22));
		branchesLabel.setForeground(Color.WHITE);
		// text area
		branInfo = new JTextArea(20, 40);
		branInfo.setFont(new Font("Consolas", Font.PLAIN, 20));
		branInfo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
		//
		setButton();
		display();

	}

	private void display() {
		setBorder(BorderFactory.createEmptyBorder(100, 5, 5, 5));
		setLayout(null);
		setBackground(new Color(67, 77, 89));
		nodLabel.setBounds(50, 0, 600, 100);
		nodesField.setBounds(350, 30, 100, 50);
		branchesLabel.setBounds(50, 200, 800, 50);
		add(nodLabel);
		add(nodesField);
		add(branchesLabel);
		JScrollPane scroll = new JScrollPane(branInfo);
		scroll.createVerticalScrollBar();
		scroll.setBounds(50, 300, 500, 500);
		add(scroll);
		start.setBounds(700,820, 200, 100);
		add(start);
		frame.add(this);
		setVisible(true);
	}

	private void setButton() {
		start = new JButton("Solve");
		start.setFont(new Font("Arial", Font.BOLD, 25));
		start.setForeground(Color.black);
		start.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		start.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
		start.setBackground(new Color(255, 255, 255));
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Facade.getFacade().start(nodesField.getText(), branInfo.getText());
			}
		});
	}
}
