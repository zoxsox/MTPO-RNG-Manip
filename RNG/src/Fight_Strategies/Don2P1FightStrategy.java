package Fight_Strategies;

import java.net.NoRouteToHostException;
import java.util.concurrent.ThreadLocalRandom;

import Fight_States.Don2P1FightState;
import Fight_States.FightState;
import Fight_States.FrameRule;
import Memory_Value.FramesIncrement;
import Memory_Value.InputsIncrement;
import RNG_files.CreateDon2P1ManipControlsFunction;
import RNG_files.ManipControls;

public class Don2P1FightStrategy implements FightStrategy {
	public static CreateDon2P1ManipControlsFunction normalGutPunch = () -> {
		return new ManipControls(gutPunchIncrement(), standardPunchDelay());
	};
	public static CreateDon2P1ManipControlsFunction normalFacePunch = () -> {
		return new ManipControls(facePunchIncrement(), standardPunchDelay());
	};
	public static CreateDon2P1ManipControlsFunction holdUpDuringDelay = () -> {
		return new ManipControls(new InputsIncrement(32), new FramesIncrement(0));
	};
	public static CreateDon2P1ManipControlsFunction holdAManip = () -> {
		return new ManipControls(holdAIncrement(), standardPunchDelay(), true);
	};
	public static CreateDon2P1ManipControlsFunction madeUpManip = () -> {
		return new ManipControls(new InputsIncrement(120), standardPunchDelay(), true);
	};
	public static CreateDon2P1ManipControlsFunction holdUpandBManip = () -> {
		return new ManipControls(holdUpAndBIncrement(), standardPunchDelay(), true);
	};
	public static CreateDon2P1ManipControlsFunction misdirectManip = () -> {
		FramesIncrement increment = standardPunchDelay();
		return new ManipControls(misdirectIncrement(increment.getValue()), increment, true);
	};

	int frameRuleId;
	CreateDon2P1ManipControlsFunction face1;
	CreateDon2P1ManipControlsFunction gut32a;
	CreateDon2P1ManipControlsFunction gut33a;
	CreateDon2P1ManipControlsFunction gut22b;
	CreateDon2P1ManipControlsFunction gut23b;
	CreateDon2P1ManipControlsFunction face2b;
	CreateDon2P1ManipControlsFunction gut33b;
	CreateDon2P1ManipControlsFunction face3b;

	public Don2P1FightStrategy(int frameRuleId, CreateDon2P1ManipControlsFunction f1,
			CreateDon2P1ManipControlsFunction g32a, CreateDon2P1ManipControlsFunction g33a,
			CreateDon2P1ManipControlsFunction g22b, CreateDon2P1ManipControlsFunction g23b,
			CreateDon2P1ManipControlsFunction f2b, CreateDon2P1ManipControlsFunction g33b,
			CreateDon2P1ManipControlsFunction f3b) {
		this.frameRuleId = frameRuleId;
		face1 = f1;
		gut32a = g32a;
		gut33a = g33a;
		gut22b = g22b;
		gut23b = g23b;
		face2b = f2b;
		gut33b = g33b;
		face3b = f3b;
	}

	public ManipControls getManipControls(FightState state) {
		if (!(state instanceof Don2P1FightState)) {
			throw new RuntimeException("Running Don 2 strategy on non-Don 2 state");
		}
		Don2P1FightState s = (Don2P1FightState) state;
		switch (s.getCurrentPatternId()) {
		case preFight:
			return new ManipControls(InputsIncrement.randomIncrement(), firstPunchDelay());
		case weirdGut:
			return normalGutPunch.getManipControls();
		case gut1:
			if (s.isPrevPunchRandomStar()) {
				if (s.getRoute() == Don2P1Route.B && s.getRoundOfPattern() == 2 && s.getRandomStars() == 1) {
					s.setAttemptedManip("gut22b");
					return gut22b.getManipControls();
				}
				if (s.getRoute() == Don2P1Route.A && s.getRoundOfPattern() == 3 && s.getRandomStars() == 2) {
					s.setAttemptedManip("gut32a");
					return gut32a.getManipControls();
				}
			}
			return normalGutPunch.getManipControls();
		case gut2:
			if (s.isPrevPunchRandomStar()) {
				if (s.getRoute() == Don2P1Route.B && s.getRoundOfPattern() == 2 && s.getRandomStars() == 1) {
					s.setAttemptedManip("gut23b");
					return gut23b.getManipControls();
				}
				if (s.getRoute() == Don2P1Route.B && s.getRoundOfPattern() == 3 && s.getRandomStars() == 1) {
					s.setAttemptedManip("gut33b");
					return gut33b.getManipControls();
				}
				if (s.getRoute() == Don2P1Route.A && s.getRoundOfPattern() == 3 && s.getRandomStars() == 2) {
					s.setAttemptedManip("gut33a");
					return gut33a.getManipControls();
				}
					
			}
			return normalGutPunch.getManipControls();
		case gut3:
			if (s.isPrevPunchRandomStar()) {
				if (s.getRoundOfPattern() == 1 && s.getRandomStars() == 1) {
					s.setAttemptedManip("face1");
					return face1.getManipControls();
				}	
				if (s.getRoute() == Don2P1Route.B && s.getRoundOfPattern() == 2 && s.getRandomStars() == 1) {
					s.setAttemptedManip("face2b");
					return face2b.getManipControls();
				}
				if (s.getRoute() == Don2P1Route.B && s.getRoundOfPattern() == 3 && s.getRandomStars() == 1) {
					s.setAttemptedManip("face3b");
					return face3b.getManipControls();
				}
			}
			return normalFacePunch.getManipControls();
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

	public boolean throwStar(Don2P1FightState state) {
		boolean throwStar = state.getHealth() < 81 || state.numStars() > 1;
		if(throwStar)
			state.setRoute(Don2P1Route.A);
		else {
			state.setRoute(Don2P1Route.B);
		}
		return throwStar;
	}

	private static FramesIncrement standardPunchDelay() {
		double r = ThreadLocalRandom.current().nextDouble();
		if (r < .3)
			return new FramesIncrement(0);
		if (r < .8)
			return new FramesIncrement(1);
		return new FramesIncrement(2);
	}

	private static FramesIncrement firstPunchDelay() {
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
