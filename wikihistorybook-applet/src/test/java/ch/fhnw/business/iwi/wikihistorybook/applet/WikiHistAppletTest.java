package ch.fhnw.business.iwi.wikihistorybook.applet;

import org.fest.swing.applet.AppletViewer;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.launcher.AppletLauncher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.fhnw.business.iwi.wikihistorybook.applet.WikiBookContainer;

public class WikiHistAppletTest {

	private AppletViewer viewer;
	private FrameFixture applet;

	@Before
	public void setUp() {
		viewer = AppletLauncher.applet(WikiBookContainer.class).start();
		applet = new FrameFixture(viewer);
		applet.show();
	}

	@Test
	public void shouldChangeLabelOnButtonClick() {
		applet.slider("wikihist-slider").click();
	}

	@After
	public void tearDown() {
		viewer.unloadApplet();
		applet.cleanUp();
	}
}
