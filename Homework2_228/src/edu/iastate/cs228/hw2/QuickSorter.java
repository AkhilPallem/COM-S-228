package edu.iastate.cs228.hw2;


/**
 *  
 *  @author Akhil Pallem
 *
 */

/**
 * 
 * This class implements the version of the quicksort algorithm presented in the lecture.   
 *
 */

public class QuickSorter extends AbstractSorter
{
	
	// Other private instance variables if you need ... 
		
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *   
	 * @param pts   input array of integers
	 */
	public QuickSorter(Point[] pts)
	{
		super(pts);
		super.algorithm = "QuickSort";
	}
		

	/**
	 * Carry out quicksort on the array points[] of the AbstractSorter class.  
	 * 
	 */
	@Override 
	public void sort()
	{
		quickSortRec(0, points.length - 1);
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last. 
	 * 
	 * @param first  starting index of the subarray
	 * @param last   ending index of the subarray
	 */
	private void quickSortRec(int first, int last)
	{
		if(first >= last) {
			return;
		}
		int x = partition(first, last);
		quickSortRec(first, x - 1);
		quickSortRec(x + 1, last);
	}
	
	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first
	 * @param last
	 * @return
	 */
	private int partition(int first, int last){
		Point partition = this.points[last];
		int x = first - 1;
		for (int j = first; j < last; j++) {
			if (pointComparator.compare(this.points[j], partition) <= 0) {
				x++;
				super.swap(x, j);
			}
		}
		super.swap(x + 1, last);
		return(x + 1);
	}	
		


	
	// Other private methods if needed ...
}
