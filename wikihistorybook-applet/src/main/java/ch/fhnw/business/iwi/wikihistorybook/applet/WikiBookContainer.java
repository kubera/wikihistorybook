package ch.fhnw.business.iwi.wikihistorybook.applet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import org.apache.log4j.Logger;
import org.graphstream.graph.Graph;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import org.graphstream.ui.swingViewer.Viewer.ThreadingModel;

import ch.fhnw.business.iwi.wikihistorybook.graph.DBProvider;
import ch.fhnw.business.iwi.wikihistorybook.graph.GraphFactory;
import ch.fhnw.business.iwi.wikihistorybook.graph.IWikiBookContainer;

public class WikiBookContainer extends JApplet implements IWikiBookContainer {

        private final static Logger LOGGER = Logger.getLogger(WikiBookContainer.class.getName()); 
        private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JPanel sidePanel;
	private JPanel btnPnl;
	private Viewer viewer;
	private View view;
	private GraphicElement lastElement;
	private JSlider tLine;
	private GraphFactory graphFactory;
	private JEditorPane outputText;
	private JTextArea yearText;
	private JPanel controlPanel;
	private int maxNodes;
	private JSpinner spinner;
	private JButton btnZoomIn, btnZoomOut;

	private final int SLIDER_MIN = -2000;
	private final int SLIDER_MAX = 2000;
	private final int SLIDER_INIT = 0;

	private final int YEAR_INIT = 0;

	private Graph graph;

	public WikiBookContainer() {
            try {
                System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
            } catch (Exception e) {
                LOGGER.warn("can't set system property 'org.graphstream.ui.renderer' " + e.getClass().getName());
            }

		panel = new JPanel(new java.awt.BorderLayout(1, 1));
		sidePanel = new JPanel(new java.awt.GridLayout(0, 1));
		sidePanel.setPreferredSize(new Dimension(200, 500));
		sidePanel.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0,
				Color.LIGHT_GRAY));

		outputText = new JEditorPane();
		outputText.setContentType("text/html");
		outputText.setEditable(false);
		outputText.setBorder(new EmptyBorder(10, 10, 0, 0));
		outputText.addHyperlinkListener(new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					try {
						Desktop.getDesktop().browse(e.getURL().toURI());
					} catch (IOException e1) {
						LOGGER.error( "failed!", e1 );
					} catch (URISyntaxException e1) {
						LOGGER.error( "failed!", e1 );
					}
				}
			}
		});

		controlPanel = new JPanel(new java.awt.GridLayout(0, 1));
		maxNodes = 1000;
		SpinnerModel yearModel = new SpinnerNumberModel(1000, 1, 2000, 10);
		spinner = new JSpinner(yearModel);
		spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				maxNodes = (Integer) spinner.getValue();
			}
		});
		controlPanel.add(spinner);
		controlPanel.setFocusable(false);
		controlPanel.setBorder(BorderFactory.createTitledBorder("max. Nodes"));

		sidePanel.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0,
				Color.LIGHT_GRAY));

		yearText = new JTextArea(3, 1);
		yearText.setFocusable(false);
		yearText.setText("Year: " + YEAR_INIT);
		yearText.setBorder(new EmptyBorder(10, 10, 0, 0));

		btnPnl = new JPanel();
		btnPnl.setFocusable(false);
		btnPnl.setBorder(BorderFactory.createTitledBorder("Zooming"));
		btnZoomIn = new JButton("+");
		btnZoomIn.setFocusable(false);
		btnZoomOut = new JButton("-");
		btnZoomOut.setFocusable(false);
		btnPnl.add(btnZoomIn);
		btnPnl.add(btnZoomOut);

		sidePanel.add(yearText);
		sidePanel.add(controlPanel);
		sidePanel.add(btnPnl);
		sidePanel.add(outputText);

		graphFactory = new GraphFactory(SLIDER_INIT, DBProvider.getInstance());
		GraphThread thread = new GraphThread(this, graphFactory);
		thread.start();

		tLine = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX,
				SLIDER_INIT);
		tLine.setName("wikihist-slider");
		tLine.setMajorTickSpacing(1000);
		tLine.setMinorTickSpacing(100);
		tLine.setPaintTicks(true);
		tLine.setPaintLabels(true);
		tLine.addChangeListener(new TimelineManager(this, thread));
		tLine.setFocusable(false);

		btnZoomIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double p = view.getCamera().getViewPercent();
				view.getCamera().setViewPercent(p - 0.1);
			}
		});

		btnZoomOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double p = view.getCamera().getViewPercent();
				view.getCamera().setViewPercent(p + 0.1);
			}
		});

		panel.add(sidePanel, BorderLayout.EAST);
		panel.add(tLine, BorderLayout.SOUTH);

		getContentPane().add(panel);
	}

	public void showGraph(Graph graph) {
		this.graph = graph;
		View newView;
		viewer = new Viewer(graph, ThreadingModel.GRAPH_IN_SWING_THREAD);
		viewer.enableAutoLayout();
		newView = viewer.addDefaultView(false);
		try {
			panel.remove(view);
		} catch (NullPointerException e) {

		}
		newView.setMouseManager(new InternalMouseManager(lastElement));
		newView.setShortcutManager(new InternalKeyManager());
		view = newView;
		view.setFocusable(true);
		view.getCamera().setViewPercent(0.7);
		panel.add(newView, BorderLayout.CENTER);
		panel.updateUI();
		view.requestFocusInWindow();
	}

	public void setOutputText(String output) {
		outputText.setText(output);
	}

	public void setYearText(String text) {
		yearText.setText(text);
	}

	public Graph getGraph() {
		return graph;
	}

	public int getMaxNodes() {
		return maxNodes;
	}

}
