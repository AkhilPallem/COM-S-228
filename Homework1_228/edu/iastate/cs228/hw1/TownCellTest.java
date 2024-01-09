package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class TownCellTest {
	Town t1 = new Town(4,4);
	
	@Test
	public void test2() { 
		t1.grid[0][0] = new Reseller(t1, 0, 0);
		t1.grid[1][2] = new Casual(t1, 1, 2);
		t1.grid[0][1] = new Empty(t1, 0, 1);
		t1.grid[0][2] = new Casual(t1, 0, 2);
		t1.grid[2][2] = new Outage(t1, 2, 2);
		t1.grid[1][0] = new Streamer(t1, 1, 0);
		t1.grid[2][1] = new Casual(t1, 2, 1);
		t1.grid[1][1] = new Reseller(t1, 1, 1);
		t1.grid[2][0] = new Empty(t1, 2, 0);
		int[] nCensus_test = new int[TownCell.NUM_CELL_TYPE];
		t1.grid[1][1].census(nCensus_test);
		
		assertEquals(1, nCensus_test[TownCell.RESELLER]);
		assertEquals(1, nCensus_test[TownCell.OUTAGE]);
		assertEquals(2, nCensus_test[TownCell.EMPTY]);
		assertEquals(3, nCensus_test[TownCell.CASUAL]);
		assertEquals(1, nCensus_test[TownCell.STREAMER]);
	}


}
