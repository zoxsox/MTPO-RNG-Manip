package Fight_Strategies;

import Fight_States.FightState;
import RNG_files.ManipControls;

public interface FightStrategy {

	public ManipControls getManipControls(FightState currentState);
	
}
