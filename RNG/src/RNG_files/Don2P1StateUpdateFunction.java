package RNG_files;

import java.util.concurrent.ThreadLocalRandom;

import Fight_States.Don2P1FightState;
import Fight_States.FightState;
import Fight_States.FrameRule;
import Fight_Strategies.Don2P1FightStrategy;
import Memory_Value.FramesIncrement;
import Random_Events.Don2Delay;
import Random_Events.Don2RandomStar;

public class Don2P1StateUpdateFunction implements StateUpdateFunction {
	Don2P1FightStrategy strategy;

	final FramesIncrement GUT_TO_GUT = new FramesIncrement(43);
	final FramesIncrement GUT_TO_FACE = new FramesIncrement(42);
	final FramesIncrement FACE_TO_DELAY = new FramesIncrement(31);
	final FramesIncrement DELAY_TO_DELAY = new FramesIncrement(1);
	final FramesIncrement DELAY_TO_GUT_STAR = new FramesIncrement(134);
	final FramesIncrement DELAY_TO_GUT_NO_STAR = new FramesIncrement(79);
	final FramesIncrement GUT_STAR_DELAY = new FramesIncrement(6);
	final FramesIncrement FACE_STAR_DELAY = new FramesIncrement(1);

	static RngEventConditions randomStarConditions = new RngEventConditions("lucky star", new Don2RandomStar(), true);
	static RngEventConditions randomDelayConditions = new RngEventConditions("random delay", new Don2Delay(), false);

	public Don2P1StateUpdateFunction(Don2P1FightStrategy s) {
		strategy = s;
	}

	@Override
	public void update(FightState state) {
		if (!(state instanceof Don2P1FightState)) {
			throw new RuntimeException("Running Don 2 strategy on non-Don 2 state");
		}
		Don2P1FightState s = (Don2P1FightState) state;
		ManipControls controls = strategy.getManipControls(s);
		s.setPrevPunchRandomStar(false);
		boolean starChance = false;
		switch (s.getCurrentPatternId()) {
		case preFight:
			// assume right gut
			FrameRule frameRule = strategy.getFrameRule();
			s.addFrames(frameRule.framesToFirstPunch());
			s.dealDamage(5);
			s.getStar();
			s.setCurrentPatternId(Don2P1PatternId.weirdGut);
			break;
		case weirdGut:
			// assume right gut
			s.addFrames(new FramesIncrement(60));
			s.incPunchesTowardsStar();
			s.dealDamage(5);
			starChance = true;
			s.setCurrentPatternId(Don2P1PatternId.gut3);
			break;
		case gut1:
			// assume right gut
			s.addFrames(GUT_TO_GUT);
			s.incPunchesTowardsStar();
			s.dealDamage(5);
			starChance = true;
			s.setCurrentPatternId(Don2P1PatternId.gut2);
			break;
		case gut2:
			// assume right gut
			s.addFrames(GUT_TO_GUT);
			s.incPunchesTowardsStar();
			s.dealDamage(5);
			starChance = true;
			s.setCurrentPatternId(Don2P1PatternId.gut3);
			break;
		case gut3:
			// assume face punch
			s.addFrames(GUT_TO_FACE);
			s.incPunchesTowardsStar();
			s.dealDamage(5);
			if (s.getHealth() > 0) {
				starChance = true;
			}
			s.setCurrentPatternId(Don2P1PatternId.face);
			break;
		case face:
			assert controls.framesDelayed.equals(new FramesIncrement(0));
			if (s.getHealth() == 0) {
				// fight done
				s.addFrames(new FramesIncrement(0)); //TODO: how many frames to end?
				s.setCurrentPatternId(Don2P1PatternId.done);
			} else if (s.getHealth() <= 22) {
				// star to finish
				s.addFrames(new FramesIncrement(50)); //TODO: how many frames to end?
				s.throwStar();
				s.incPunchesTowardsStar();
				s.dealDamage(22);
				s.setCurrentPatternId(Don2P1PatternId.done);
			} else {
				// keep going
				s.incRoundOfPattern();
				s.addFrames(FACE_TO_DELAY);
				s.setCurrentPatternId(Don2P1PatternId.delay);
			}
			break;
		case delay:
			assert controls.framesDelayed.equals(new FramesIncrement(0));
			s.addFrames(DELAY_TO_DELAY);
			break;
		case delayDone:
			boolean star = strategy.throwStar(s);
			if (star) {
				s.addFrames(DELAY_TO_GUT_STAR);
				s.throwStar();
				s.incPunchesTowardsStar();
				s.dealDamage(22);
			} else {
				s.addFrames(DELAY_TO_GUT_NO_STAR);
			}
			// assume right gut
			s.incPunchesTowardsStar();
			s.dealDamage(5);
			starChance = true;
			s.setCurrentPatternId(Don2P1PatternId.gut1);
			break;
		default:
			throw new RuntimeException("Not a recognized state to continue from");
		}
		s.add(controls);

		// __001E and _0019 set, check random events now
		if (starChance && (getStar(s))) {
			s.getStar();
			if(controls.manip)
				s.setSuccessfulManip(true);
			switch (s.getCurrentPatternId()) {
			case gut1:
			case gut2:
			case gut3:
				s.addFrames(GUT_STAR_DELAY);
				break;
			case face:
				s.addFrames(FACE_STAR_DELAY);
				break;
			default:
				throw new RuntimeException("Can only have a star chance on gut or face punch");
			}
		}

		if (s.getCurrentPatternId() == Don2P1PatternId.delay) {
			boolean crowdMoved = ThreadLocalRandom.current().nextDouble() < (8 / 19.25);
			if (!randomDelayConditions.meetsConditions(s.get_0019(), s.get_001E(), crowdMoved)) {
				s.setCurrentPatternId(Don2P1PatternId.delayDone);
			}
		}
	}

	private boolean getStar(Don2P1FightState s) {
		// check for guaranteed star
		if (s.getPunchesTowardsStar() >= 7) {
			s.resetPunches();
			return true;
		}
		if (s.numStars() > 0) {
			// check for random star
			boolean crowdMoved = ThreadLocalRandom.current().nextDouble() < (8 / 19.25);
			if (randomStarConditions.meetsConditions(s.get_0019(), s.get_001E(), crowdMoved)) {
				s.setPrevPunchRandomStar(true);
				s.incRandomStars();
				return true;
			}
		}
		return false;
	}
}
