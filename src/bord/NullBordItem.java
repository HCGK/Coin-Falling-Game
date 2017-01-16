package bord;

public class NullBordItem extends BordItem {
	
	public static final NullBordItem NULLBORDITEM = new NullBordItem();
	
	protected void coinEnter(byte coinType, int col) {
	}

	@Override
	public boolean drop() {
		// TODO Auto-generated method stub
		return false;
	}

}
