package Fight_Strategies;

import Fight_States.FightState;
import Fight_States.FrameRule;
import RNG_files.ManipControls;

public interface FightStrategy {

	public FrameRule getFrameRule();
	
	public ManipControls getManipControls(FightState state);
	
}
