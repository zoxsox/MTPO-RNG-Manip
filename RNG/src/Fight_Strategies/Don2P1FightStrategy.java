package Fight_Strategies;

import Fight_States.Don2P1FightState;
import Fight_States.FightState;
import Fight_States.FrameRule;
import Memory_Value.FramesIncrement;
import Memory_Value.InputsIncrement;
import RNG_files.ManipControls;


//idea is some of this would be configurable, but some baseline don 2 facts as well
public class Don2P1FightStrategy implements FightStrategy {
	
	public Don2P1FightStrategy() {
		
	}


	public ManipControls getManipControls(FightState state) {
		// TODO update this
		return new ManipControls(new InputsIncrement(50), new FramesIncrement(50));
		
	}

	public FrameRule getFrameRule() {
		// TODO Auto-generated method stub
		return new FrameRule();
		
	}

	public boolean throwStar(FightState state) {
		// TODO Auto-generated method stub
		return true;
	}

}
