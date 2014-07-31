package ch.fhnw.business.iwi.wikihistorybook.applet;


import java.awt.event.KeyEvent;

import org.graphstream.ui.swingViewer.util.DefaultShortcutManager;

/**
 * The key board events. 
 * 
 * @author Stefan Wagner
 *
 */
public class InternalKeyManager extends DefaultShortcutManager {
	
	
	public InternalKeyManager(){
		super();
	}

	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		double p = view.getCamera().getViewPercent();
		switch (e.getKeyCode()) {

		case KeyEvent.VK_O:
			view.getCamera().setViewPercent(p + 0.1);
			break;

		case KeyEvent.VK_I:
			if(p > 0.2){
				view.getCamera().setViewPercent(p - 0.1);
			}
			break;

		}
	}

}
