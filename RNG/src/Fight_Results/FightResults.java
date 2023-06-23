package Fight_Results;

public abstract class FightResults {
	int framesTaken;
	
	public FightResults(int frames) {
		framesTaken = frames;
	}

	public int getFramesTaken() {
		return framesTaken;
	}
}
