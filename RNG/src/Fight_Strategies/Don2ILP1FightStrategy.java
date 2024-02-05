package Fight_Strategies;

import java.util.concurrent.ThreadLocalRandom;

import Fight_States.Don2ILP1FightState;
import Fight_States.FightState;
import Fight_States.FrameRule;
import Memory_Value.FramesIncrement;
import Memory_Value.InputsIncrement;
import RNG_files.CreateDon2P1ManipControlsFunction;
import RNG_files.ManipControls;

public class Don2ILP1FightStrategy implements FightStrategy {
	static CreateDon2P1ManipControlsFunction normalGutPunch = () -> {
		return new ManipControls(gutPunchIncrement(), standardPunchDelay());
	};
	static CreateDon2P1ManipControlsFunction normalFacePunch = () -> {
		return new ManipControls(facePunchIncrement(), standardPunchDelay());
	};
	static CreateDon2P1ManipControlsFunction holdUpDuringDelay = () -> {
		return new ManipControls(new InputsIncrement(32), new FramesIncrement(0));
	};
	static CreateDon2P1ManipControlsFunction holdAManip = () -> {
		return new ManipControls(holdAIncrement(), standardPunchDelay());
	};
	static CreateDon2P1ManipControlsFunction misdirectManip = () -> {
		FramesIncrement increment = standardPunchDelay();
		return new ManipControls(misdirectIncrement(increment.getValue()), increment);
	};
	CreateDon2P1ManipControlsFunction bufferFaceControlsFunction = () -> {
		return new ManipControls(new InputsIncrement(0), new FramesIncrement(252));
	};

	int frameRuleId;
	boolean testingIL;
	int delay1and2;
	int delay3;
	int inc1;
	int inc2;

	public Don2ILP1FightStrategy(int frameRuleId, int d1and2, int d3, int i1, int i2) {
		this.frameRuleId = frameRuleId;
		this.testingIL = true;
		delay1and2 = d1and2;
		delay3 = d3;
		inc1 = i1;
		inc2 = i2;
	}
	
	public int getInc1() {
		return inc1;
	}
	
	public int getInc2() {
		return inc2;
	}

	public ManipControls getManipControls(FightState state) {
		if (!(state instanceof Don2ILP1FightState)) {
			throw new RuntimeException("Running Don 2 strategy on non-Don 2 state");
		}
		Don2ILP1FightState s = (Don2ILP1FightState) state;
		switch (s.getCurrentPatternId()) {
		case preFight:
			if (testingIL) {
				return new ManipControls(InputsIncrement.randomIncrementNoRight(), new FramesIncrement(0));
			}
			return new ManipControls(InputsIncrement.randomIncrement(), standardPunchDelay());
		case weirdGut:
			if (testingIL) {
				return new ManipControls(gutPunchIncrement(), new FramesIncrement(delay1and2));
			}
			return normalGutPunch.getManipControls();
		case gut1, gut2:
			if (s.isPrevPunchStar()) {
				return holdAManip.getManipControls();
			} else {
				return normalGutPunch.getManipControls();
			}
		case gut3:
		if (testingIL) {
			return bufferFaceControlsFunction.getManipControls();
		}
		if (s.isPrevPunchStar()) {
			return misdirectManip.getManipControls();
		} else {
			return normalFacePunch.getManipControls();
		}
		case face:
			return new ManipControls(InputsIncrement.randomIncrement(), new FramesIncrement(0));
		case delay:
			return holdUpDuringDelay.getManipControls();
		case delayDone:
			return new ManipControls(InputsIncrement.randomIncrement(), standardPunchDelay());
		default:
			throw new RuntimeException("Not a recognized state to continue from");
		}
	}

	public FrameRule getFrameRule() {
		return new FrameRule(frameRuleId);
	}

	public boolean throwStar(Don2ILP1FightState state) {
		return state.getHealth() < 81 || state.numStars() > 1;
	}

	private static FramesIncrement standardPunchDelay() {
		int delay = ThreadLocalRandom.current().nextInt(0, 1);
		return new FramesIncrement(delay);
	}

	private static InputsIncrement gutPunchIncrement() {
		int framesPressA = ThreadLocalRandom.current().nextInt(0, 16);
		return new InputsIncrement(framesPressA * 16);
	}

	private static InputsIncrement facePunchIncrement() {
		int framesPressB = ThreadLocalRandom.current().nextInt(6, 10);
		int framesPressUp = ThreadLocalRandom.current().nextInt(10, 15);
		return new InputsIncrement((framesPressB * 8 + framesPressUp * 32) % 0x100);
	}

	private static InputsIncrement holdAIncrement() {
		return new InputsIncrement(192);
	}

	private static InputsIncrement holdUpAndBIncrement() {
		return new InputsIncrement(184);
	}

	private static InputsIncrement misdirectIncrement(int framesDelayed) {
		return new InputsIncrement(88 + framesDelayed * 32);
	}
}
