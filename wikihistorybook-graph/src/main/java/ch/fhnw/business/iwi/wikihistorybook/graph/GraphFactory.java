package ch.fhnw.business.iwi.wikihistorybook.graph;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.IdAlreadyInUseException;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.AdjacencyListGraph;

public class GraphFactory {

        private final static Logger LOGGER = Logger.getLogger(GraphFactory.class.getName());

        private Graph graph;
	private DBProvider db;
	private int cur_year;
	private ResultSet people;
	private ResultSet connections;
	private double nodeNum;
	private double conNum;

	public GraphFactory(int initYear, DBProvider dbProvider) {
		super();
		db = dbProvider;
		graph = new AdjacencyListGraph("Wiki");
		cur_year = initYear;
	}

	public void run(IWikiBookContainer wikibook) {
		int maxNodes = wikibook.getMaxNodes();
		people = db.getPeople(cur_year, maxNodes);
		wikibook.setYearText("Year:  " + cur_year + "\n" + "Nodes loading...");
		nodeNum = loadNodes();
		wikibook.setYearText("Year:  " + cur_year + "\n" + "Nodes: " + nodeNum
				+ "\n" + "Connections loading...");
		conNum = loadConnections(maxNodes);
		wikibook.setYearText("Year:  " + cur_year + "\n" + "Nodes: " + nodeNum
				+ "\n" + "Connections:	" + conNum + "\n");
		graph.addAttribute("ui.antialias");
		graph.addAttribute("ui.quality");
		setIndegrees();

		graph.addAttribute("ui.stylesheet", "url('graph_style.css')");

		wikibook.showGraph(graph);
	}

	private int loadNodes() {
		int num = 0;
		try {
			people.afterLast();
			while (people.previous()) {
				num++;
				try {
					Node n = graph.addNode(people.getString("id"));
					n.addAttribute("label", people.getString("name"));
					n.addAttribute("layout.weight", 0.5);
					n.addAttribute("indegree", people.getString("indegree"));
					n.addAttribute("year_from", people.getString("year_from"));
					n.addAttribute("year_to", people.getString("year_to"));

				} catch (SQLException e) {
					LOGGER.debug("failed", e);
				} catch (IdAlreadyInUseException f) {
					LOGGER.debug("failed", f);
				}
			}
		} catch (SQLException e3) {
			LOGGER.debug("failed", e3);
		}
		return num;
	}

	private double loadConnections(int maxNodes) {
		int num = 0;
		connections = db.getConnections(cur_year, maxNodes);
		try {
			connections.first();
			while (connections.next()) {
				num++;
				try {
					graph.addEdge(connections.getString("person_from")
							+ connections.getString("person_to"),
							connections.getString("person_from"),
							connections.getString("person_to"));
				} catch (ElementNotFoundException e) {

				} catch (EdgeRejectedException e) {

				}
			}

		} catch (SQLException e) {
			LOGGER.debug("failed", e);
		}

		return num;
	}

	private void setIndegrees() {
		try {
			people.first();
			double maxIn = people.getDouble("indegree");
			while (people.next()) {
				double in = people.getDouble("indegree");

				double size = in / maxIn * 20;
				double weight = in / maxIn;

				if (weight < 0.01) {
					weight = 0;
				}

				if (size < 5) {
					size = 5;
				}

				int g = 0;
				int r = 255 - (int) (255 - (in / maxIn * 255));
				int b = (int) (255 - (in / maxIn * 255));

				try {
					graph.getNode(people.getString("id")).changeAttribute(
							"ui.style",
							"fill-color: rgb(" + r + "," + g + "," + b
									+ "); size: " + size + "px;");

					graph.getNode(people.getString("id")).changeAttribute(
							"layout.weight", weight);
				} catch (SQLException e) {
					LOGGER.debug("failed", e);
				}

			}
		} catch (SQLException e) {
			LOGGER.debug("failed", e);
		}
	}

	public DBProvider getDB() {
		return db;
	}

	public void setCurrentYear(int time) {
		cur_year = time;
	}
}
