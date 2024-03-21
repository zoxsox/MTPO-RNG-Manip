package Fight_Strategies;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import Fight_States.Don2P1FightState;
import Fight_States.FightState;
import Fight_States.FrameRule;
import Memory_Value.FramesIncrement;
import Memory_Value.InputsIncrement;
import RNG_files.CreateDon2P1ManipControlsFunction;
import RNG_files.Don2P1PatternId;
import RNG_files.ManipControls;

public class Don2P1FightStrategy implements FightStrategy {
	public CreateDon2P1ManipControlsFunction normalGutPunch = () -> {
		return new ManipControls(gutPunchIncrement(), standardPunchDelay());
	};
	public CreateDon2P1ManipControlsFunction normalFacePunch = () -> {
		return new ManipControls(facePunchIncrement(), standardPunchDelay());
	};
	public static CreateDon2P1ManipControlsFunction holdUpDuringDelay = () -> {
		return new ManipControls(new InputsIncrement(32), new FramesIncrement(0));
	};
	
	public static List<String> manipNames = Arrays.asList(
			"face1", "gut32a", "gut33a", "gut22b", "gut23b", "face2b", "gut33b", "face3b"
	);

	int frameRuleId;
	Map<String, CreateDon2P1ManipControlsFunction> manips;
	boolean delaysSupplied;
	Iterator<Integer> delaysItr;
	List<Integer> delaysList;
	int aPress;
	int bPress;
	int upPress;
	int startPress;

	public Don2P1FightStrategy(int frameRuleId, Map<String, CreateDon2P1ManipControlsFunction> m) {
		this.frameRuleId = frameRuleId;
		if(!manipNames.containsAll(m.keySet())) {
			throw new RuntimeException("Passed in manip names that are not supported");
		}
		manips = m;
		delaysSupplied = false;
	}
	
	public Don2P1FightStrategy(int frameRuleId, Map<String, CreateDon2P1ManipControlsFunction> m, List<Integer> delays, int aPress, int bPress, int upPress, int startPress) {
		this.frameRuleId = frameRuleId;
		if(!manipNames.containsAll(m.keySet())) {
			throw new RuntimeException("Passed in manip names that are not supported");
		}
		manips = m;
		delaysSupplied = true;
		delaysList = delays;
		this.aPress = aPress;
		this.bPress = bPress;
		this.upPress = upPress;
		this.startPress = startPress;
	}

	public ManipControls getManipControls(FightState state) {
		if (!(state instanceof Don2P1FightState)) {
			throw new RuntimeException("Running Don 2 strategy on non-Don 2 state");
		}
		Don2P1FightState s = (Don2P1FightState) state;
		if(s.getCurrentPatternId() == Don2P1PatternId.preFight) {
			if (delaysSupplied) {
				delaysItr = delaysList.iterator();
			}
		}
		switch (s.getCurrentPatternId()) {
		case preFight:
			return new ManipControls(InputsIncrement.randomIncrement(), firstPunchDelay());
		case weirdGut:
			return normalGutPunch.getManipControls();
		case gut1:
			if (s.isPrevPunchRandomStar()) {
				if (manips.containsKey("gut22b") && s.getRoute() == Don2P1Route.B && s.getRoundOfPattern() == 2 && s.getRandomStars() == 1) {
					s.setAttemptedManip("gut22b");
					return manips.get("gut22b").getManipControls();
				}
				if (manips.containsKey("gut32a") && s.getRoute() == Don2P1Route.A && s.getRoundOfPattern() == 3 && s.getRandomStars() == 2) {
					s.setAttemptedManip("gut32a");
					return manips.get("gut32a").getManipControls();
				}
			}
			return normalGutPunch.getManipControls();
		case gut2:
			if (s.isPrevPunchRandomStar()) {
				if (manips.containsKey("gut23b") && s.getRoute() == Don2P1Route.B && s.getRoundOfPattern() == 2 && s.getRandomStars() == 1) {
					s.setAttemptedManip("gut23b");
					return manips.get("gut23b").getManipControls();
				}
				if (manips.containsKey("gut33b") && s.getRoute() == Don2P1Route.B && s.getRoundOfPattern() == 3 && s.getRandomStars() == 1) {
					s.setAttemptedManip("gut33b");
					return manips.get("gut33b").getManipControls();
				}
				if (manips.containsKey("gut33a") && s.getRoute() == Don2P1Route.A && s.getRoundOfPattern() == 3 && s.getRandomStars() == 2) {
					s.setAttemptedManip("gut33a");
					return manips.get("gut33a").getManipControls();
				}
				
					
			}
			return normalGutPunch.getManipControls();
		case gut3:
			if (s.isPrevPunchRandomStar()) {
				if (manips.containsKey("face1") && s.getRoundOfPattern() == 1 && s.getRandomStars() == 1) {
					s.setAttemptedManip("face1");
					return manips.get("face1").getManipControls();
				}	
				if (manips.containsKey("face2b") && s.getRoute() == Don2P1Route.B && s.getRoundOfPattern() == 2 && s.getRandomStars() == 1) {
					s.setAttemptedManip("face2b");
					return manips.get("face2b").getManipControls();
				}
				if (manips.containsKey("face3b") && s.getRoute() == Don2P1Route.B && s.getRoundOfPattern() == 3 && s.getRandomStars() == 1) {
					s.setAttemptedManip("face3b");
					return manips.get("face3b").getManipControls();
				}
			}
			return normalFacePunch.getManipControls();
		case face:
			return new ManipControls(faceInputsIncrement(), new FramesIncrement(0));
		case delay:
			return holdUpDuringDelay.getManipControls();
		case delayDone:
			return new ManipControls(afterDelayInputsIncrement(), standardPunchDelay());
		default:
			throw new RuntimeException("Not a recognized state to continue from");
		}
	}

