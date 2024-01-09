package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ResellerTest {
	Town t1 = new Town (4, 4);
	Reseller r1 = new Reseller(t1, 1, 2);
	@Test
	public void test()
	{
		assertEquals(r1.who(), State.RESELLER);
	}
}
