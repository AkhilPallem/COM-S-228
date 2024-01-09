package edu.iastate.cs228.hw1;

/**
 * @author Akhil Pallem
 *
 *This class extends Towncell and modifies certain Varibles based off neighborhoods and keeps track of the CASUAL
 */
public class Casual extends TownCell {

	public Casual(Town p, int r, int c) {
		super(p, r, c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public State who() {
		// TODO Auto-generated method stub
		return State.CASUAL;
	}

	@Override
	public TownCell next(Town tNew) {
		int[] nCensus = new int[5];
		census(nCensus);
		if(nCensus[OUTAGE] + nCensus[EMPTY] <= 1) {
			return new Reseller(tNew, row, col);
		}else if(nCensus[RESELLER] > 0) {
			return new Outage(tNew, row, col);
		}else if(nCensus[STREAMER] > 0 || nCensus[CASUAL] >= 5) {
			return new Streamer(tNew, row, col);
		}else {
			return new Casual(tNew, row, col);
		}
	}

}