package bord;

import engine.AbstractScoreBord;

public class Exit extends BordItem {
	
	public final int[][] points;
	private final engine.AbstractScoreBord engine;
	public byte[] coins;
	
	public Exit(int[][] points, AbstractScoreBord engine){
		this.points = points;
		this.engine = engine;
		coins = new byte[points.length];
	}
	
	@Override
	protected void coinEnter(byte coinType, int col) {
		coins[col] += coinType;
	}
	@Override
	public boolean drop() {
		for(int i=0; i<points.length; i++){
			engine.incressScore((coins[i])*points[engine.round][i]);
			coins[i]=0;
		}
		return false;
	}
}
