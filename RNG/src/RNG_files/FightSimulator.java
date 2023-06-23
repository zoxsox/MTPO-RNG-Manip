package RNG_files;

import Fight_Results.FightResults;
import Fight_States.FightState;
import Fight_Strategies.FightStrategy;

public class FightSimulator {

	FightState currentState;
	FightStrategy strategy;
	
	public FightSimulator(FightState startingState, FightStrategy str) {
		currentState = startingState;
		strategy = str;
	}
	
	private boolean nextFightState() {
		ManipControls manipControls = strategy.getManipControls(currentState);
		currentState.add(manipControls);
		return currentState.nextState();
	}
	
	public FightResults getFightResults() {
		while(nextFightState()) {
			
		}
		return currentState.getFightResults();
	}
}
