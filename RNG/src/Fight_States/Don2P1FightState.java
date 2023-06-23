package Fight_States;

import Fight_Results.Don2P1FightResults;
import Memory_Value.InputsIncrement;
import Random_Events.FrameRuleRandomEvent;
import Random_Events.RandomEventResult;

public class Don2P1FightState extends FightState {
	
	StarCount stars;
	boolean prevPunchStar;
	int framesDelayedByStars;
	
	public Don2P1FightState(FrameRuleRandomEvent frameRuleEvent) {
		super(frameRuleEvent);
		stars = new StarCount(0);
		framesDelayedByStars = 0;
	}

	@Override
	public boolean nextState() {
		RandomEventResult result = nextEvent.getResult(_0019, _001E);
		if(result == null) {
			//TODO: add last frames
			nextEvent = null;
			return false;
		}
		switch (result.getOutcome()) {
		case "started_fight":
			break;
		case "g1_star":
		case "g2_star":
			_0019 =_0019.add(new InputsIncrement(55));
			framesElapsed += 55;
			stars.addStar();
			framesDelayedByStars += 6;
			break;
		case "g1_no_star":
		case "g2_no_star":
			_0019 =_0019.add(new InputsIncrement(49));
			framesElapsed += 49;
			break;
		default:
			break;
		}
		nextEvent = result.getNextEvent();
		return true;
	}

	@Override
	public Don2P1FightResults getFightResults() {
		return new Don2P1FightResults(framesElapsed, stars.getNumStars());
	}

	public boolean wasPrevPunchStar() {
		return prevPunchStar;
	}

}
