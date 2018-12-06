package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.PriorityBlockingQueue;

public class trainTask implements Runnable{

	private pipeUtil PU;
	private Stack<Integer> bestScores;
	private PriorityBlockingQueue<bird> fatigue;
	private PriorityBlockingQueue<bird> newborn;
	private List<bird> bl;
	private int step;
	
	
	public trainTask(pipeUtil PU, Stack<Integer> bestScores, PriorityBlockingQueue<bird> fatigue, PriorityBlockingQueue<bird> newborn, List<bird> bl) {
		this.PU = PU;
		this.bestScores = bestScores;
		this.fatigue = fatigue;
		this.newborn = newborn;
		this.bl = bl;
	}
	
	
	@Override
	public void run() {
			int step = Main.getStep(); 
			while(step<generation.max_iter_step) {
//			System.out.println("generation:"+step);
				
			step = Main.getStep();
			if(step==0) {
				for(int i=0;i<200;i++) {
					bird bird = new bird("g"+step+"_"+Thread.currentThread().getName());
					generation.singleTask(bird, new stage(generation.stage_length, generation.stage_height, PU), fatigue);
				}
				
			}
			else {
				for(int i=0;i<200;i++) {
					generation.singleTask(newborn.poll(), new stage(generation.stage_length, generation.stage_height, PU), fatigue);
					
				}
			
			}
			try {
				Main.getBarrier().await();
				step = Main.getStep();
				synchronized (Main.lock){
					if(Main.getBarrierStep()!=step) {
						Main.getBarrier().reset();
						Main.setBarrierStep(step);
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//			System.out.println("bestScore:"+bestScores.peek());
		
		}
	}
}