	public FrameRule getFrameRule() {
		return new FrameRule(frameRuleId);
	}

	public boolean throwStar(Don2P1FightState state) {
		boolean throwStar = state.getHealth() < 81 || state.numStars() > 1;
		if(state.getHealth() >= 66) {
			if(throwStar)
				state.setRoute(Don2P1Route.A);
			else {
				state.setRoute(Don2P1Route.B);
			}
		}
		return throwStar;
	}
	
	private InputsIncrement faceInputsIncrement() {
		return delaysSupplied ? new InputsIncrement(0) : InputsIncrement.randomIncrement();
	}
	
	private InputsIncrement afterDelayInputsIncrement() {
		return delaysSupplied ? new InputsIncrement(startPress*4) : InputsIncrement.randomIncrement();
	}

	private FramesIncrement standardPunchDelay() {
		if(delaysSupplied) {
			return new FramesIncrement(delaysItr.next());
		}
		double r = ThreadLocalRandom.current().nextDouble();
		if (r < .3)
			return new FramesIncrement(3);
		if (r < .8)
			return new FramesIncrement(1);
		return new FramesIncrement(2);
	}
	
	private FramesIncrement leftGutDelay() {
		return new FramesIncrement(standardPunchDelay().getValue()-1);
	}

	private FramesIncrement firstPunchDelay() {
		if(delaysSupplied) {
			return new FramesIncrement(delaysItr.next());
		}
		double r = ThreadLocalRandom.current().nextDouble();
		if (r < .15)
			return new FramesIncrement(1);
		if (r < .32)
			return new FramesIncrement(1);
		if (r < .55)
			return new FramesIncrement(2);
		if (r < .75)
			return new FramesIncrement(3);
		if (r < .9)
			return new FramesIncrement(4);
		return new FramesIncrement(5);
	}

	private InputsIncrement gutPunchIncrement() {
		int framesPressA = delaysSupplied ? aPress : ThreadLocalRandom.current().nextInt(8,16);
		return new InputsIncrement((framesPressA * 16) + ThreadLocalRandom.current().nextInt(4,8)*128);
	}

	private InputsIncrement facePunchIncrement() {
		int framesPressB = delaysSupplied ? bPress :  ThreadLocalRandom.current().nextInt(6, 10);
		int framesPressUp = delaysSupplied ? upPress :  ThreadLocalRandom.current().nextInt(10, 15);
		return new InputsIncrement((framesPressB * 8 + framesPressUp * 32) % 0x100);
	}
}
