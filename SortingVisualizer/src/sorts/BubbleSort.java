package sorts;

import sortingvisualizer.SortingArray;

public class BubbleSort implements SortingAlgorithm {
	
	private long stepDelay = 2;
	//implementation of BubbleSort
	public void runSort(SortingArray array) {
		int length = array.arraySize();
		
		for (int k = 0; k < length - 1; k++) {
			
			for (int i = 0; i < length - k - 1; i++) {
				if (array.getValue(i) > array.getValue(i + 1)) {
					//calls swap method to swap two values during visualization
					array.swap(i, i + 1, getDelay(), true);
				}
			}
		}
	}
	
	public String getName() {
		return "Bubble Sort";
	}
	
	public long getDelay() {
		return stepDelay;
	}
	
	public void setDelay(long delay) {
		this.stepDelay = delay;
	}
}
