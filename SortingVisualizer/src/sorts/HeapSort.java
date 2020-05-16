package sorts;

import sortingvisualizer.SortingArray;
//recursive implementation of heap sort
public class HeapSort implements SortingAlgorithm {
	
	private long stepDelay = 20;
	//helper method to decide if child is greater than root. (Root should be largest in Max Heap
	private boolean isChildLargerThanRoot(int child, int largest, int n, SortingArray array) {
		return child < n && array.getValue(child) > array.getValue(largest);
	}
	//puts array in binary tree array
	private void toBinTree(SortingArray array, int n, int root) {
		int largest  = root;
		int lChild = 2 * root + 1;
		int rChild = 2 * root + 2;
		//if left child is larger than root, set the largest value to the left child
		if (isChildLargerThanRoot(lChild, largest, n, array)) {
			largest = lChild;
		}
		//if right child is larger than root, set the largest value to the right child
		if (isChildLargerThanRoot(rChild, largest, n, array)) {
			largest = rChild;
		}
		//if the largest value is not the root, swap the root and largest value and recursively call the method
		if (largest != root) {
			array.swap(root, largest, getDelay(), true);
			toBinTree(array, n, largest);
		}
	}
	
	public void runSort(SortingArray array) {
		int length = array.arraySize();
		
		for (int k = length / 2 - 1; k >= 0; k--) {
			toBinTree(array, length, k);
		}
		
		for (int k = length - 1; k >= 0; k--) {
			//calls swap to exchange values during visualization
			array.swap(0, k, getDelay(), true);
			toBinTree(array, k, 0);
		}
	}
	
	public String getName() {
		return "Heap Sort";
	}
	
	public long getDelay() {
		return stepDelay;
	}
	
	public void setDelay(long delay) {
		this.stepDelay = delay;
	}
}
