package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StreamerTest {
	Town t1 = new Town(4,4);
	Streamer s1 = new Streamer(t1, 1, 2);
	
	@Test 
	public void test() {
		assertEquals(s1.who(), State.STREAMER);
	}
}
