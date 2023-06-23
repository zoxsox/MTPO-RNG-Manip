package Fight_Strategies;

import RNG_files.ManipControls;
import Fight_States.Don2P1FightState;
import Fight_States.FightState;
import Memory_Value.FramesIncrement;
import Memory_Value.InputsIncrement;


//idea is some of this would be configurable, but some baseline don 2 facts as well
public class Don2P1FightStrategy implements FightStrategy {

	@Override
	public ManipControls getManipControls(FightState currentState) {
		if(!(currentState instanceof Don2P1FightState)) {
			throw new IllegalStateException();
		}
		Don2P1FightState state = (Don2P1FightState)currentState;
		switch (state.getNextEvent().getName()) {
		case "frame_rule":
			return new ManipControls(new InputsIncrement(0), new FramesIncrement(0));
		case "g1":
		case "f1":
			if(!state.wasPrevPunchStar()) {
				return new ManipControls(new InputsIncrement(64), new FramesIncrement(2));
			}
			return new ManipControls(new InputsIncrement(0), new FramesIncrement(5));

		default:
			break;
		}
		// TODO Auto-generated method stub
		return null;
	}

}
