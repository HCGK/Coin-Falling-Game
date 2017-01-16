package engine;

public class StanderdScoreBord extends AbstractScoreBord implements Cloneable{
	
	public int currentplayer = 0;
	private boolean playertarget = false;
	private boolean roundend = false;
	
	public StanderdScoreBord() {
		super(new int[]{10,40,20,80}, new PlayerScore[2]);
		player[0] = new PlayerScore();
		player[1] = new PlayerScore();
	}
	
	@Override
	public void incressScore(int a){
		playertarget = playertarget || player[currentplayer].incressScore(a);
	}
	
	@Override
	public boolean endTurn(){
		currentplayer = (currentplayer + 1)%2;
		if(roundend){
			playertarget = false;
			round ++;
			if (round == TARGET.length){
				return true;
				//note this ends the method
			}
		}
		roundend = playertarget;
		return false;
	}
	
	@Override
	public void setPlayer(int i){
		currentplayer = i;
	}
	
	@Override
	public String toString(){
		char[] DIGIT = {'0','1','2','3','4','5','6','7','8','9'};
		StringBuilder out = new StringBuilder("\t   /10\t\t   /10\r\n\t   /40\t\t   /40\r\n\t   /20\t\t   /20\r\n\t   /80\t\t   /80\r\n");
		int i = 1;
		for(int r=0;r<4;r++){
			out.setCharAt(i, DIGIT[player[0].score[r]/100]);
			i++;
			out.setCharAt(i, DIGIT[player[0].score[r]%100/10]);
			i++;
			out.setCharAt(i, DIGIT[player[0].score[r]%10]);
			i+=6;
			out.setCharAt(i, DIGIT[player[1].score[r]/100]);
			i++;
			out.setCharAt(i, DIGIT[player[1].score[r]%100/10]);
			i++;
			out.setCharAt(i, DIGIT[player[1].score[r]%10]);
			i+=7;
		}
		return out.toString();
	}
}
