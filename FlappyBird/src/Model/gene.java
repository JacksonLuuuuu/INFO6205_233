package Model;

import java.util.Random;

public enum gene {  
    Hover, FlapUp, FlapDown,Hover_Eat, FlapUp_Eat, FlapDown_Eat; 
	public static gene getRandGene() {
		Random r = new Random();
		switch(r.nextInt(6)) {
			case 0: return gene.Hover;
			case 1: return gene.FlapUp;
			case 2: return gene.FlapDown;
			case 3: return gene.Hover_Eat;
			case 4: return gene.FlapUp_Eat;
			case 5: return gene.FlapDown_Eat;
			default: return gene.Hover;
		}
	}
}  
