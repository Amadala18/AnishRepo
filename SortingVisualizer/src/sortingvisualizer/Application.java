package sortingvisualizer;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import screens.MenuScreen;
import screens.Screen;

public class Application {
	
	private final JFrame gui;
	public static final int GUI_WIDTH = 1280;
	public static final int GUI_HEIGHT = 720;
	private final ArrayList<Screen> screens;
	
	public Application() {
		//creates arrayList of screens for visualizer and sets up window
		screens = new ArrayList<>();
		gui = new JFrame("Sorting Visualizer");
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setVisible(true);
	}
	//returns current screen
	public Screen getCurrentScreen() {
		return screens.get(screens.size() - 1);
	}
	//gets rid of current screen and adds screen passed into method
	public void pushScreen(Screen screen) {
		if (!screens.isEmpty()) {
			gui.remove(getCurrentScreen());
		}
		
		screens.add(screen);
		gui.setContentPane(screen);
		gui.validate();
		screen.onOpen();
	}
	//gets rid of top screen
	public void popScreen() {
		//if screen list is not empty, remove current screen
		if (!screens.isEmpty()) {
			Screen previous = getCurrentScreen();
			screens.remove(previous);
			gui.remove(previous);
			//if list is still not empty show the next screen in list
			if (!screens.isEmpty()) {
				Screen current = getCurrentScreen() ;
				gui.setContentPane(current);
				gui.validate();
				current.onOpen();
			} else {
				//otherwise close window
				gui.dispose();
			}
		}
	}
	//begins application
	public void start() {
		pushScreen(new MenuScreen(this));
		gui.pack();
	}
	//main method
	public static void main(String[] args) {
		System.setProperty("sun.java2d.opengl", "true");
		SwingUtilities.invokeLater(() -> {
			new Application().start();
		});
	}
	
}
;