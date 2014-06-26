package ch.fhnw.business.iwi.wikihistorybook;

import org.fest.swing.applet.AppletViewer;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.launcher.AppletLauncher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WikiHistAppletTest {

    private AppletViewer viewer;
    private FrameFixture applet;

    @Before
    public void setUp() {
        viewer = AppletLauncher.applet(WikiHistApplet.class).start();
        applet = new FrameFixture(viewer);
        applet.show();
    }

    @Test
    public void shouldChangeLabelOnButtonClick() {
        applet.button("OK").click();
    }

    @After
    public void tearDown() {
        viewer.unloadApplet();
        applet.cleanUp();
    }
}
