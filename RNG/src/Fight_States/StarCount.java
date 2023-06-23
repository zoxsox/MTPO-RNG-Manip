package Fight_States;

public class StarCount {
	
	int stars;
	
	public StarCount(int numStars) {
		stars = numStars;
	}
	
	void throwStar() {
		if(stars == 0) {
			throw new IllegalStateException();
		}
		stars--;
	}
	
	void addStar() {
		stars = Math.min(stars+1, 3);
	}
	
	int getNumStars() {
		return stars;
	}
	

}
