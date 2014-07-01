package ch.fhnw.business.iwi.wikihistorybook.applet;

import java.awt.BorderLayout;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;

public class WikiHistApplet extends JApplet {

    private static final long serialVersionUID = 1L;

    public void init() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    guiInit(); // initialize the GUI
                }
            });
        } catch (Exception exc) {
            System.out.println("Can't create because of " + exc);
        }
    }

    // Called second, after init(). Also called
    // whenever the applet is restarted.
    public void start() {
        // Not used by this applet.
    }

    // Called when the applet is stopped.
    public void stop() {
        // Not used by this applet.
    }

    // Called when applet is terminated. This is
    // the last method executed.
    public void destroy() {
        // Not used by this applet.
    }

    // Setup and initialize the GUI.
    private void guiInit() {
        setLayout(new BorderLayout());

        Graph graph = new SingleGraph("Test 1");
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CA", "C", "A");

        for (int i = 0; i < 100; i++) {
            graph.addNode("Node" + i);
        }

        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_SWING_THREAD);
        viewer.enableAutoLayout();
        View view = viewer.addDefaultView(false);
        view.getCamera().setViewCenter(2, 3, 4);
        view.getCamera().setViewPercent(0.5);
        view.getCamera().resetView();
        view.setFocusable(true);
        view.getCamera().setViewPercent(0.7);
        view.requestFocusInWindow();
        getContentPane().add(view, BorderLayout.CENTER);
        JButton btn = new JButton("OK");
        btn.setName("OK");
        getContentPane().add(btn, BorderLayout.PAGE_END);
    }
}