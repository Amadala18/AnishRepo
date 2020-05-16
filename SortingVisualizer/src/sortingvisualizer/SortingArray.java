package sortingvisualizer;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import sorts.SortingAlgorithm;

public class SortingArray extends JPanel {

	public static final int DEFAULT_GUI_WIDTH = 1280;
	public static final int DEFAULT_GUI_HEIGHT = 720;
	private static final int DEFAULT_BAR_WIDTH = 5;
	//percent of the panel the bars will take up
	private static final double GUI_BAR_PERCENTAGE = 512.0/720.0;
	private static final int NUM_BARS = DEFAULT_GUI_WIDTH / DEFAULT_BAR_WIDTH;
	private final int[] array;
	private final int[] barColors;
	private int spinnerValue = 0;
	private String algoName = "";
	private SortingAlgorithm algorithm;
	private long delay = 0;
	private JSpinner spinner;
	private int arrayChanges = 0;
	
	public SortingArray() {
		setBackground(Color.BLACK);
		array = new int[NUM_BARS];
		barColors = new int[NUM_BARS];
		//set all colors to code 0, and assign values to array
		for (int k = 0; k < NUM_BARS; k++) {
			array[k] = k;
			barColors[k] = 0;
		}
		//create a spinner to control speed and define execution
		spinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
		spinner.addChangeListener((event) -> {
			//set the delay to value on the spinner
			delay = (Integer) spinner.getValue();
			algorithm.setDelay(delay);
		});
		
		add(spinner,BorderLayout.LINE_START);
	}
	
	public int arraySize() {
		return array.length;
	}
	
	public int getValue(int index) {
		return array[index];
	}
	//get the max value in the array and if there is none, get the minimum value
	public int getMaxValue() {
		return Arrays.stream(array).max().orElse(Integer.MIN_VALUE);
	}
	//when updating, attempt to sleep the thread for provided milliseconds and interrupt method if error occurs
	private void update(long milsecDelay, boolean isStep) {
		repaint();
		try {
			Thread.sleep(milsecDelay);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		//update step count of step occurs
		if (isStep) {
			arrayChanges++;
		}
	}
	
	public void swap(int fIndex, int sIndex, long milsecDelay, boolean isStep) {
		//swap the two indexes for swapping during sorts
		int temp = array[fIndex];
		array[fIndex] = array[sIndex];
		array[sIndex] = temp;
		//set bar colors to code 100
		barColors[fIndex] = 100;
		barColors[sIndex] = 100;
		//call update method to add delay to show visualization
		update(milsecDelay, isStep);
	}
	//update method for sorts to be called in sort algorithms
	public void singleUpdate(int index, int value, long milsecDelay, boolean isStep) {
		array[index] = value;
		barColors[index] = 100;
		
		update(milsecDelay, isStep);
		repaint();
	}
	//creates a random array of values
	public void shuffle() {
		arrayChanges = 0;
		Random rdm = new Random();
		
		for (int k = 0; k < arraySize(); k++) {
			int swapWithIndex = rdm.nextInt(arraySize() - 1);
			swap(k, swapWithIndex, 5, false);
		}
		
		arrayChanges = 0;
	}
	//shows the current value the visualzier is comparing
	public void highlight() {
		for (int k = 0; k < arraySize(); k++) {
			singleUpdate(k, getValue(k), 5, false);
		}
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_GUI_WIDTH, DEFAULT_GUI_HEIGHT);
	}
	//sets colors back to code 0
	public void resetColor() {
		for (int k = 0; k < NUM_BARS; k++) {
			barColors[k] = 0;
		}
		
		repaint();
	}
	//overrides paintComponent method
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D panelGraphics = (Graphics2D) g.create();
		
		try {
			//creates hashmap and sets up gui window during visualization
			Map<RenderingHints.Key, Object> renderingHints = new HashMap<>();
			renderingHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			panelGraphics.addRenderingHints(renderingHints);
			panelGraphics.setColor(Color.WHITE);
			panelGraphics.setFont(new Font("Sanserif Plain", Font.BOLD, 20));
			panelGraphics.drawString(" Current Sorting Method: " + algoName, 10, 30);
			panelGraphics.drawString("Current step delay: " + delay + "ms", 10, 55);
			panelGraphics.drawString("     Changes to Array: " + arrayChanges, 10, 80);
			
			drawBars(panelGraphics);
		} finally {
			panelGraphics.dispose();
		}
	}
	//creates actual bar image
	private void drawBars(Graphics2D panelGraphics) {
		int barW = getWidth() / NUM_BARS;
		int bufferedImageW = getWidth();
		int bufferedImageH = getHeight();
		//always sets buffered image width to 256
		if (bufferedImageH > 0 && bufferedImageW > 0) {
			if (bufferedImageW < 256) {
				bufferedImageW = 256;
			}
			
			double max = getMaxValue();
			
			BufferedImage bufferedImage = new BufferedImage(bufferedImageW, bufferedImageH, BufferedImage.TYPE_INT_ARGB);
			makeBufferedImageTransparent(bufferedImage);
			Graphics2D bufferedGraphics = null;
			
			try {
				bufferedGraphics = bufferedImage.createGraphics();
				//puts bars in correct position and sets colors while visualization is sorting
				for (int w = 0; w < NUM_BARS; w++) {
					double current = getValue(w);
					double percentOfMax = current / max;
					double heightPercentOfPanel = percentOfMax * GUI_BAR_PERCENTAGE;
					int height = (int) (heightPercentOfPanel * (double) getHeight());
					int xStart = w + (barW - 1) * w;
					int yStart = getHeight() - height;
					int value = barColors[w] * 2;
					
					if (value > 190) {
						bufferedGraphics.setColor(new Color(255 - value, 255, 255 - value));
					} else {
						bufferedGraphics.setColor(new Color(255, 255 - value, 255 - value));
					}
					
					bufferedGraphics.fillRect(xStart, yStart, barW, height);
					
					if (barColors[w] > 0) {
						barColors[w] -= 5;
					}
				}
			} finally {
				if (bufferedGraphics != null) {
					bufferedGraphics.dispose();
				}
			}
			
			panelGraphics.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
		}
	}
	//makes image transparent
	private void makeBufferedImageTransparent(BufferedImage image) {
		Graphics2D bufferedGraphics = null;
		try {
			bufferedGraphics = image.createGraphics();
			bufferedGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
			bufferedGraphics.fillRect(0, 0, image.getWidth(), image.getHeight());
			bufferedGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		} finally {
			if (bufferedGraphics != null) {
				bufferedGraphics.dispose();
			}
		}
	}
	
	public void setName(String algoName) {
		this.algoName = algoName;
	}
	//sets algorithm to to desired sort and updates delay based on spinner
	public void setAlgo(SortingAlgorithm algorithm) {
		this.algorithm = algorithm;
		delay = algorithm.getDelay();
		spinner.setValue((int) algorithm.getDelay());
	}
	
	public long getAlgoDelay() {
		return delay;
	}
}
