package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TownTest {
	Town t1 = new Town(4,4);
	
	@Test
	public void test() {
		assertEquals(t1.getLength(), 4);
	}

}
