package Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.PriorityBlockingQueue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import Model.bird;
import Model.birdCase;
import Model.gene;
import Model.generation;
import Model.stage;
import Model.pipeUtil;

@SuppressWarnings("ALL")
public class testCase {
	@Test
	public void birdInitTest1() throws Exception {
		pipeUtil PU = new pipeUtil();
		PU.initPipe();
		stage stage = new stage(generation.stage_length, generation.stage_height, PU);
		System.out.println("<birdInitTest1>");
		bird b = new bird("001");
		b.load(stage);
		b.getStage().draw();
		assertEquals(0, b.getStage().getBp().getX());
		assertEquals(generation.stage_height/2, b.getStage().getBp().getY());
	}
	
	@Test
	public void birdInitTest2() throws Exception {
		pipeUtil PU = new pipeUtil();
		PU.initPipe();
		stage stage = new stage(generation.stage_length, generation.stage_height, PU);
		System.out.println("<birdInitTest2>");
		assertEquals(true, stage.getGrids()[generation.stage_length-1][0].isEnd());
		assertEquals(true, stage.getGrids()[generation.stage_length-1][generation.stage_height/2].isEnd());
		assertEquals(true, stage.getGrids()[generation.stage_length-1][generation.stage_height-1].isEnd());
		assertEquals(true, stage.getGrids()[generation.stage_length-1][generation.stage_height/2].isBoundary());
		assertEquals(true, stage.getGrids()[generation.stage_length/2][0].isBoundary());
		assertEquals(true, stage.getGrids()[generation.stage_length/2][generation.stage_height-1].isBoundary());
	}
	
	@Test
	public void birdMoveTest1() throws Exception {
		pipeUtil PU = new pipeUtil();
		PU.initPipe();
		stage stage = new stage(generation.stage_length, generation.stage_height, PU);
		System.out.println("<birdMoveTest1>");
		bird b = new bird("001");
		b.load(stage);
		assertEquals(birdCase.Flying, b.getStage().moveBird(gene.Hover));
		assertEquals(1, b.getStage().getBp().getX());
		assertEquals(generation.stage_height/2, b.getStage().getBp().getY());
		b.getStage().draw();
	}
	
	@Test
	public void birdMoveTest2() throws Exception {
		pipeUtil PU = new pipeUtil();
		PU.initPipe();
		stage stage = new stage(generation.stage_length, generation.stage_height, PU);
		System.out.println("<birdMoveTest2>");
		bird b = new bird("001");
		b.load(stage);
		assertEquals(birdCase.Flying, b.getStage().moveBird(gene.FlapUp));
		assertEquals(1, b.getStage().getBp().getX());
		assertEquals(generation.stage_height/2+1, b.getStage().getBp().getY());
		b.getStage().draw();
	}
	
