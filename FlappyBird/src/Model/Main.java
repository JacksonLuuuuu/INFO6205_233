package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.PriorityBlockingQueue;


public class Main {

	private static int threadNum = 5;
	private static Thread[] threads = new Thread[threadNum];
	private static int step = 0;
	private static int barrierStep = 0;
	public static Object lock = new Object();
	private static Stack<Integer> bestScores;
	private static PriorityBlockingQueue<bird> fatigue;
	private static PriorityBlockingQueue<bird> newborn;
	private static List<bird> bl;
	
	
	private static CyclicBarrier barrier = new CyclicBarrier(threadNum, new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(!newborn.isEmpty()) {
				System.out.println("g"+step+": initial newborn is not empty Exception");
			}
			Main.step++;
			generation.offSpringBreed(newborn,fatigue,bl,"g"+step+"_"+Thread.currentThread().getName(),bestScores);
//			System.out.println(newborn.size());
			if(!fatigue.isEmpty()) {
				System.out.println("g"+step+": fatigue is not killed after breeding Exception");
			}
			if(newborn.isEmpty()) {
				System.out.println("g"+step+": newborn is still empty after breeding Exception");
			}
		}
	});
	
	public static int getStep() {
		return step;
	}
	
	public static void setStep(int step) {
		Main.step = step;
	}
	
	public static int getBarrierStep() {
		return barrierStep;
	}
	
	public static void setBarrierStep(int barrierStep) {
		Main.barrierStep = barrierStep;
	}
	
	public static CyclicBarrier getBarrier() {
		return barrier;
	}

	
	
	public static void main(String[] args) {
		pipeUtil PU = new pipeUtil();
		PU.initPipe();
		bestScores = new Stack<>();
		bestScores.push(0);
		fatigue = new PriorityBlockingQueue<>();
		newborn = new PriorityBlockingQueue<>();
		bl = new ArrayList<>();
		for(int i=0; i<threadNum; i++) {
			threads[i] = new Thread(new trainTask(PU, bestScores, fatigue, newborn, bl));
		}
		for(int i=0; i<threadNum; i++) {
			threads[i].start();
		}
		for(int i=0; i<threadNum; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
