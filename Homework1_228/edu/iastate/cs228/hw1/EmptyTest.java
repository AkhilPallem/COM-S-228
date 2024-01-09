package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EmptyTest {
	Town t1 = new Town(4,4);
	Empty e1 = new Empty(t1, 1,2);
	
	@Test
	public void test() {
		assertEquals(e1.who(), State.EMPTY);
	}
	

}
