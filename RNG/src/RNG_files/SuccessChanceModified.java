package RNG_files;

public class SuccessChanceModified {
	double bothNoCrowd;
	double crowdNoCrowd;
	double noCrowdCrowd;
	double bothCrowd;
	double overallChance;
	
	public SuccessChanceModified(double bothNoCrowd, double crowdNoCrowd, double noCrowdCrowd, double bothCrowd) {
		this.bothNoCrowd = bothNoCrowd;
		this.crowdNoCrowd = crowdNoCrowd;
		this.noCrowdCrowd = noCrowdCrowd;
		this.bothCrowd = bothCrowd;
		this.overallChance = 
				0.24127*bothNoCrowd + 
				0.34314*crowdNoCrowd + 
				0.17157*noCrowdCrowd + 
				0.24401*bothCrowd;
	}
	
	@Override
	public String toString() {
		return String.format("Overall chance: %.4f", overallChance);
		//return "Both no crowd: " + bothNoCrowd + ", Crowd then no crowd: " + crowdNoCrowd + ", No crowd then crowd: " + noCrowdCrowd + ", Both crowd: " + bothCrowd;
	}
	
}
