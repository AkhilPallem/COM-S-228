package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * @author Akhil Pallem
 *
 */
public class MsgTree {
	public char payloadChar;
	public MsgTree left;
	public MsgTree right;
	private boolean leaf = true;
	private static int bit = 0;
	private static int staticIdx = 0;
	private static int charCount = 0;

	public MsgTree(String encodingString) {
		if (staticIdx == 0) {
			staticIdx = 1;
		}
		char val = encodingString.charAt(staticIdx);

		// Builds the left size
		if (val == '^') {
			this.left.staticIdx++;
			this.left = new MsgTree(encodingString);
			this.left.leaf = true;
		} else {
			this.left = new MsgTree(val);
			this.left.leaf = false;
		}

		staticIdx++; //Goes to the next index
		if (staticIdx > encodingString.length() - 1) {
			return;
		}
		val = encodingString.charAt(staticIdx);
		if (val == '^') {
			this.right.staticIdx++;
			this.right = new MsgTree(encodingString);
			this.right.leaf = true;
		} else {
			this.right = new MsgTree(val);
			this.right.leaf = false;
		}

		if (staticIdx > encodingString.length() - 1) {
			return;
		}
	}

	// Constructor for a single node with null children
	public MsgTree(char payloadChar) {
		this.left = null; 
		this.right = null;
		this.payloadChar = payloadChar;
		this.leaf = false;
	}

	// This method prints the character an their binary code
	public static void printCodes(MsgTree root, String code) {
		if (root.leaf == false) {
			if (root.payloadChar == '\n') {
				System.out.println("    " + "\\n" + "      " + code);
				return;
			}
			System.out.println("    " + root.payloadChar + "       " + code);
			return;
		} else {
			if (root.left != null) { 
				printCodes(root.left, code + "0");
			}
			if (root.right != null) {
				printCodes(root.right, code + "1");
			}
		}
	}

	public static void decode(MsgTree codes, String msg) {
		System.out.println("Message: ");

		MsgTree root = codes;
		MsgTree current = codes;
		String output = "";

		for (int i = 0; i < msg.length(); i++) {
			if (msg.charAt(i) == '0') {
				current = current.left;
			} else {
				current = current.right;
			}
			if (current.leaf == false) {
				output += current.payloadChar;
				current = root;
				charCount++;
			}
		}
		System.out.println(output);
	}

	public static void printStatistics() {
		System.out.println("");
		System.out.println("STATISTICS:");
		System.out.printf("AVG bits/char:       %.2f", ((double) bit) / charCount);
		System.out.println("");
		System.out.println("Total characters:    " + charCount);
		System.out.printf("Space savings:       %.2f", 100 * (1 - ((double) bit / ((double) charCount) / 16)));
		System.out.print("%");
		System.out.println();
	}

	public static void main(String[] args) throws FileNotFoundException, NullPointerException {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter a file name to decode (include .arch): ");
		String fileName = scan.nextLine();
		if (fileName == null) {
			throw new FileNotFoundException("File name is null!");
		}
		scan.close();
		File file = new File(fileName);

		Scanner scanner = new Scanner(file);

		String buildT = scanner.nextLine(); 
		String line2 = scanner.nextLine();
		String code = ""; 

		if (line2.contains("^")) {
			buildT += '\n' + line2;
		} else {
			code = line2;
		}
		MsgTree tree = new MsgTree(buildT);

		while (scanner.hasNextLine()) {
			code += scanner.nextLine();
		}
		System.out.println("");
		System.out.println("");
		System.out.println("character   code");
		System.out.println("-------------------------");

		printCodes(tree, "");
		decode(tree, code); // tree = root of MsgTree
		bit = code.length();
		printStatistics();
	}
}