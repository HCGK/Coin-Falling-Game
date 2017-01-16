package bord;

public class Entry extends BordItem {
	
	 private final BordItem[] drop;
	 private final int[] dropCol;
	 public final byte[] coins;
	 
	 public static final char COIN = 'o';
	 public static final int[] COIN_POS = {17,19,21,23,25,27,29,31};

	public Entry(BordItem[] drop, int[] dropCol) {
		this.drop = drop;
		this.dropCol = dropCol;
		coins =	new byte[drop.length];
	}

	@Override
	protected void coinEnter(byte coinType, int col) {
		coins[col] += coinType;
	}

	@Override
	public boolean drop() {
		System.out.println("u");
	    boolean output = false;
	    for(int j=0;j<coins.length;j++){
	    	if(coins[j] !=0){
	    		output = true;
	    		drop[j].coinEnter(coins[j], dropCol[j]);
	    		coins[j] = 0;
	    	}
	    }
	    return output;
	}
	
	@Override
	public String toString(){
		StringBuilder out = new StringBuilder("1 2 3 4 5 6 7 8\r\n | | | | | | | \r\n | | | | | | | \r\n");
		for(int i=0;i<coins.length;i++){
			if(coins[i] != 0){
			 out.setCharAt(COIN_POS[i], COIN);
			}
		}
		return out.toString();
	}
}
