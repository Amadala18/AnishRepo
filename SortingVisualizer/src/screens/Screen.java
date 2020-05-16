package screens;

import static sortingvisualizer.Application.GUI_HEIGHT;
import static sortingvisualizer.Application.GUI_WIDTH;

import java.awt.Dimension;
import javax.swing.JPanel;
import sortingvisualizer.Application;

public abstract class Screen extends JPanel {

	protected Application app;
	//constructor
	public Screen(Application app) {
		this.app = app;
	}
	//puts Dimension of Screen in correct size
	public Dimension getPreferredSize() {
		return new Dimension(GUI_HEIGHT, GUI_WIDTH);
	}
	
	public abstract void onOpen();
	
}
