package Model;

import java.util.Queue;
import java.util.Random;

public class pipeUtil {
	static int cap = generation.stage_length/generation.stage_height;
	public int[] start = new int[cap];
	public int[] topEdgeOfGround = new int[cap];
	public int[] botEdgeOfCeil = new int[cap];
	public int[] fruit_x = new int[cap];
	public int[] fruit_y = new int[cap];
	
	public void loadFruit(grid[][] grids) {
		for(int i=0;i<cap;i++) {
			grids[fruit_x[i]][fruit_y[i]].setGridStat(grid.fruit);
		}
		
	}
	
	public void initPipe() {
		Random r = new Random();
		int interval = generation.stage_height;
		int ini_start = interval;
		for(int i=0;i<cap;i++) {
			start[i] = ini_start;
			topEdgeOfGround[i] = r.nextInt(generation.stage_height-3);
			botEdgeOfCeil[i] = topEdgeOfGround[i]+3+r.nextInt(generation.stage_height-topEdgeOfGround[i]-3);
			if(i==0) {
				fruit_y[i] = r.nextInt(generation.stage_height-2)+1;
				fruit_x[i] = r.nextInt(start[0]);
			}
			else {
				fruit_y[i] = r.nextInt(generation.stage_height-2)+1;
				fruit_x[i] = start[i-1]+1+r.nextInt(interval-2);
			}
			ini_start += interval;
		}
	}
	public void loadPipe(grid[][] grids,Queue<Integer> checkpoints) {
		for(int i=0;i<cap;i++) {
			for(int h=0;h<generation.stage_height;h++) {
				if(!grids[start[i]][h].isBoundary()) {
					if(h<=topEdgeOfGround[i]){
						grids[start[i]][h].setGridStat(grid.groundPipe);
					}
					else if(h>= botEdgeOfCeil[i]) {
						grids[start[i]][h].setGridStat(grid.ceilPipe);
					}
				}
			} 
			checkpoints.add(start[i]);
			checkpoints.add(botEdgeOfCeil[i]);
			checkpoints.add(topEdgeOfGround[i]);
		}
	}
}