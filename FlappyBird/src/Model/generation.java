package Model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.PriorityBlockingQueue;

public class generation {
	public static final int max_iter_step = 10000;
	public static final int generation_scale = 1000;
	public static final int stage_length = 140;
	public static final int stage_height= 15;
	public static final double mutat_ratio = 0.0001;
	private int iter_step = 0;
	static PriorityBlockingQueue<bird> pq = new PriorityBlockingQueue<>();
	
	public static void singleTask(stage newstage) {
		bird b1 = new bird("b1");
		b1.startHerEvilLife(newstage);
		doLog(b1,"utf-8","vicissitude.txt");
	}
	
	public static void singleTask(bird bird, stage newstage, PriorityBlockingQueue<bird> fatiguedBird) {
		bird.startHerEvilLife(newstage);
		fatiguedBird.add(bird);
	}
	
	public static void Roulette_Wheel_Selection(List<bird> selectedBirds,PriorityBlockingQueue<bird> fatiguedBird) {
		Random r =  new Random();
		int count = 0;
		int ScoreSum = 0;
		List<Integer> wheel = new ArrayList<Integer>();
		if(!selectedBirds.isEmpty()) selectedBirds.clear();
		Object[] arr = fatiguedBird.toArray();
		Arrays.sort(arr);
		for(Object bird:arr) {
			ScoreSum += ((bird)bird).getScore();
            wheel.add(ScoreSum);
		}
		while(count++<500) {
			int rand = r.nextInt(ScoreSum);
			for(int i=0;i<wheel.size();i++) {
				if(rand<wheel.get(i)) {
					selectedBirds.add((bird)arr[i]);
					break;
				}
			}
		}
	}
	
	public static void Rank_Selection(List<bird> bestBirds,PriorityBlockingQueue<bird> fatiguedBird) {
		if(!bestBirds.isEmpty()) bestBirds.clear();
		int count = 0;
		while(count++<10) {
			bestBirds.add(fatiguedBird.poll());
		}
	}
	
	public static void offSpringBreed(PriorityBlockingQueue<bird> newborn,PriorityBlockingQueue<bird> fatiguedBird,List<bird> bl,String generation_id,Stack<Integer> bestScores) {
		Roulette_Wheel_Selection(bl,fatiguedBird);
		bird bird_B = fatiguedBird.poll();
//		bird bird_M = fatiguedBird.poll();
		if(bird_B.getScore()>bestScores.peek()) {
			doLog(bird_B,"utf-8",bird_B.getB_name()+".txt");
			System.out.println(bird_B.getB_name()+" : "+bird_B.getScore());
			bird_B.getStage().draw();
			bestScores.push(bird_B.getScore());
		}
		newborn.clear();
		bl.sort(null);
		int count = 0;
		for(int i=0;i<100;i++) {
			newborn.add(bl.get(i));		
		}
		for(int i=0;i<500;i++) {
			bird bird_F = bl.get(i);
			bird bird_M = bl.get(++i);
			newborn.add(new bird(bird_F,bird_M,true,generation_id,mutat_ratio,false));
			newborn.add(new bird(bird_F,bird_M,false,generation_id,mutat_ratio,false));
		}
		while(count++<400) {
			newborn.add(new bird(generation_id));
		}
//		count = 0;
//		while(count++<5) {
//			newborn.add(new bird(bird_F,bird_M,true,generation_id,mutat_ratio,false));
//			newborn.add(new bird(bird_F,bird_M,false,generation_id,mutat_ratio,false));
//		}
		fatiguedBird.clear();
	}
	
	public static void main(String[] args) {
	}
	
	public static void doLog(bird bird, String code, String filename) {
		String home = System.getProperty("user.home");
		String dir = home + File.separator + "Documents"+ File.separator + "6205" + File.separator  + "FinalProject" + File.separator + "history" + File.separator + filename ;
		OutputStreamWriter osw = null;
		String result = "Name: ";
		result += bird.getB_name()+"\n";
		result += "Score: ";
		result += bird.getScore()+"\n";
		result += "Whole life: Start";
		result += bird.getWholeLife();
		result += " -> Died\n";
		result += "Gene sequence:";
		for(gene g: bird.getGenes()) {
			result += " -> "+g;
			
		}
		result += "\n";
		
		try {
			File file = new File(dir);
			if(!file.exists()){
				File file_p = new File(file.getParent());
				if(!file_p.exists()){
					file_p.mkdirs();
				}
				file.createNewFile();
			}
			if("asci".equals(code)){
				code = "utf-8";
			}
			osw = new OutputStreamWriter(new FileOutputStream(dir),code);
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
	
}