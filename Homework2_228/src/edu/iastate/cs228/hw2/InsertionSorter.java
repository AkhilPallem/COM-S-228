package edu.iastate.cs228.hw2;

/**
 *  
 * @author Akhil Pallem
 *
 */

/**
 * 
 * This class implements insertion sort.
 *
 */

public class InsertionSorter extends AbstractSorter {
	// Other private instance variables if you need ...

	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts
	 */
	public InsertionSorter(Point[] pts) {
		super(pts);
		super.algorithm = "InsertionSort";

	}

	/**
	 * Perform insertion sort on the array points[] of the parent class
	 * AbstractSorter.
	 */
	@Override
	public void sort() {
		//length in terms of a local variable 
		int n = points.length;
		for (int i = 1; i < n; i++) {
			Point temp1 = points[i];
			int j = i - 1;
			while ((j > -1) && (pointComparator.compare(points[j], temp1) > 0)) {
				points[j + 1] = points[j];
				j--;
			}
			points[j + 1] = temp1;
		}
	}
}
