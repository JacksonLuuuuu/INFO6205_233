package Model;

import java.util.Random;

public class bird implements Comparable<bird>{
	private boolean fatigued = false;
	private String wholeLife = "";
	public boolean isFatigued() {
		return fatigued;
	}
	public void setFatigued(boolean fatigued) {
		this.fatigued = fatigued;
	}
	private stage stage;
	private String b_name;
	public String getB_name() {
		return b_name;
	}
	public void setB_name(String b_name) {
		this.b_name = b_name;
	}
	birdCase cur_state = birdCase.Start;
	gene[] genes;
	private int score = 0;
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public bird(String id) {
//		System.out.println("bird()");
		this.b_name = "primitive_"+id;
		this.genes = new gene[generation.stage_length];
		for(int i=0;i<this.genes.length;i++) {
			genes[i] = gene.getRandGene();
		}
	}
	public bird(bird father, bird mother, boolean f_first, String id, double mutat_ratio, boolean mutate_mode) {
		this.b_name = "hybrid_"+id;
		this.genes = crossover(father, mother, f_first);
		this.genes = mutate_individual(this.genes, mutat_ratio, mutate_mode);
	}
	
	
	
	@Override
	public int compareTo(bird bird) {
		return -(this.getScore()-(bird).getScore());
	}
	
	public int fitness() {
//		System.out.println(this.b_name+"init:"+this.score);
		// Basic Point
		this.score = getStage().getHerEvilLifStopHere()*20;
//		System.out.println(this.b_name+"basic:"+this.score);
		
		while(!getStage().getEventTriggered().isEmpty()) {
			birdCase bc = getStage().getEventTriggered().remove();
			this.setWholeLife(this.getWholeLife() + " -> "+bc);
			switch(bc) {
			    case Eating: this.score += 100;break;// Eat fruit will get points
			    case TouchEnd: this.score = this.score*2;break;// Touch end will be rewarded
			    case TouchBoundary: this.score = this.score<10?0:this.score-10;break;// Touch boundary is not recommended
			    case TouchPipe: this.score = this.score==0?0:this.score/5;break;// Touch pipe is not allowed
			    case Empty_handed: this.score = this.score<5?0:this.score-5;break;//Bird should better not do futile work because it may lose energy
			    default: break;
			}
		}
//		System.out.println(this.b_name+"improved:"+this.score);
		// Pass the column will add extra points
		while(!getStage().getDis2edge().isEmpty()) {
			this.score += getStage().getDis2edge().remove()*15;
		}
//		System.out.println(this.b_name+"final:"+this.score);
		return score;
	}
	
	public gene[] crossover(bird father, bird mother, boolean father_first) {
		Random r = new Random();
		gene[] x_genes = new gene[generation.stage_length];
		double genetic_prob = (double)father.score/(father.score+mother.score);
		if(father_first) {
			for(int i=0;i<generation.stage_length;i++) {
				if(i<father.getStage().getHerEvilLifStopHere())
					x_genes[i] = (r.nextDouble()<genetic_prob?father.genes[i]:mother.genes[i]);
				else x_genes[i] = gene.getRandGene();
			}
		}
		else {
			for(int i=0;i<generation.stage_length;i++) {
				if(i<mother.getStage().getHerEvilLifStopHere()) 
					x_genes[i] = (r.nextDouble()<genetic_prob?father.genes[i]:mother.genes[i]);
				else x_genes[i] = gene.getRandGene();
			}
		}
		return x_genes;
	}
	
	public gene[] mutate_individual(gene[] genes, double mutat_ratio, boolean mode) {
		Random r = new Random();
		if(mode) {
			int count=0;
			while(count++<generation.stage_length/5) {
				if(r.nextDouble()<=mutat_ratio) {
					int gene_bit = r.nextInt(generation.stage_length);
					genes[gene_bit] = gene.getRandGene();
				}
			}
		}
		else {
			int count = 0;
			while(count++<generation.stage_length/20) {
				if(r.nextDouble()<=mutat_ratio) {
					int gene_bit = r.nextInt(generation.stage_length);
					genes[gene_bit] = gene.getRandGene();
				}
			}
		}
		return genes;
	}
	
	public void load(stage stage) {
		setStage((stage)stage.clone());
	}
	
	public void startHerEvilLife(stage stage) {
		if(!this.isFatigued()) {
			if(this.cur_state == birdCase.Start || this.stage == null) load(stage);
			for(int bit=0;bit<this.genes.length;bit++) {
				if(cur_state!=birdCase.TouchPipe && cur_state!=birdCase.TouchEnd) {
					cur_state = getStage().moveBird(this.genes[bit]);
				}
				else {
					break;
				}
			}
			fitness();
			setFatigued(true);
		}
	}
	public gene[] getGenes() {
		return genes;
	}
	public void setGenes(gene[] genes) {
		this.genes = genes;
	}
	public stage getStage() {
		return stage;
	}
	public void setStage(stage stage) {
		this.stage = stage;
	}
	public String getWholeLife() {
		return wholeLife;
	}
	public void setWholeLife(String wholeLife) {
		this.wholeLife = wholeLife;
	}
}