package edu.iastate.cs228.hw2;

import java.io.File;

/**
 * 
 * @author 
 *
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * 
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class PointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    
		
	protected long scanTime; 	       // execution time in nanoseconds. 

	private String outputFileName;
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{
		if(pts == null) {
			throw new IllegalArgumentException("Points is null");
		}else if (pts.length == 0) {
			throw new IllegalArgumentException("Points has no points");
		}
		
		sortingAlgorithm = algo;
		points = new Point[pts.length];
		for(int i = 0; i < pts.length; i++) {
			points[i] = pts[i];
		}
	}

	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		File inputFile = new File(inputFileName);
		Scanner input = new Scanner(inputFile);
		ArrayList<Integer> place = new ArrayList<Integer>();
		while (input.hasNextInt()) {
			place.add(input.nextInt());
		}
		if ((place.size() % 2) != 0) {
			throw new InputMismatchException("Input file does not contain even number of values. It is odd.  Length was: " + place.size());
		}
		if (inputFileName == null) {
			throw new IllegalArgumentException("File name was null");
		}
		this.points = new Point[place.size() / 2];
		int j = 0;
		for (int i = 0; i < place.size(); i+=2) {
			Point x = new Point(place.get(i), place.get(i+1));
			this.points[j] = x;
			j++;
		}
		this.sortingAlgorithm = algo;
		
		//sets outputFileName to the sort methpd
		if (algo.equals(Algorithm.SelectionSort)) {
			outputFileName = "Select.txt";
		} else if (algo.equals(Algorithm.InsertionSort)) {
			outputFileName = "Insert.txt";
		} else if (algo.equals(Algorithm.MergeSort)) {
			outputFileName = "Merge.txt";
		} else {
			outputFileName = "Quick.txt";
		}
		
		System.out.println(outputFileName);
	}

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param algo
	 * @return
	 */
	public void scan()
	{
		AbstractSorter aSorter; 
		if (sortingAlgorithm == Algorithm.SelectionSort) {
			aSorter = new SelectionSorter(this.points);
		} else if (sortingAlgorithm == Algorithm.InsertionSort) {
			aSorter = new InsertionSorter(this.points);
		} else if (sortingAlgorithm == Algorithm.MergeSort) {
			aSorter = new MergeSorter(this.points);
		} else {
			aSorter = new QuickSorter(this.points);
		}
		
		// create an object to be referenced by aSorter according to sortingAlgorithm. for each of the two 
		// rounds of sorting, have aSorter do the following: 
		// 
		//     a) call setComparator() with an argument 0 or 1. 
		//
		//     b) call sort(). 		
		// 
		//     c) use a new Point object to store the coordinates of the medianCoordinatePoint
		//
		//     d) set the medianCoordinatePoint reference to the object with the correct coordinates.
		//
		//     e) sum up the times spent on the two sorting rounds and set the instance variable scanTime. 
		int x = 0;
		int y = 0;

		long startTime = System.nanoTime();
		for (int i = 0; i < 2; i++) {
			aSorter.setComparator(i);
			aSorter.sort();
			if (i == 0) {
				x = aSorter.getMedian().getX();
			}
			if (i == 1) {
				y = aSorter.getMedian().getY();
				medianCoordinatePoint = new Point(x, y);
				medianCoordinatePoint.toString();
			}
		}	
		long endTime = System.nanoTime();
		this.scanTime = endTime - startTime;
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{
		String end = String.format("%-17s %-10d %-10d", this.sortingAlgorithm, this.points.length, this.scanTime);
		return end;
	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		//gets both median coordinates and puts them together in a displayed string 
		return ("MCP:" + medianCoordinatePoint.getX() + " , " + medianCoordinatePoint.getY());
	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException
	{
		try {
			System.out.println(this.outputFileName);
			PrintWriter end = new PrintWriter(this.outputFileName);
			
			end.println(this.toString());
			end.close();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("File not gfound");
		}
	    
	}	

	

		
}
