package Model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

public class stage implements Cloneable{
	private int length;
	private int height;
	private int herEvilLifStopHere;
	private int ini_bpX = 0;
	private int ini_bpY = height/2;
	Queue<Integer> checkpoints = new LinkedList<Integer>();
	Queue<Integer> dis2edge = new LinkedList<Integer>();
	Queue<birdCase> eventTriggered = new LinkedList<birdCase>();
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	private grid[][] grids;
	
	public grid[][] getGrids() {
		return grids;
	}
	public void setGrids(grid[][] grids) {
		this.grids = grids;
	}
	public int getHerEvilLifStopHere() {
		return herEvilLifStopHere;
	}
	public void setHerEvilLifStopHere(int herEvilLifStopHere) {
		this.herEvilLifStopHere = herEvilLifStopHere;
	}
	public Queue<birdCase> getEventTriggered() {
		return eventTriggered;
	}
	public void setEventTriggered(Queue<birdCase> eventTriggered) {
		this.eventTriggered = eventTriggered;
	}		
	public Queue<Integer> getDis2edge() {
		return dis2edge;
	}
	public void setDis2edge(Queue<Integer> dis2edge) {
		this.dis2edge = dis2edge;
	}
	public stage(int l,int h,pipeUtil pipeUtil) {
		this.length = l;
		this.height = h;
		grids = new grid[this.length][this.height];
		init(pipeUtil);
	}
	
	public void init(pipeUtil pipeUtil) {
		int l;
		int h;
		for(l=0;l<this.length;l++) {
			for(h=0;h<this.height;h++) {
				grids[l][h] = new grid(grid.grid);
			}
		}
		for(l=0;l<this.length;l++) {
			grids[l][0].setGridStat(grid.ground);
			grids[l][this.height-1].setGridStat(grid.ceil);
		}
		for(h=0;h<this.height;h++) {
			grids[this.length-1][h].setGridStat(grid.end);
		}
		pipeUtil.loadPipe(this.grids,checkpoints);
		pipeUtil.loadFruit(this.grids);
		applyBird();
	}
	public void applyBird() {
		grids[0][height/2].setGridStat(grid.bird);
		this.bp.setXY(0,height/2);
	}
	public birdCase moveBird(gene move) {
		int pred_x;
		int pred_y;
		int cur_x = bp.getX();
		int cur_y = bp.getY();
		switch(move) {
			case Hover:{
				pred_x = cur_x+1;
				pred_y = cur_y;
				return check_update(pred_x,pred_y,false);
			}
			case FlapUp:{
				pred_x = cur_x+1;
				pred_y = cur_y+1;
				return check_update(pred_x,pred_y,false);
			}
			case FlapDown:{
				pred_x = cur_x+1;
				pred_y = cur_y-1;
				return check_update(pred_x,pred_y,false);
			}
			case Hover_Eat:{
				pred_x = cur_x+1;
				pred_y = cur_y;
				return check_update(pred_x,pred_y,true);
			}
			case FlapUp_Eat:{
				pred_x = cur_x+1;
				pred_y = cur_y+1;
				return check_update(pred_x,pred_y,true);
			}
			case FlapDown_Eat:{
				pred_x = cur_x+1;
				pred_y = cur_y-1;
				return check_update(pred_x,pred_y,true);
			}
			default: return birdCase.Flying;
		}
	}
	
	public birdCase eat(int x,int y) {
		if(grids[x][y].isFruit()) {
			eventTriggered.add(birdCase.Eating);
			grids[x][y].setGridStat(grid.birdeating);
			return birdCase.Eating;
		}
		else {
			grids[x][y].setGridStat(grid.bird);
			eventTriggered.add(birdCase.Empty_handed);
			return birdCase.Empty_handed;
		}
	}
	
	public birdCase check_update(int x,int y, boolean eat) {
		if(grids[x][y].isBoundary()) {
			if(grids[x][y].isEnd()) {
				eventTriggered.add(birdCase.TouchEnd);
				this.setHerEvilLifStopHere(x);
				return birdCase.TouchEnd;
			} 
			else {
				eventTriggered.add(birdCase.TouchBoundary);
				if(grids[x][y].getGridStat() == 4) {
					return check_update(x,y-1,eat);
				}
				else {
					return check_update(x,y+1,eat);
				}
			}
		}
		else if(grids[x][y].isTouchable()) {
			if(!checkpoints.isEmpty() && x==checkpoints.element()) {
				checkpoints.remove();
				dis2edge.add(Math.min(checkpoints.remove()-y, y-checkpoints.remove()));
				eventTriggered.add(birdCase.CrossSlit);
			}
			bp.setXY(x,y);
			if(eat) {
				eat(x,y);
			}
			else if(!grids[x][y].isFruit()) grids[x][y].setGridStat(grid.bird);
			eventTriggered.add(birdCase.Flying);
			return birdCase.Flying;
		} 
		else {
			this.setHerEvilLifStopHere(x);
			eventTriggered.add(birdCase.TouchPipe);
			return birdCase.TouchPipe;
		}
	}
	public void draw() {
		String home = System.getProperty("user.home");
		String dir = home + File.separator + "Documents"+ File.separator + "6205" + File.separator  + "FinalProject" + File.separator + "history" + File.separator + "vicissitude" ;
		OutputStreamWriter osw = null;
		String result = "";
		
		for(int h=this.height-1;h>=0;h--) {
			for(int l=0;l<this.length;l++) {
				switch(grids[l][h].getGridStat()) {
					case 0: System.out.print("  ");result+="  ";break;
					case 1: System.out.print("_/");result+="_/";break;
					case 2: System.out.print("||");result+="||";break;
					case 3: System.out.print("||");result+="||";break;
					case 4: System.out.print("--");result+="--";break;
					case 5: System.out.print("--");result+="--";break;
					case 6: System.out.print("^");result+="^";break;
					case 7: System.out.print("**");result+="**";break;
					case 8: System.out.print("<o");result+="<o";break;
					default: System.out.print("  ");result+="  ";break;
				}
			}
			result+="\n";
			System.out.println();
		}
		result+="\n";
		
		try {
			File file = new File(dir);
			if(!file.exists()){
				File file_p = new File(file.getParent());
				if(!file_p.exists()){
					file_p.mkdirs();
				}
				file.createNewFile();
				osw = new OutputStreamWriter(new FileOutputStream(dir),"utf-8");
			}
			else{
				osw = new OutputStreamWriter(new FileOutputStream(dir,true),"utf-8");
            }
			osw.write(result);
			osw.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(osw != null){
					osw.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public Object clone() {
        stage stage = null;
        try {
            stage = (stage) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        
        return stage;
    }
	
	public class birdPosition{
		private int x;
		private int y;
		birdPosition(int x,int y){
			this.x = x;
			this.y = y;
		}
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		public void setXY(int x,int y) {
			this.x = x;
			this.y = y;
		}
	}
	private birdPosition bp = new birdPosition(ini_bpX,ini_bpY);

	public birdPosition getBp() {
		return bp;
	}
	public void setBp(birdPosition bp) {
		this.bp = bp;
	}
}