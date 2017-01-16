package engine;

import java.util.Scanner;

import bord.Bord;

public class Game {
	public final AbstractScoreBord scores = new StanderdScoreBord();
	public final Bord bord;
	
	public Game(){
		int h = 0x40000001;
		if((h&0x40000000) == 0){
			System.out.println("left player to start");
		}else{
			System.out.println("right player to start");
			scores.setPlayer(1);
		}
		bord = new Bord(scores, h);
	}
	
	public boolean turn(int col){
		bord.enter(col);
		//this loop is some what ugly as the main change is also the test
		
		int i = 0;
		do{
			System.out.println(this);
			i++;
		}while(i<5 & bord.drop());
		return scores.endTurn();
	}
	
	public void cmdGame(){
		boolean b;
		Scanner s = new Scanner(System.in);
		do{
			int col = -1;
			System.out.println("pick a column");
			do{
				try{
					String string = s.next();
					col = Integer.parseInt(string);
					col--;
					if(col<0 || 7<col){
						throw new IndexOutOfBoundsException();
					}
				}catch(Exception e){
					System.out.println("try again");
					col = -1;
				}
			}while(col == -1);
			System.out.println("out");
			b = turn(col);
			System.out.println(this);
		}while(b);
		s.close();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(scores);
		builder.append("\r\n");
		builder.append(bord);
		return builder.toString();
	}
	
	public static void main(String[] args){
		Game game = new Game();
		System.out.println(game);
		game.cmdGame();
	}
}
