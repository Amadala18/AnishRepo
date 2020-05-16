package sorts;

import sortingvisualizer.SortingArray;

public class SelectionSort implements SortingAlgorithm {

	private long stepDelay = 120;
	//implementation for selection sort
	public void runSort(SortingArray array) {
		int length = array.arraySize();
		
		for (int k = 0; k < length - 1; k++) {
			int min = k;
			
			for (int i = k + 1; i < length; i++) {
				if (array.getValue(i) < array.getValue(min)) {
					min = i;
				}
			}
			//calls swap to exchange two values after comparison
			array.swap(k, min, getDelay(), true);
		}
	}
		
		public String getName() {
			return "Selection Sort";
		}
		
		public long getDelay() {
			return stepDelay;
		}
		
		public void setDelay(long delay) {
			this.stepDelay = delay;
		}
	}

