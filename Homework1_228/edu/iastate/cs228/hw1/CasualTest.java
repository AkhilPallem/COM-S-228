package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class CasualTest {
	Town t1 = new Town(4,4);
	Casual c1 = new Casual(t1, 1, 2);
	
	@Test 
	public void test() {
		assertEquals(c1.who(), State.CASUAL);
	}
}