package edu.iastate.cs228.hw2;

/**
 *  
 * @author Akhil Pallem
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.
 *
 */

public class MergeSorter extends AbstractSorter {
	// Other private instance variables if needed

	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts input array of integers
	 */
	public MergeSorter(Point[] pts) {
		super(pts);
		super.algorithm = "MergeSort";
	}

	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter.
	 * 
	 */
	@Override
	public void sort() {
		mergeSortRec(this.points);
	}

	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of
	 * points. One way is to make copies of the two halves of pts[], recursively
	 * call mergeSort on them, and merge the two sorted subarrays into pts[].
	 * 
	 * @param pts point array
	 */
	private void mergeSortRec(Point[] pts) {
		if (pts.length < 2) {
			return;
		}
		int middle = pts.length / 2;
		Point[] left = new Point[middle];
		Point[] right = new Point[pts.length - middle];
		for (int i = 0; i < middle; i++) {
			left[i] = pts[i];
		}
		for (int i = middle; i < pts.length; i++) {
			right[i - middle] = pts[i];
		}
		mergeSortRec(left);
		mergeSortRec(right);
		merge(left, right);

	}

	// Other private methods if needed ...
	private Point[] merge(Point[] B, Point[] C) {
		//takes two sorted arrays are compares their elements and then puts them together 
		Point[] D = new Point[B.length + C.length];
		int a = 0;
		int b = 0;
		int c = 0;
		while ((b < B.length) && (c < C.length)) {
			if (pointComparator.compare(B[b], C[c]) <= 0) {
				D[a] = B[b];
				b++;
				a++;
			} else {
				D[a] = C[c];
				c++;
				a++;
			}
		}

		if (b < B.length) {
			D[a] = B[b];
		} else if (c < C.length) {
			D[a] = C[c];
		}
		return (D);

	}
}
