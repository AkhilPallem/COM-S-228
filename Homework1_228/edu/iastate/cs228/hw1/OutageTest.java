package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OutageTest {
	Town t1 = new Town (4, 4);
	Outage o1 = new Outage (t1, 1, 2);
	
	@Test
	public void test() {
		assertEquals(o1.who(), State.OUTAGE);
	}

}
