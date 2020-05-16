package sorts;

import sortingvisualizer.SortingArray;

public class InsertionSort implements SortingAlgorithm {
	
	private long stepDelay = 1;
	//implementation of InsertionSort
	public void runSort(SortingArray array) {
		for (int k = 0; k < array.arraySize(); k++) {
			int key = array.getValue(k);
			int j = k - 1;
			
			while (j >= 0 && array.getValue(j) > key) {
				//calls singleUpdate method to show comparisons during visualization
				array.singleUpdate(j + 1, array.getValue(j), 5, true);
				j--;
			}
			
			array.singleUpdate(j + 1, key, getDelay(), true);
		}
	}
	
	public String getName() {
		return "Insertion Sort";
	}
	
	public long getDelay() {
		return stepDelay;
	}
	
	public void setDelay(long delay) {
		this.stepDelay = delay;
	}
}
