package Starting_States;

import Fight_States.Don2P1FightState;
import Fight_States.FightState;
import RNG_files.FightName;
import RNG_files.RngEventConditions;
import Random_Events.Don2Delay;
import Random_Events.Don2NoDelay;
import Random_Events.Don2NoRandomStar;
import Random_Events.Don2RandomStar;
import Random_Events.FrameRuleRandomEvent;
import Random_Events.RandomEvent;


public class StartingStateFactory {
	
	public FightState getStartingState(FightName fightName) {
		switch (fightName) {
		case DON_2_P1:
			return getDon2p1FightState();
		default:
			throw new IllegalStateException();
		}
		
	}

	
	private Don2P1FightState getDon2p1FightState() {
		RngEventConditions randomStarCondition = new RngEventConditions("lucky star", new Don2RandomStar(), true);
		RngEventConditions noRandomStarCondition = new RngEventConditions("no lucky star", new Don2NoRandomStar(), true);
		RngEventConditions delayConditions = new RngEventConditions("1 frame delay", new Don2Delay(), false);
		RngEventConditions noDelayConditions = new RngEventConditions("no 1 frame delay", new Don2NoDelay(), false);
		
		RandomEvent gut2 = new RandomEvent("g2");
		RandomEvent face1 = new RandomEvent("f1");
		RandomEvent delay1 = new RandomEvent("d1");
		RandomEvent gut3 = new RandomEvent("g3");
		RandomEvent gut4 = new RandomEvent("g4");
		RandomEvent gut5 = new RandomEvent("g5");
		RandomEvent face2 = new RandomEvent("f2");
		RandomEvent delay2 = new RandomEvent("d2");
		RandomEvent gut6 = new RandomEvent("g6");
		RandomEvent gut7 = new RandomEvent("g7");
		RandomEvent gut8 = new RandomEvent("g8");
		RandomEvent face3 = new RandomEvent("f3");
		gut2.addEventResult(randomStarCondition, "g2_star", face1);
		gut2.addEventResult(noRandomStarCondition, "g2_no_star", face1);
		face1.addEventResult(randomStarCondition, "f1_star", delay1);
		face1.addEventResult(noRandomStarCondition, "f1_no_star", delay1);
		delay1.addEventResult(delayConditions, "d1_delay", delay1);
		delay1.addEventResult(noDelayConditions, "d1_no_delay", gut3);
		gut3.addEventResult(randomStarCondition, "g3_star", gut4);
		gut3.addEventResult(noRandomStarCondition, "g3_no_star", gut4);
		gut4.addEventResult(randomStarCondition, "g4_star", gut5);
		gut4.addEventResult(noRandomStarCondition, "g4_no_star", gut5);
		gut5.addEventResult(randomStarCondition, "g5_star", face2);
		gut5.addEventResult(noRandomStarCondition, "g5_no_star", face2);
		face2.addEventResult(randomStarCondition, "f2_star", delay2);
		face2.addEventResult(noRandomStarCondition, "f2_no_star", delay2);
		delay2.addEventResult(delayConditions, "d2_delay", delay2);
		delay2.addEventResult(noDelayConditions, "d2_no_delay", gut6);
		gut6.addEventResult(randomStarCondition, "g6_star", gut7);
		gut6.addEventResult(noRandomStarCondition, "g6_no_star", gut7);
		gut7.addEventResult(randomStarCondition, "g7_star", gut8);
		gut7.addEventResult(noRandomStarCondition, "g7_no_star", gut8);
		gut8.addEventResult(randomStarCondition, "g8_star", face3);
		gut8.addEventResult(noRandomStarCondition, "g8_no_star", face3);
		face3.addEventResult(randomStarCondition, "f3_star", null);
		face3.addEventResult(noRandomStarCondition, "f3_no_star", null);
		
		FrameRuleRandomEvent frameRuleEvent = new FrameRuleRandomEvent(gut2);
		Don2P1FightState startingState = new Don2P1FightState(frameRuleEvent);
		return startingState;
	}
}
