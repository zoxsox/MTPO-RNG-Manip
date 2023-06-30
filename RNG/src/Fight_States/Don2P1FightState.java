package Fight_States;

import Fight_Strategies.Don2P1Route;
import Memory_Value.FramesIncrement;
import Memory_Value.InputsIncrement;
import RNG_files.Don2P1PatternId;
import RNG_files.ManipControls;

public class Don2P1FightState extends FightState {
	
	StarCount stars;
	private boolean prevPunchRandomStar;
	int framesDelayedByStars;
	private Don2P1PatternId currentPatternId;
	int health;
	int punchesTowardsStar;
	int randomStars;
	int punchesThrown;
	private Don2P1Route route;
	private int roundOfPattern;
	private String attemptedManip;
	private boolean successfulManip;
	
	public Don2P1FightState() {
		stars = new StarCount(0);
		framesDelayedByStars = 0;
		setPrevPunchRandomStar(false);
		setCurrentPatternId(Don2P1PatternId.preFight);
		health = 96;
		punchesTowardsStar = 0;
		randomStars = 0;
		punchesThrown = 0;
		setRoute(null);
		roundOfPattern = 1;
		setAttemptedManip("none");
		setSuccessfulManip(false);
	}

	public Don2P1PatternId getCurrentPatternId() {
		return currentPatternId;
	}

	public void setCurrentPatternId(Don2P1PatternId currentPatternId) {
		this.currentPatternId = currentPatternId;
	}

	public boolean isPrevPunchRandomStar() {
		return prevPunchRandomStar;
	}

	public void setPrevPunchRandomStar(boolean prevPunchRandomStar) {
		this.prevPunchRandomStar = prevPunchRandomStar;
	}

	public void incPunchesTowardsStar() {
		punchesTowardsStar = getPunchesTowardsStar() + 1;
	}

	public void dealDamage(int dmg) {
		health = Math.max(0, getHealth() - dmg);
		punchesThrown++;
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

	public int getPunchesTowardsStar() {
		return punchesTowardsStar;
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
		return String.format("Stars: %s, currentPatternId: %s, health: %d, punchesTowardsStar: %d, _0019: %d, _001E: %d", stars, currentPatternId, health, punchesTowardsStar, _0019.getValue(), _001E.getValue());
	}

	public int numStars() {
		return stars.getNumStars();
	}

	public void resetPunches() {
		punchesTowardsStar = 0;
	}

	public void addInputs(InputsIncrement inputs) {
		_0019 = get_0019().add(inputs);
	}

	public Don2P1Route getRoute() {
		return route;
	}

	public void setRoute(Don2P1Route route) {
		this.route = route;
	}

	public int getRoundOfPattern() {
		return roundOfPattern;
	}

	public int getRandomStars() {
		return randomStars;
	}
	
	public void incRandomStars() {
		randomStars++;
	}

	public void incRoundOfPattern() {
		roundOfPattern++;
		
	}

	public String getAttemptedManip() {
		return attemptedManip;
	}

	public void setAttemptedManip(String attemptedManip) {
		this.attemptedManip = attemptedManip;
	}

	public boolean isSuccessfulManip() {
		return successfulManip;
	}

	public void setSuccessfulManip(boolean successfulManip) {
		this.successfulManip = successfulManip;
	}
}
