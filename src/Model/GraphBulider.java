package Model;

import java.awt.BorderLayout;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import view.MainPanel;

public class GraphBulider {

	private static Graph graph;
	private static final String styleSheet = " graph{" + " 	text-style: bold;" + "text-size: 40px;" + "}"
			+ "		node {" + "	size: 30px;" + "	fill-color: black;" + "	text-background-mode: rounded-box;"
			+ "	text-background-color: rgb(77,77,77);" + "   text-color: white;" + "   text-size: 22px;"
			+ "   text-padding: 5px;" + "	text-alignment: under;" + "}" + " edge{" + "  text-size: 20px;"
			+ "text-style: bold;" + "	text-alignment: above;" + "    shape: freeplane;" + "text-color: red;" + "}";

	public static void buildGraph(int numOfnodes, String[][] edgesInfo) {
		graph = new MultiGraph("SGF");
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		for (int i = 1; i <= numOfnodes; i++) {
			graph.addNode("Y" + i);
			Node n = graph.getNode("Y" + i);
			n.addAttribute("ui.label", "Y" + i);
		}
		for (int i = 0; i < edgesInfo.length; i++) {
			String ed = "Y" + edgesInfo[i][0] + "Y" + edgesInfo[i][1];
			graph.addEdge(ed, "Y" + edgesInfo[i][0], "Y" + edgesInfo[i][1], true);
			Edge e = graph.getEdge(ed);
			e.addAttribute("ui.label", edgesInfo[i][2]);
		}
		graph.addAttribute("ui.stylesheet", styleSheet);
		graph.addAttribute("ui.quality");
		setView();
	}

	private static void setView() {
		Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
		viewer.enableAutoLayout();
		ViewPanel view = viewer.addDefaultView(false);
		MainPanel.getPanel().removeAll();
		MainPanel.getPanel().add(view, BorderLayout.CENTER);
		MainPanel.getPanel().repaint();
		MainPanel.getPanel().revalidate();
	}

	public static double getGain(String edge) {

		Edge e = graph.getEdge(edge);
		return Double.parseDouble((String) e.getAttribute("ui.label"));
	}
}
