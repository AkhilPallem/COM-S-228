package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Akhil Pallem
 *
 *         The ISPBusiness class performs simulation over a grid plain with
 *         cells occupied by different TownCell types.
 *
 */
public class ISPBusiness {

	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 * 
	 * @param tOld: old/current Town object.
	 * @return: New town object.
	 */
	public static Town updatePlain(Town tOld) {
		Town tNew = new Town(tOld.getLength(), tOld.getWidth());
		// TODO: Write your code here.
		for (int x = 0; x < tOld.getWidth(); x++) {
			for (int y = 0; y < tOld.getLength(); y++) {
				tNew.grid[x][y] = tOld.grid[x][y].next(tNew);
			}
		}
		return tNew;
	}

	/**
	 * Returns the profit for the current state in the town grid.
	 * 
	 * @param town
	 * @return
	 */
	public static int getProfit(Town town) {
		int p1 = 0;
		for (int x = 0; x < town.getLength(); x++) {
			for (int y = 0; y < town.getWidth(); y++) {
				if (town.grid[x][y].who() == State.CASUAL) {
					p1 += 1;
				}
			}
		}
		return p1;
	}

	/**
	 * Main method. Interact with the user and ask if user wants to specify elements
	 * of grid via an input file (option: 1) or wants to generate it randomly
	 * (option: 2).
	 * 
	 * Depending on the user choice, create the Town object using respective
	 * constructor and if user choice is to populate it randomly, then populate the
	 * grid here.
	 * 
	 * Finally: For 12 billing cycle calculate the profit and update town object
	 * (for each cycle). Print the final profit in terms of %. You should print the
	 * profit percentage with two digits after the decimal point: Example if profit
	 * is 35.5600004, your output should be:
	 *
	 * 35.56%
	 * 
	 * Note that this method does not throw any exception, so you need to handle all
	 * the exceptions in it.
	 * 
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		int input;
		final int endCycle = 12;
		Scanner scnr = new Scanner(System.in);
		System.out.println("How to populate grid (type 1 or 2) 1: From a file 2: Is generated randomly with seed ");
		input = scnr.nextInt();
		Town t = null;
		if (input == 1) {
			
			String filePath = "";
			try {
				System.out.println("Please enter file path: ");
				scnr.nextLine();
				filePath = scnr.nextLine();
				File file = new File(filePath);
				t = new Town(filePath);
				} catch (FileNotFoundException e) {
				System.out.println("Invalid path" + e.toString());}}
		if (input == 2) {
			
			int seed;
			int row;
			int col;
			System.out.println("Enter the rows and columns seperated by the seed: ");
			row = scnr.nextInt();
			col = scnr.nextInt();
			seed = scnr.nextInt();
			t = new Town(row, col);
			t.randomInit(seed);}
		double profit = 0.0;
		for (int month = 0; month < 12; month++) {
			profit += (getProfit(t) / ((double) t.getWidth() * (double) t.getLength())) * 100;
			t = updatePlain(t);}
		profit = profit / endCycle;
		System.out.printf("%.2f%c", profit, '%');
	}

}
