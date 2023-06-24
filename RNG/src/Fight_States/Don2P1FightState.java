package Fight_States;

import Fight_Results.Don2P1FightResults;
import Memory_Value.FramesIncrement;
import Random_Events.FrameRuleRandomEvent;
import Random_Events.RandomEventResult;

public class Don2P1FightState extends FightState {
	
	StarCount stars;
	boolean prevPunchStar;
	int framesDelayedByStars;
	
	final int GUT_TO_GUT = 49;
	final int GUT_TO_FACE = 48;
	final int FACE_TO_DELAY = 31;
	final int DELAY_TO_DELAY = 1;
	final int DELAY_TO_GUT_STAR = 134;
	final int DELAY_TO_GUT_NO_STAR = 79;
	final int GUT_STAR_DELAY = 6;
	final int FACE_STAR_DELAY = 1;
	
	public Don2P1FightState(FrameRuleRandomEvent frameRuleEvent) {
		super(frameRuleEvent);
		stars = new StarCount(0);
		framesDelayedByStars = 0;
	}

	@Override
	public boolean nextState() {
		RandomEventResult result = nextEvent.getResult(_0019, _001E);
		switch (result.getOutcome()) {
		case "started_fight":
			break;
		case "g3_star":
		case "g4_star":
		case "g6_star":
		case "g7_star":
			updateState(GUT_TO_GUT, GUT_STAR_DELAY);
			break;
		case "g3_no_star":
		case "g4_no_star":
		case "g6_no_star":
		case "g7_no_star":
			updateState(GUT_TO_GUT, 0);
			break;
		case "g2_star":
		case "g5_star":
		case "g8_star":
			updateState(GUT_TO_FACE, GUT_STAR_DELAY);
			break;
		case "g2_no_star":
		case "g5_no_star":
		case "g8_no_star":
			updateState(GUT_TO_FACE, 0);
			break;
		case "f1_star":
		case "f2_star":
			updateState(FACE_TO_DELAY, FACE_STAR_DELAY);
			break;
		case "f1_no_star":
		case "f2_no_star":
			updateState(FACE_TO_DELAY, 0);
			break;
		case "d1_delay":
		case "d2_delay":
			updateState(DELAY_TO_DELAY, 0);
		case "d1_no_delay":
			if(stars.stars > 1) {
				updateState(DELAY_TO_GUT_STAR, 0);
				stars.throwStar();
			}else {
				updateState(DELAY_TO_GUT_NO_STAR, 0);
			}
		case "d2_no_delay":
			updateState(DELAY_TO_GUT_STAR, 0);
			stars.throwStar();
			
		default:
			break;
		}
		nextEvent = result.getNextEvent();
		return true;
	}

	private void updateState(int normalIncrement, int starIncrement) {
		_001E =_001E.add(new FramesIncrement(normalIncrement + starIncrement));
		framesElapsed += normalIncrement + starIncrement;
		if(starIncrement > 0) {
			stars.addStar();
			framesDelayedByStars += starIncrement;
			prevPunchStar = true;
		}else {
			prevPunchStar = false;
		}
		
	}

	@Override
	public Don2P1FightResults getFightResults() {
		return new Don2P1FightResults(framesElapsed, stars.getNumStars());
	}

	public boolean wasPrevPunchStar() {
		return prevPunchStar;
	}

}
