package bord;

public class Lever extends BordItem{
	
	public boolean direction; //true = cup:left = lever right, false = cup right = lever left
	private final BordItem[] drop;
	private final int[] dropCol;
	public final byte[][] coins= new byte[4][2] ; //rows: cup(fall), cup(rest), lever, space; col: col (left/right)
	
	//For To String
	public static final String STRING = "   \r\n   \r\n   \r\n | \r\n | \r\n | \r\n | \r\n";
	public static final char COIN = 'o';
	public static final char[][] LEVER = {{'V','\\','\\'},{'V','/','/'}};
	public static final int[][] LEVER_POS = {{5,11,17},{7,11,15}};
	public static final int[][] COIN_POS = {{0,2},{0,2},{15,17},{25,27}};
	
	public Lever(BordItem left,BordItem right, boolean direction){
		drop = new BordItem[]{left,right};
		dropCol = new int[]{1,0};
		this.direction = direction;
	}
	public Lever(BordItem base, int dropCol, boolean direction){
		drop = new BordItem[]{base,base};
		this.dropCol = new int[]{dropCol,dropCol+1};
		this.direction = direction;
	}
	
	@Override
	protected void coinEnter(byte coinType, int col) {
		if (coinType != 0) {
			if((col==1) == direction){//if the coin is falling on the cup
				if(coinType + coins[1][col] > 1){
					coins[0][(col+1)%2] += coinType - 1;
				}
						coins[1][col] = 1;
			}
			else{
				coins[0][col] += coinType;
			}
		}
	}

	@Override
	public boolean drop() {
		boolean output = false;
		byte enterLeft = 0;
		byte enterRight = 0;
		
		//row 3 (space) [no holes code]
		for(int i=0;i<coins[3].length;i++){
			if(coins[3][i] != 0){
				output = true;
				drop[i].coinEnter(coins[3][i], dropCol[i]);
				coins[3][i] = 0;
			}
		}
		//row 2 (lever),left 
		if(coins[2][0] != 0){
			output = true;
			coins[3][0] = coins[2][0];
			if(coins[2][0]%2 == 1){//if odd
				direction = false;
				enterRight = coins[1][1];
				coins[1][1]=0;
			}
			coins[2][0] = 0;
		}
		//row 2 (lever), right
		if(coins[2][1] !=0){
			output = true;
			coins[3][1] = coins[2][1];
			if(coins[2][1]%2 == 1){//if odd
				direction = true;
				enterLeft = coins[1][0];
				coins[1][0] = 0;
			}
			coins[2][1] = 0;
		}
		//fall
		for(int i=0;i<coins[0].length;i++){
			if(coins[0][i] != 0){
				output = true;
				coins[2][i] = coins[0][i];
				coins[0][i] = 0;
			}
		
		}
		coinEnter(enterLeft, 0);
		coinEnter(enterRight, 1);
		return output;
	}
	
	@Override
	public String toString(){
		StringBuilder out = new StringBuilder(STRING);
		int k = direction ? 1 : 0;
		for(int j=0;j<LEVER[0].length;j++){
			out.setCharAt(LEVER_POS[k][j], LEVER[k][j]);
		}
		for(int i=0;i<coins.length;i++){
			for(int j=0;j<coins[i].length;j++){
				if(coins[i][j] != 0){
					out.setCharAt(COIN_POS[i][j], COIN);
				}
			}
		}
		return out.toString();
	}
	public String hString(){
		return coins[0][0] +":"+ coins[0][1] +"#"+ coins[1][0] +":"+ coins[1][1] +"#"+coins[2][0] +":"+ coins[2][1] +"#"+coins[3][0] +":"+ coins[3][1];
	}
	
	public static void main(String[] args){
		byte ONE = 1;
		Lever i = new Lever(NullBordItem.NULLBORDITEM,NullBordItem.NULLBORDITEM,true);
		System.out.println(i.hString());
		System.out.println(i);
		i.coinEnter(ONE, 1);
		System.out.println(i.hString());
		System.out.println(i);
		i.drop();
		System.out.println(i.hString());
		System.out.println(i);
		i.coinEnter(ONE, 0);
		System.out.println(i.hString());
		System.out.println(i);
		i.drop();
		System.out.println(i.hString());
		System.out.println(i);
		i.drop();
		System.out.println(i.hString());
		System.out.println(i);
		i.drop();
		System.out.println(i.hString());
		System.out.println(i);
		i.drop();
		System.out.println(i.hString());
		System.out.println(i);
	}
}
