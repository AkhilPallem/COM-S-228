package edu.iastate.cs228.hw1;

/**
 * @author Akhil Pallem
 *
 *This class extends Towncell and modifies certain Varibles based off neighborhoods and keeps track of RESELLER
 */
public class Reseller extends TownCell {

	public Reseller(Town p, int r, int c) {
		super(p, r, c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public State who() {
		// TODO Auto-generated method stub
		return State.RESELLER;
	}

	@Override
	public TownCell next(Town tNew) {
		int[] nCensus = new int[5];
		census(nCensus);
		if(nCensus[CASUAL] <= 3 || nCensus[EMPTY] >= 3) {
			return new Empty(tNew, row, col);
		}else if(nCensus[CASUAL] <= 5) {
			return new Streamer(tNew, row, col);
		}else {
			return new Reseller(tNew, row, col);	
		}
	}

}
