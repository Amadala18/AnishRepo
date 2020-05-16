package screens;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import sortingvisualizer.Application;
import sorts.BubbleSort;
import sorts.HeapSort;
import sorts.InsertionSort;
import sorts.SelectionSort;
import sorts.SortingAlgorithm;

public class MenuScreen extends Screen {
	
	private static final Color BACKGROUND_COLOR = Color.BLACK;
	//creates an arraylist of boxes that contain various sorts
	private final ArrayList<CheckBox> boxes;
	//constructor
	public MenuScreen(Application app) {
		super(app);
		boxes = new ArrayList<>();
		setUpGUI();
	}
	//add box to the screen/panel and sets color and position on window
	private void addBox(SortingAlgorithm algorithm, JPanel panel) {
		JCheckBox box = new JCheckBox("", true);
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		box.setBackground(BACKGROUND_COLOR);
		box.setForeground(Color.WHITE);
		boxes.add(new CheckBox(algorithm, box));
		panel.add(box);
	}
	//sets layout of the panel in BoxLayout
	private void Container(JPanel p) {
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		p.setBackground(BACKGROUND_COLOR);
	}
	
	public void setUpGUI() {
		//creates containers for each part of the screen and sets in boxLayout
		JPanel sortAlgoContainer = new JPanel();
		JPanel optionsContainer = new JPanel();
		JPanel outerContainer = new JPanel();
		Container(this);
		Container(optionsContainer);
		Container(outerContainer);
		outerContainer.setBackground(BACKGROUND_COLOR);
		outerContainer.setLayout(new BoxLayout(outerContainer, BoxLayout.LINE_AXIS));
		
		sortAlgoContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
		//adds check boxes for various sorts
		addBox(new InsertionSort(), sortAlgoContainer);
		addBox(new SelectionSort(), sortAlgoContainer);
		addBox(new BubbleSort(), sortAlgoContainer);
		addBox(new HeapSort(), sortAlgoContainer);
		//creates a button to start visualization and defines what button executes
		JButton start = new JButton("Start Visualizer");
		start.addActionListener((ActionEvent e) -> {
			ArrayList<SortingAlgorithm> algorithms = new ArrayList<>();
			//for every box selected, get the algorithm for the sort
			for (CheckBox acb : boxes) {
				if (acb.isSelected()) {
					algorithms.add(acb.getAlgorithm());
				}
			}
			
			app.pushScreen(new SortingScreen(algorithms, app));
		});
		//finish setting up gui with after check box
		start.setAlignmentX(Component.CENTER_ALIGNMENT);
		outerContainer.add(optionsContainer);
		outerContainer.add(Box.createRigidArea(new Dimension(5, 0)));
		outerContainer.add(sortAlgoContainer);
		int gap = 15;
		add(Box.createRigidArea(new Dimension(0, gap)));
		add(outerContainer);
		add(Box.createRigidArea(new Dimension(0, gap)));
		add(start);
	}
	//for every box selected, call the unselect method
	public void onOpen() {
		boxes.forEach((box) -> {
			box.unselect();
		});
	}
	
	private class CheckBox {
		private final SortingAlgorithm algorithm;
		private final JCheckBox box;
		
		public CheckBox(SortingAlgorithm algorithm, JCheckBox box) {
			this.algorithm = algorithm;
			this.box = box;
			this.box.setText(algorithm.getName());
		}
		
		public void unselect() {
			box.setSelected(false);
		}
		
		public boolean isSelected() {
			return box.isSelected();
		}
		
		public SortingAlgorithm getAlgorithm() {
			return algorithm;
		}
	}

}