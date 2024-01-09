package edu.iastate.cs228.hw1;

/**
 * @author Akhil Pallem
 *
 *This class extends Towncell and modifies certain Varibles based off neighborhoods and keeps track of the EMPTY
 */
public class Empty extends TownCell {

	public Empty(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	public State who() {
		// TODO Auto-generated method stub
		return State.EMPTY;
	}

	@Override
	public TownCell next(Town tNew) {
		int [] nCensus1 = new int[5];
		census(nCensus1);
		if(nCensus1[OUTAGE] + nCensus1[EMPTY] <= 1) {
			return new Reseller(tNew, row, col);
		}
		return new Casual(tNew, row, col);
	}

}
