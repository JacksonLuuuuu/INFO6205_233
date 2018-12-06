package Model;

public class grid {
	public static final int grid = 0;
	public static final int bird = 1;
	public static final int ceilPipe = 2;
	public static final int  groundPipe= 3;
	public static final int ceil = 4;
	public static final int ground = 5;
	public static final int end = 6;
	public static final int fruit = 7;
	public static final int birdeating = 8;
	private int gridStat = 0;
	private boolean touchable;
	private boolean isBoundary;
	private boolean isBird;
	private boolean isEnd;
	private boolean isFruit;
	private boolean isEating;
	
	public boolean isEating() {
		return isEating;
	}

	public void setEating(boolean isEating) {
		this.isEating = isEating;
	}

	public grid(int gridStat) {
		setGridStat(gridStat);
		setBird(this.gridStat == 1);
		setTouchable(this.gridStat == 0);
		setBoundary(this.gridStat == 4 || this.gridStat == 5);
		setEnd(this.gridStat == 6);
		setFruit(this.gridStat == 7);
	}
	
	public boolean isBird() {
		return isBird;
	}
	public void setBird(boolean isBird) {
		this.isBird = isBird;
	}
	public int getGridStat() {
		return gridStat;
	}
	public void setGridStat(int gridStat) {
		this.gridStat = gridStat;
		refresh();
	}
	public boolean isTouchable() {
		return touchable;
	}
	public void setTouchable(boolean touchable) {
		this.touchable = touchable;
	}
	public boolean isBoundary() {
		return isBoundary;
	}
	public void setBoundary(boolean isBoundary) {
		this.isBoundary = isBoundary;
	}	
	public boolean isEnd() {
		return isEnd;
	}
	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}
	private void refresh() {
		setBird(this.gridStat == 1);
		setTouchable(this.gridStat == 0 || this.gridStat == 7);
		setBoundary(this.gridStat == 4 || this.gridStat == 5 || this.gridStat == 6);
		setEnd(this.gridStat == 6);
		setFruit(this.gridStat == 7);
	}

	public boolean isFruit() {
		return isFruit;
	}

	public void setFruit(boolean isFruit) {
		this.isFruit = isFruit;
	}
}
