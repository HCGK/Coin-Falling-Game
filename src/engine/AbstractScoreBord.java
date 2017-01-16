package engine;

import java.util.Arrays;

public abstract class AbstractScoreBord implements Cloneable{
	/*
	 * aims:
	 * set up easy
	 * runs game as scoring/round keeping engine
	 * 
	 * input:
	 *     end of player turn (to be determined when there are no coins on the bored)
	 *     any scoring events (e.g. coin at bottom) 
	 * output:
	 *     current scores (by extending animation element)
	 *     winner
	 * 
	 * engine process
	 *     a players score is set as the sum of there score for each round
	 *     when either player reaches or exceeds the target for a round
	 *       they finish there turn then the other player takes a full turn 
	 *       then the round is incremented
	 *     after the final round the player with the most points wins
	 *     this may not be the player how ended or won the final round
	 */
	
	public final int[] TARGET;
	public int round = 0;
	protected final PlayerScore[] player;
	
	
	public AbstractScoreBord(int[] TARGET, PlayerScore[] player){
		this.TARGET = TARGET;
		this. player = player;
	}
	public AbstractScoreBord(AbstractScoreBord orginal){
		this.TARGET = orginal.TARGET.clone(); //should be deep copy for primitive
		this.round = orginal.round;
		this.player = orginal.player.clone();
		//the fun thing about final arrays is it dosn't mean much 
		for(int i=0;i<player.length;i++){
			player[i] = orginal.player[i].clone();
		}
	}
	
	public abstract void incressScore(int a);
		
	public abstract boolean endTurn();
		
	protected void endGame() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * should be overwritten when play events not obvious from score can effect play state
	 * but when over written [0][0] should remain hashCode() and [1][] playerScores
	 * should be fit for use when ever the board is stable
	 * 
	 * @return a hash of ScorBoard state that is near collision proof and data carrying
	 */
	public int[][] bigHash() {
		int[] scoreHash = new int[player.length];
		for(int i=0;i<player.length;i++){
			scoreHash[i]=player[i].hashCode();
		}
		return new int[][]{{hashCode(),round},scoreHash};
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = this.getClass().hashCode();
		result = prime * result + Arrays.hashCode(TARGET);
		return result;
	}
	/**
	 * when necessary overwrite but maintain the following:
	 * Two ScoreBords are equal when the precise rules are equal
	 * for all rules controlled by the ScoreBoard class in question
	 *  
	 *  ScoreBord.equals is not effected by changes made during play
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractScoreBord other = (AbstractScoreBord) obj;
		if (!Arrays.equals(TARGET, other.TARGET))
			return false;
		return true;
	}




	public final class PlayerScore implements Cloneable, Comparable<PlayerScore>{
		public int[] score = {0,0,0,0};
		public PlayerScore(){}//main constructor;
		public PlayerScore(PlayerScore p){
			score = p.score.clone();
		}
		
		public boolean incressScore(int a){
			score[round] += a;
			return score[round]>TARGET[round];
		}
		
		public int totalScore(){
			int sum = 0;
			for(int i: score){
				sum += i;
			}
			return sum;
		}
		@Override
		public int hashCode(){
			return score[0] + (score[1]<<6) + (score[2]<<15) + (score[3]<<22);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PlayerScore other = (PlayerScore) obj;
			if (!Arrays.equals(score, other.score))
				return false;
			return true;
		}
		@Override
		public PlayerScore clone(){
			return new PlayerScore(this);
		}
		
		/**
		 * Compares this Player with another for the leading player.
		 * 
		 * Takes difference when both are weekly positive or strictly negative;
		 * Uses INTEGER.MAX_VALUE OR INTEGER.MIN_VALUE
		 *  
		 * @param o - the object player to compare to
		 * @return a negative, zero, or a positive if this player beaten, drawing with, or beating o
		 */
		public int compareTo(PlayerScore o){
			if(this.totalScore()>=0){
				if(o.totalScore()>=0)
					return this.totalScore() - o.totalScore();
				//else
					return Integer.MAX_VALUE;
			}
			//else
				if(o.totalScore()<0)
					return this.totalScore() - o.totalScore();
				//else
					return Integer.MIN_VALUE;
		}
		
	}




	public abstract void setPlayer(int i);
}