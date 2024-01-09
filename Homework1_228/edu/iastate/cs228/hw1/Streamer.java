package edu.iastate.cs228.hw1;

/**
 * @author Akhil Pallem
 * 
 *This class extends towncell and modifies certain variables checking neighborhoods and keeps track of STREAMER
 */
public class Streamer extends TownCell {

	public Streamer(Town p, int r, int c) {
		super(p, r, c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public State who() {
		// TODO Auto-generated method stub
		return State.STREAMER;
	}

	@Override
	public TownCell next(Town tNew) {
		int[] nCensus = new int[5];
		census(nCensus);
		if(nCensus[OUTAGE] + nCensus[EMPTY] <= 1) {
			return new Reseller(tNew, row, col);
		}else if(nCensus[RESELLER] > 0) {
			return new Outage(tNew, row, col);
		}else if (nCensus[OUTAGE] > 0) {
			return new Empty(tNew, row, col);
		}
		return new Streamer(tNew, row, col);
	}

}
