package Models;

public class Notification {

	private  int maxP;
	private int minP;
	private  int grt;
	private  int lesst;
	private  int step;
	
	public Notification(){}
	
	

	public Notification(int maxP, int minP, int grt, int lesst, int step) {
		super();
		this.maxP = maxP;
		this.minP = minP;
		this.grt = grt;
		this.lesst = lesst;
		this.step = step;
	}



	public int getMaxP() {
		return maxP;
	}

	public void setMaxP(int maxP) {
		this.maxP = maxP;
	}

	public int getMinP() {
		return minP;
	}

	public void setMinP(int minP) {
		this.minP = minP;
	}

	public int getGrt() {
		return grt;
	}

	public void setGrt(int grt) {
		this.grt = grt;
	}

	public int getLesst() {
		return lesst;
	}

	public void setLesst(int lesst) {
		this.lesst = lesst;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}
	
	
}
