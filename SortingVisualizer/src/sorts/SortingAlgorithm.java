package sorts;

import sortingvisualizer.SortingArray;

public interface SortingAlgorithm {
	
	public String getName();
	
	public long getDelay();
	
	public void setDelay(long delay);
	
	public void runSort(SortingArray array);

}
