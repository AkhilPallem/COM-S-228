package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ISPBusinessTest {
	Town t1 = new Town(4,4);
	
	@Test
	public void test() {
		t1.randomInit(10);
		assertEquals(ISPBusiness.getProfit(t1), 1);
	}

}
