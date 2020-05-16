package screens;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;
import sortingvisualizer.Application;
import sortingvisualizer.SortingArray;
import sorts.SortingAlgorithm;


public final class SortingScreen extends Screen {

	private final SortingArray sortingArray;
	private final ArrayList<SortingAlgorithm> sortingQueue;
	//constructor
	public SortingScreen(ArrayList<SortingAlgorithm> algorithms, Application app) {
		super(app);
		this.sortingArray = new SortingArray();
		setLayout(new BorderLayout());
		add(sortingArray, BorderLayout.CENTER);
		sortingQueue = algorithms;
	}
	//pauses animation for a second
	private void sleep() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	//creates random values, resets the color and pauses animation for a second
	private void shuffle() {
		sortingArray.shuffle();
		sortingArray.resetColor();
		sleep();
	}
	
	public void onOpen() {
		SwingWorker<Void, Void> swing = new SwingWorker<Void, Void>() {
			protected Void doInBackground() throws Exception {
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//creates a queue for algorithms checked and runs sorts accordingly
				for (SortingAlgorithm algorithm : sortingQueue) {
					shuffle();
					sortingArray.setName(algorithm.getName());
					sortingArray.setAlgo(algorithm);
					algorithm.runSort(sortingArray);
					sortingArray.resetColor();
					sortingArray.highlight();
					sortingArray.resetColor();
					sleep();
				}
				
				return null;
			}
			//gets rid of current window
			public void done() {
				app.popScreen();
			}	
		};
		
		swing.execute();
	}
}
