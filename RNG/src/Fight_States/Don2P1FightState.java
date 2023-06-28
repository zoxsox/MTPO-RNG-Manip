package Fight_States;

import Memory_Value.FramesIncrement;
import RNG_files.Don2P1PatternId;
import RNG_files.ManipControls;

public class Don2P1FightState extends FightState {
	
	StarCount stars;
	private boolean prevPunchStar;
	int framesDelayedByStars;
	private Don2P1PatternId currentPatternId;
	int health;
	int punches;
	
	public Don2P1FightState() {
		stars = new StarCount(0);
		framesDelayedByStars = 0;
		setPrevPunchStar(false);
		setCurrentPatternId(Don2P1PatternId.preFight);
		health = 96;
		punches = 0;
	}

	public Don2P1PatternId getCurrentPatternId() {
		return currentPatternId;
	}

	public void setCurrentPatternId(Don2P1PatternId currentPatternId) {
		this.currentPatternId = currentPatternId;
	}

	public boolean isPrevPunchStar() {
		return prevPunchStar;
	}

	public void setPrevPunchStar(boolean prevPunchStar) {
		this.prevPunchStar = prevPunchStar;
	}

	public void incPunches() {
		punches = getPunches() + 1;
	}

	public void dealDamage(int dmg) {
		health = Math.max(0, getHealth() - dmg);
	}

	public void add(ManipControls controls) {
		_0019 = get_0019().add(controls.getInputsAdded());
		_001E = get_001E().add(controls.getFramesDelayed());
	}
	
	public void getStar() {
		stars.addStar();
	}
	
	public void throwStar() {
		stars.throwStar();
	}

	public void addFrames(FramesIncrement frames) {
		_001E = get_001E().add(frames);
	}

	public int getPunches() {
		return punches;
	}

	public int getHealth() {
		return health;
	}

	@Override
	public boolean isDone() {
		return currentPatternId == Don2P1PatternId.done;
	}
	
	@Override
	public String toString() {
		return String.format("Stars: %s, currentPatternId: %s, health: %d, punches: %d", stars, currentPatternId, health, punches);
	}

	public int numStars() {
		return stars.getNumStars();
	}

	public void resetPunches() {
		punches = 0;
	}
}
