package edu.iastate.cs228.hw2;

/**
 *  
 * @author Akhil Pallem
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class CompareSorters {
	/**
	 * Repeatedly take integer sequences either randomly generated or read from
	 * files. Use them as coordinates to construct points. Scan these points with
	 * respect to their median coordinate point four times, each time using a
	 * different sorting algorithm.
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException {
		// TODO
		//
		// Conducts multiple rounds of comparison of four sorting algorithms. Within
		// each round,
		// set up scanning as follows:
		//
		// a) If asked to scan random points, calls generateRandomPoints() to initialize
		// an array
		// of random points.
		//
		// b) Reassigns to the array scanners[] (declared below) the references to four
		// new
		// PointScanner objects, which are created using four different values
		// of the Algorithm type: SelectionSort, InsertionSort, MergeSort and QuickSort.
		//
		//
		System.out.println("Performances of Four Sorting Algorithms in Point Scanning");
		System.out.println();
		System.out.println("1: Random Integers  2: File Input  3: Exit");
		PointScanner[] scanners = new PointScanner[4];
		Scanner input = new Scanner(System.in);
		int trials = 1;
		Random rand = new Random();
		int option = 1;

		// For each input of points, do the following.
		//
		// a) Initialize the array scanners[].
		//
		// b) Iterate through the array scanners[], and have every scanner call the
		// scan()
		// method in the PointScanner class.
		//
		// c) After all four scans are done for the input, print out the statistics
		// table from
		// section 2.
		//
		// A sample scenario is given in Section 2 of the project description.
		while (true) {
			System.out.print("Trial " + trials + ": ");
			option = input.nextInt();
			if (option == 1) {
				System.out.print("Enter number of random points: ");
				int numRandPoints = input.nextInt();
				System.out.println();
				Point[] point = generateRandomPoints(numRandPoints, rand);
				scanners[0] = new PointScanner(point, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(point, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(point, Algorithm.MergeSort);
				scanners[3] = new PointScanner(point, Algorithm.QuickSort);
			} else if(option == 2){
				System.out.println("Points from a file");
				System.out.print("File name: ");
				String file = input.next();
				scanners[0] = new PointScanner(file, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(file, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(file, Algorithm.MergeSort);
				scanners[3] = new PointScanner(file, Algorithm.QuickSort);
			}else {
				System.exit(0);
			}
			
			for (int i = 0; i < scanners.length; i++) {
				scanners[i].scan();
			}
			System.out.println("");
			System.out.printf("%-17s %-10s %-10s \n", "algorithm", "size", "time (ns)");
			System.out.println("--------------------------------------");
			for (int i = 0; i < scanners.length; i++) {
				System.out.println(scanners[i].stats());
			}
			System.out.println("--------------------------------------");
			System.out.println();
			trials += 1;
		}
	}

	/**
	 * This method generates a given number of random points. The coordinates of
	 * these points are pseudo-random numbers within the range [-50,50] � [-50,50].
	 * Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing.
	 * 
	 * @param numPts number of points
	 * @param rand   Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException {
		if (numPts < 1) {
			throw new IllegalArgumentException("Number of points is less than 1");
		} else {
			Point[] point = new Point[numPts];
			int x;
			int y;
			for (int i = 0; i < numPts; i++) {
				x = rand.nextInt(101) - 50;
				y = rand.nextInt(101) - 50;
				Point x2 = new Point(x, y);
				point[i] = x2;
			}
			return (point);
		}
	}

}
