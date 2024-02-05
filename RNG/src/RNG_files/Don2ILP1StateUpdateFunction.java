package RNG_files;

import java.util.concurrent.ThreadLocalRandom;

import Fight_States.Don2ILP1FightState;
import Fight_States.FightState;
import Fight_States.FrameRule;
import Fight_Strategies.Don2ILP1FightStrategy;
import Memory_Value.FramesIncrement;
import Memory_Value.InputsIncrement;
import Random_Events.Don2Delay;
import Random_Events.Don2RandomStar;
import Random_Events.Don2TakeEarlyPunch;



public class Don2ILP1StateUpdateFunction implements StateUpdateFunction {
	Don2ILP1FightStrategy strategy;
	
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
	static RngEventConditions takeEarlyPunchConditions = new RngEventConditions("early punch works", new Don2TakeEarlyPunch(), false);
	
	public Don2ILP1StateUpdateFunction(Don2ILP1FightStrategy s) {
		strategy = s;
	}

	@Override
	public void update(FightState state) {
		if(!(state instanceof Don2ILP1FightState)) {
			throw new RuntimeException("Running Don 2 strategy on non-Don 2 state");
		}
		Don2ILP1FightState s = (Don2ILP1FightState)state;
		ManipControls controls = strategy.getManipControls(s);
		s.setPrevPunchStar(false);
		boolean starChance = false;
		switch (s.getCurrentPatternId()) {
		case preFight:
			//assume right gut
			FrameRule frameRule = strategy.getFrameRule();
			s.addFrames(frameRule.framesToFirstPunch());
			s.dealDamage(5);
			s.getStar();
			s.setCurrentPatternId(Don2P1PatternId.weirdGut);
			break;
		case weirdGut:
			//assume right gut
			s.addFrames(new FramesIncrement(60));
			s.incPunches();
			s.dealDamage(5);
			starChance = true;
			s.setCurrentPatternId(Don2P1PatternId.gut3);
			break;
		case gut1:
			//assume right gut
			s.addFrames(GUT_TO_GUT);
			s.incPunches();
			s.dealDamage(5);
			starChance = true;
			s.setCurrentPatternId(Don2P1PatternId.gut2);
			break;
		case gut2:
			//assume right gut
			s.addFrames(GUT_TO_GUT);
			s.incPunches();
			s.dealDamage(5);
			starChance = true;
			s.setCurrentPatternId(Don2P1PatternId.gut3);
			break;
		case gut3:
			//assume face punch
			if(controls.framesDelayed.getValue() > 250) {
				//early punch, check if goes through
				s.addFrames(new FramesIncrement(34));
				s.addInputs(new InputsIncrement(strategy.getInc1()));
				if(earlyPunchWorks(s)) {
					s.incPunches();
					s.dealDamage(5);
					s.addInputs(new InputsIncrement(strategy.getInc2()));
					s.addFrames(new FramesIncrement(4));
					if(getStar(s)) {
						s.getStar();
						s.addFrames(FACE_STAR_DELAY);
					}
					s.setCurrentPatternId(Don2P1PatternId.done);
                }else {
					s.setCurrentPatternId(Don2P1PatternId.done);
					s.setBlocked(true);
                }
                return;
            }else {
				s.addFrames(GUT_TO_FACE);
				s.incPunches();
				s.dealDamage(5);
				if(s.getHealth() > 5) {
					starChance = true;
				}
				s.setCurrentPatternId(Don2P1PatternId.face);
			}
			break;
		case face:
			assert controls.framesDelayed.equals(new FramesIncrement(0));
			if(s.getHealth() == 0) {
				//fight done
				s.addFrames(new FramesIncrement(0));
				s.setCurrentPatternId(Don2P1PatternId.done);
			}else if(s.getHealth() <= 22) {
				//star to finish
				s.addFrames(new FramesIncrement(50));
				s.throwStar();
				s.incPunches();
				s.dealDamage(22);
				s.setCurrentPatternId(Don2P1PatternId.done);
			}else {
				//keep going
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
			if(star) {
				s.addFrames(DELAY_TO_GUT_STAR);
				s.throwStar();
				s.incPunches();
				s.dealDamage(22);
			}else {
				s.addFrames(DELAY_TO_GUT_NO_STAR);
			}
			//assume right gut
			s.incPunches();
			s.dealDamage(5);
			starChance = true;
			s.setCurrentPatternId(Don2P1PatternId.gut1);
			break;
		default:
			throw new RuntimeException("Not a recognized state to continue from");
		}
		s.add(controls);
		
		//__001E and _0019 set, check random events now
		if(starChance && (getStar(s))) {
			s.getStar();
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
		}else {
			if(starChance) {
				s.setCurrentPatternId(Don2P1PatternId.done);
			}
		}
		
		if(s.getCurrentPatternId() == Don2P1PatternId.delay) {
			boolean crowdMoved = ThreadLocalRandom.current().nextDouble() < (8/19.25);
			if(!randomDelayConditions.meetsConditions(s.get_0019(), s.get_001E(), crowdMoved)) {
				s.setCurrentPatternId(Don2P1PatternId.delayDone);
			}
		}
		
		//only do first 2 star chances
		if(s.getCurrentPatternId() == Don2P1PatternId.face) {
			s.setCurrentPatternId(Don2P1PatternId.done);
		}
		
		
	}
	
	private boolean earlyPunchWorks(Don2ILP1FightState s) {
		boolean crowdMoved = ThreadLocalRandom.current().nextDouble() < (8/19.25);
		return takeEarlyPunchConditions.meetsConditions(s.get_0019(), s.get_001E(), crowdMoved);
	}

	private boolean getStar(Don2ILP1FightState s) {
		//check for guaranteed star
		if(s.getPunches() >= 7) {
			s.resetPunches();
			return true;
		}
		if(s.numStars() > 0) {
			//check for random star
			boolean crowdMoved = ThreadLocalRandom.current().nextDouble() < (8/19.25);
			if(randomStarConditions.meetsConditions(s.get_0019(), s.get_001E(), crowdMoved)) {
				s.setPrevPunchStar(true);
				return true;
			}
		}
		return false;
	}
}
