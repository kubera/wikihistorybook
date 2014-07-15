package ch.fhnw.business.iwi.wikihistorybook.applet;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import ch.fhnw.business.iwi.wikihistorybook.graph.DBProvider;
import ch.fhnw.business.iwi.wikihistorybook.graph.GraphFactory;
import ch.fhnw.business.iwi.wikihistorybook.graph.IWikiBookContainer;

class TimelineManager implements ChangeListener {

    private final static Logger LOGGER = Logger.getLogger(TimelineManager.class.getName());
    private IWikiBookContainer wikiBookContainer;
    private GraphFactory graphFactory;
    private GraphThread thread;

    public TimelineManager(IWikiBookContainer wikiBookContainer, GraphThread thread) {
        this.wikiBookContainer = wikiBookContainer;
        this.thread = thread;
    }

    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            int time = (int) source.getValue();
            if (thread != null && thread.isAlive()) {
                try {
                    thread.join();
                } catch (InterruptedException e1) {
                    LOGGER.debug("failed!", e1);
                }
            }
            graphFactory = new GraphFactory(time, DBProvider.getInstance());
            thread = new GraphThread(wikiBookContainer, graphFactory);
            thread.start();
        }
    }
}