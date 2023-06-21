package RNG_files;

public class SuccessChance {
	double bothNoCrowd;
	double crowdNoCrowd;
	double noCrowdCrowd;
	double bothCrowd;
	double overallChance;
	
	public SuccessChance(double bothNoCrowd, double crowdNoCrowd, double noCrowdCrowd, double bothCrowd) {
		this.bothNoCrowd = bothNoCrowd;
		this.crowdNoCrowd = crowdNoCrowd;
		this.noCrowdCrowd = noCrowdCrowd;
		this.bothCrowd = bothCrowd;
		this.overallChance = 0.3481*bothNoCrowd + 0.2419*crowdNoCrowd + 0.2419*noCrowdCrowd + 0.1681*bothCrowd;
	}
	
	@Override
	public String toString() {
		return String.format("Overall chance: %.4f", overallChance);
		//return "Both no crowd: " + bothNoCrowd + ", Crowd then no crowd: " + crowdNoCrowd + ", No crowd then crowd: " + noCrowdCrowd + ", Both crowd: " + bothCrowd;
	}
	
}
