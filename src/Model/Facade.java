package Model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import finePrint.Block;
import finePrint.Board;
import finePrint.TableView;
import view.OutputPort;

public class Facade {
	private static Solver solver;
	private int numOfnodes;
	private String[][] edgesInfo;
	public static Facade facade;
	private String output;

	public static Facade getFacade() {
		if (facade == null)
			facade = new Facade();
		return facade;
	}

	private Facade() {
		solver = new Solver();
	}

	public void start(String nodesField, String branInfo) {
		extractData(nodesField, branInfo);
		GraphBulider.buildGraph(numOfnodes, edgesInfo);
		if (solver.solve(numOfnodes, edgesInfo))
			setOutputPort();
		else {
			JOptionPane.showMessageDialog(null, "There are no Forward Paths !", "Message",
					JOptionPane.INFORMATION_MESSAGE);

		}
	}

	public void setOutputPort() {
		output = "";
		constructTable("Forward Paths", "Gains", solver.getforwardPaths(), solver.getFpGains());
		constructTable("Loops", "Gains", solver.getLoops(), solver.getlpGains());
		String[] lpCombinations = new String[solver.getlpCombinations().length];
		int i;
		for (i = 0; i < lpCombinations.length; i++) {
			lpCombinations[i] = (i + 2) + "-combination";
		}
		constructTable("n-combinations", "non-touching loops", lpCombinations, solver.getlpCombinations());
		String[] delta = new String[solver.getforwardPaths().length + 1];
		for (i = 0; i < delta.length - 1; i++) {
			delta[i] = "DELTA" + (i + 1);
		}
		delta[i] = "DELTA";
		constructTable("Gain Product", "Value", delta, solver.getDeltaGains());
		output += "Overall Transfer Function = " + solver.getOverAllGain();
		OutputPort.getOutputPort().emptyFields();
		OutputPort.getOutputPort().setOutput(output);

	}

	private void constructTable(String header1, String header2, String[] labels, String[] values) {
		ArrayList<String> headersList = new ArrayList<String>();
		headersList.add(header1);
		headersList.add(header2);
		ArrayList<Integer> colAlignList = new ArrayList<Integer>();
		colAlignList.add(Block.DATA_CENTER);
		colAlignList.add(Block.DATA_CENTER);
		List<String> rowList;
		List<List<String>> rowsList = new ArrayList<List<String>>();
		for (int i = 0; i < labels.length; i++) {
			rowList = new ArrayList<String>();
			rowList.add(labels[i]);
			rowList.add(values[i]);
			rowsList.add(rowList);
		}
		if (rowsList.size() > 0) {
			Board board = new Board(100);
			TableView table = new TableView(board, 100, headersList, rowsList);
			table.setColAlignsList(colAlignList);
			Block tableBlock = table.tableToBlocks();
			board.setInitialBlock(tableBlock);
			board.build();
			output += board.getPreview() + "\n\n";
		}

	}

	private void extractData(String nodesField, String branInfo) {
		try {
			numOfnodes = Integer.parseInt(nodesField);
			String[] lines = branInfo.split("\n");
			edgesInfo = new String[lines.length][3];
			for (int i = 0; i < edgesInfo.length; i++) {
				String[] info = lines[i].split(",");
				edgesInfo[i][0] = info[0];
				edgesInfo[i][1] = info[1];
				edgesInfo[i][2] = info[2];
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error, some inputs are invalid !", "Error", JOptionPane.ERROR_MESSAGE);
		}

	}
}
