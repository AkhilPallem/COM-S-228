package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Akhil Pallem This class represents a simulated town grid
 *
 */
public class Town {

	private int length, width; // Row and col (first and second indices)
	public TownCell[][] grid;

	/**
	 * Constructor to be used when user wants to generate grid randomly, with the
	 * given seed. This constructor does not populate each cell of the grid (but
	 * should assign a 2D array to it).
	 * 
	 * @param length
	 * @param width
	 */
	public Town(int length, int width) {
		this.length = length;
		this.width = width;
		grid = new TownCell[width][length];
	}

	/**
	 * Constructor to be used when user wants to populate grid based on a file.
	 * Please see that it simple throws FileNotFoundException exception instead of
	 * catching it. Ensure that you close any resources (like file or scanner) which
	 * is opened in this function.
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public Town(String inputFileName) throws FileNotFoundException {
		File file1 = new File(inputFileName);
		try {
			Scanner scan = new Scanner(file1);
			this.width = scan.nextInt();
			this.length = scan.nextInt();
			grid = new TownCell[width][length];
			while (scan.hasNextLine()) {
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < length; y++) {
						switch (scan.next()) {
						case "R":
							grid[x][y] = new Reseller(this, x, y);
							break;
						case "S":
							grid[x][y] = new Streamer(this, x, y);
							break;
						case "C":
							grid[x][y] = new Casual(this, x, y);
							break;
						case "E":
							grid[x][y] = new Empty(this, x, y);
							break;
						case "O":
							grid[x][y] = new Outage(this, x, y);
							break;
						}
					}
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("Invalid file path" + e.toString());
		}

	}

	/**
	 * Returns width of the grid.
	 * 
	 * @return
	 */
	public int getWidth() {
		// TODO: Write/update your code here.
		return width;
	}

	/**
	 * Returns length of the grid.
	 * 
	 * @return
	 */
	public int getLength() {
		// TODO: Write/update your code here.
		return length;
	}

	/**
	 * Initialize the grid by randomly assigning cell with one of the following
	 * class object: Casual, Empty, Outage, Reseller OR Streamer
	 */
	public void randomInit(int seed) {
		Random rand = new Random(seed);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < length; y++) {
				int random_value = rand.nextInt(5);
				if (random_value == TownCell.CASUAL) {
					grid[x][y] = new Casual(this, x, y);
				} else if (random_value == TownCell.OUTAGE) {
					grid[x][y] = new Outage(this, x, y);
				} else if (random_value == TownCell.STREAMER) {
					grid[x][y] = new Streamer(this, x, y);
				} else if (random_value == TownCell.RESELLER) {
					grid[x][y] = new Reseller(this, x, y);
				} else if (random_value == TownCell.EMPTY) {
					grid[x][y] = new Empty(this, x, y);
				}
			}
		}
	}

	/**
	 * Output the town grid. For each square, output the first letter of the cell
	 * type. Each letter should be separated either by a single space or a tab. And
	 * each row should be in a new line. There should not be any extra line between
	 * the rows.
	 */
	@Override
	public String toString() {
		String s = "";
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < length; y++) {
				s += grid[x][y].who().toString().charAt(0) + " ";
			}
			s += "\n";

		}
		return s;
	}
}
