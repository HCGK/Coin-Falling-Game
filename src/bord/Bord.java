package bord;

import engine.AbstractScoreBord;

public class Bord {
	
	public static final int[][] POINTS = {{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
	                                      {34,21,13, 8, 5, 3, 2, 1, 1, 2, 3, 5, 8,13,21,34},
	                                      { 9, 8, 7, 6, 5, 4, 3, 2, 2, 3, 4, 5, 6, 7, 8, 9},
	                                      {64,49,36,25,16, 9, 4, 1, 1, 4, 9,16,25,36,49,64}};
	public final BordItem[] bord = new BordItem[32]; // Roughly from bottom up
	public final BordItem[][] bordFold;
	
	//for the to string and to strings method
	public static final char WHITESPACE = ' ';
	public static final String NEWLINE = "\r\n";
	public static final char DIVIDE = '|';
	
	public Bord(AbstractScoreBord scores, int h){
		int a = 0x20000000;
		bord[0] = new Exit(POINTS, scores);
		for(int i=1;i<9;i++){
			bord[i] = new Lever(bord[0],(i-1)*2,(h&a)!=0);
			h <<= 1;
		}
		for(int i=9;i<16;i++){
			bord[i] = new Lever(bord[i-8],bord[i-7],(h&a)!=0);
			h <<= 1;
		}
		for(int i=16;i<22;i++){
			bord[i] = new Lever(bord[i-7],bord[i-6],(h&a)!=0);
			h <<= 1;
		}
		for(int i=22;i<27;i++){
			bord[i] = new Lever(bord[i-6],bord[i-5],(h&a)!=0);
			h <<= 1;
		}
		for(int i=27;i<31;i++){
			bord[i] = new Lever(bord[i-5],bord[i-4],(h&a)!=0);
			h <<= 1;
		}
		bord[31] = new Entry(new BordItem[]{bord[27],bord[27],bord[28],bord[28],bord[29],bord[29],bord[30],bord[30]}, new int[]{0,1,0,1,0,1,0,1});
		bordFold = new BordItem[][]{{bord[31]},{bord[27],bord[28],bord[29],bord[30]},{bord[22],bord[23],bord[24],bord[25],bord[26]},{bord[16],bord[17],bord[18],bord[19],bord[20],bord[21]},{bord[9],bord[10],bord[11],bord[12],bord[13],bord[14],bord[15]},{bord[1],bord[2],bord[3],bord[4],bord[5],bord[6],bord[7],bord[8]},{bord[0]}};
	}
	public void enter(int col){
		bordFold[0][0].coinEnter((byte) 1, col);
	}
	
	public int[] bigHash(){
		int[] output = {0,0};
		for(int i=1;i<bord.length-1;i++){
			Lever l = (Lever) bord[i];
			if(l.direction)
				output[0]++;
			if(l.coins[1][0] != 0 || l.coins[1][1] != 0)
				output[1]++;
			output[0] <<= 1;
			output[1] <<= 1;
		}
		return output;
	}
	
	public boolean drop(){
		System.out.println("z");
		boolean out = false;
		for(BordItem b : bord){
			//leads with board to make sure it runs
			out = b.drop() | out;
		}
		return out;
	}
	
	@Override
	public String toString(){
		String[] LEFT = {"  ||","  ||","  ||","  ||","  ||"," // " , "//  "};
		String[] RIGHT = {  "||",  "||",  "||",  "||",  "||"," \\\\","  \\\\"};
		StringBuilder out = new StringBuilder();
		int pad = 8;
		for(BordItem[] b:bordFold){
			String[][] parts = new String[b.length][];//7 lines for lever rows
			for(int i=0;i<b.length;i++){
				parts[i] = b[i].toString().split(NEWLINE);
			}
			for(int r=0;r<parts[0].length;r++){
				for(int i=0;i<pad;i++){
					out.append(WHITESPACE);
				}
				out.append(LEFT[r]);
				for(int c=0;c<parts.length-1;c++){
					out.append(parts[c][r]);
					out.append(DIVIDE);
				}
				out.append(parts[parts.length-1][r]);
				out.append(RIGHT[r]);
				out.append(NEWLINE);
			}
			if (b.length == 7){
				LEFT[5] = LEFT[6] = "  ||";
				RIGHT[5] = RIGHT[6] = "||";
			}
			if(b.length != 1 && b.length != 8){
				pad-=2;
			}
		}
		return out.toString();
	}
}
