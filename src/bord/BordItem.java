package bord;
public abstract class BordItem {
	
	/* 
	 * Sample Fields
	 * private final BordItem[] drop;
	 * private final int[] dropCol;
	 * private final byte[][] coins;
	 */
	

	
	/**
	 * the method to pass in coins
	 * should add a coin to the higest vallue of coins
	 * 
	 * @param coinType First number of coins, Second other
	 * @param col
	 */
	protected abstract void coinEnter(byte coinType, int col);
		
	/**
	 * Control segments through which the coins pass
	 * 
	 * @return if a coin remains in the system 
	 *            (test on part being left)
	 *            e.g. if at start there is a non zero value in coins
	 */
	public abstract boolean drop();
	
	/* 	
	 * SAMPLE Note nothing works perfectly like this
	 * 
	 *  boolean output = false;
	 *  for(int j=0;j<coins[coins.length-1].length;j++){
	 *  output = output || (coins[coins.length-1][j] != 0); //output OR coins has non zero value
	 *  drop[j].coinEnter(coins[coins.length-1][j], dropCol[j]);
	 *}
	 *  for(int i=coins.length-2;i>=0;i--){
	 *  for(int j=0;j<coins[i].length;j++){
	 *  	output = output || (coins[i][j] != 0); //output OR coins has non zero value
	 *  	coins[i+1][j] += coins[i][j];
	 *  	coins[i][j] = 0;
	 *  }
	 *  return output;
	 */
}