	@Test
	public void birdMoveTest3() throws Exception {
		pipeUtil PU = new pipeUtil();
		PU.initPipe();
		stage stage = new stage(generation.stage_length, generation.stage_height, PU);
		System.out.println("<birdMoveTest3>");
		bird b = new bird("001");
		b.load(stage);
		assertEquals(birdCase.Flying, b.getStage().moveBird(gene.FlapDown));
		assertEquals(1, b.getStage().getBp().getX());
		assertEquals(generation.stage_height/2-1, b.getStage().getBp().getY());
		b.getStage().draw();
	}
	@Test
	public void SingleTaskTest() throws Exception {
		pipeUtil PU = new pipeUtil();
		PU.initPipe();
		stage stage = new stage(generation.stage_length, generation.stage_height, PU);
//		stage.draw();
		generation.singleTask(stage);
		stage.draw();
	}
	@Test
	public void StageCloneTest() throws Exception {
		pipeUtil PU = new pipeUtil();
		PU.initPipe();
		generation.singleTask(new stage(generation.stage_length, generation.stage_height, PU));
		generation.singleTask(new stage(generation.stage_length, generation.stage_height, PU));
	}
	@Test
	public void CompareTest() throws Exception {
		System.out.println("<CompareTest>");
		bird b1 = new bird("b1");
		bird b2 = new bird("b2");
		bird b3 = new bird("b3");
		bird b4 = new bird("b4");
		b1.setScore(1);
		b2.setScore(2);
		b3.setScore(3);
		b4.setScore(4);
		PriorityBlockingQueue<bird> pq = new PriorityBlockingQueue<>();
		pq.add(b4);
		pq.add(b2);
		pq.add(b3);
		pq.add(b1);
		assertEquals(pq.poll().getScore(),4);
		assertEquals(pq.poll().getScore(),3);
	}
	@Test
	public void Roulette_Wheel_SelectionTest() throws Exception {
		System.out.println("<Roulette_Wheel_SelectionTest>");
		bird b1 = new bird("b1");
		bird b2 = new bird("b2");
		bird b3 = new bird("b3");
		bird b4 = new bird("b4");
		bird b5 = new bird("b5");
		bird b6 = new bird("b6");
		bird b7 = new bird("b7");
		bird b8 = new bird("b8");
		bird b9 = new bird("b9");
		bird b10 = new bird("b10");
		b1.setScore(1);
		b2.setScore(2);
		b3.setScore(3);
		b4.setScore(4);
		b5.setScore(5);
		b6.setScore(6);
		b7.setScore(7);
		b8.setScore(8);
		b9.setScore(9);
		b10.setScore(100);
		PriorityBlockingQueue<bird> pq = new PriorityBlockingQueue<>();
		pq.add(b4);
		pq.add(b2);
		pq.add(b3);
		pq.add(b1);
		pq.add(b6);
		pq.add(b5);
		pq.add(b10);
		pq.add(b9);
		pq.add(b7);
		pq.add(b8);
		List<bird> bl = new ArrayList<>();
		generation.Roulette_Wheel_Selection(bl,pq);
		for(bird bird:bl) {
			System.out.println(bird.getScore());
		}
	}
	@Test
	public void Rank_SelectionTest() throws Exception {
		System.out.println("<Rank_SelectionTest>");
		bird b1 = new bird("b1");
		bird b2 = new bird("b2");
		bird b3 = new bird("b3");
		bird b4 = new bird("b4");
		bird b5 = new bird("b5");
		bird b6 = new bird("b6");
		bird b7 = new bird("b7");
		bird b8 = new bird("b8");
		bird b9 = new bird("b9");
		bird b10 = new bird("b10");
		b1.setScore(1);
		b2.setScore(2);
		b3.setScore(3);
		b4.setScore(4);
		b5.setScore(5);
		b6.setScore(6);
		b7.setScore(7);
		b8.setScore(8);
		b9.setScore(9);
		b10.setScore(10);
		PriorityBlockingQueue<bird> pq = new PriorityBlockingQueue<>();
		pq.add(b4);
		pq.add(b2);
		pq.add(b3);
		pq.add(b1);
		pq.add(b6);
		pq.add(b5);
		pq.add(b10);
		pq.add(b9);
		pq.add(b7);
		pq.add(b8);
		List<bird> bl = new ArrayList<>();
		generation.Rank_Selection(bl,pq);
		for(bird bird:bl) {
			System.out.println(bird.getScore());
		}
	}
	@Test 
	public void BreedTest() throws Exception {
		pipeUtil PU = new pipeUtil();
		PU.initPipe();
		stage stage = new stage(generation.stage_length, generation.stage_height, PU);
		System.out.println("<BreedTest>");
		Stack<Integer> bestScores = new Stack<>();
		bestScores.push(0);
		
		PriorityBlockingQueue<bird> fatigue = new PriorityBlockingQueue<>();
		PriorityBlockingQueue<bird> newborn = new PriorityBlockingQueue<>();
		System.out.println("bestScore:"+bestScores.peek());
		for(int i=0;i<20;i++) {
			bird bird = new bird("g0");
			generation.singleTask(bird, stage, fatigue);
		}
		System.out.println("fatigue:");
		for(bird bird:fatigue) {
			System.out.println(bird.getScore());
		}
		List<bird> bl = new ArrayList<>();
		generation.offSpringBreed(newborn,fatigue,bl,"g1",bestScores);
		System.out.println("bestScore:"+bestScores.peek());
		System.out.println("newborn");
		for(bird bird:newborn) {
			System.out.println(bird.getScore());
			generation.singleTask(bird, stage, fatigue);
		}
		System.out.println("fatigue:");
		for(bird bird:fatigue) {
			System.out.println(bird.getScore());
		}
		generation.offSpringBreed(newborn,fatigue,bl,"g2",bestScores);
		System.out.println("bestScore:"+bestScores.peek());
		for(bird bird:newborn) {
			System.out.println(bird.getScore());
		}
	}
//	@Test
	public void TrainTest() throws Exception {
		pipeUtil PU = new pipeUtil();
		PU.initPipe();
		System.out.println("<TrainTest>");
		Stack<Integer> bestScores = new Stack<>();
		bestScores.push(0);
		
		PriorityBlockingQueue<bird> fatigue = new PriorityBlockingQueue<>();
		PriorityBlockingQueue<bird> newborn = new PriorityBlockingQueue<>();
		List<bird> bl = new ArrayList<>();
		int step = 0;
		while(step<generation.max_iter_step) {
//			System.out.println("generation:"+step);
			if(step==0) {
				for(int i=0;i<1000;i++) {
					bird bird = new bird("g"+step);
					generation.singleTask(bird, new stage(generation.stage_length, generation.stage_height, PU), fatigue);
				}
			}
			else {
				if(!newborn.isEmpty()) {
					System.out.println("g"+step+": initial newborn is not empty Exception");
					break;
				}
				generation.offSpringBreed(newborn,fatigue,bl,"g"+step,bestScores);
				if(!fatigue.isEmpty()) {
					System.out.println("g"+step+": fatigue is not killed after breeding Exception");
					break;
				}
				if(newborn.isEmpty()) {
					System.out.println("g"+step+": newborn is still empty after breeding Exception");
					break;
				}
				while(!newborn.isEmpty()) {
					generation.singleTask(newborn.poll(), new stage(generation.stage_length, generation.stage_height, PU), fatigue);
				}
			}
//			System.out.println("bestScore:"+bestScores.peek());
			step++;
		}
		System.out.println("final best bird : "+fatigue.poll().getB_name());
		System.out.println("final score : "+fatigue.poll().getScore());
		bird final_bird = fatigue.poll();
		final_bird.getStage().draw();
		generation.doLog(final_bird,"utf-8","final.txt");
	}
}