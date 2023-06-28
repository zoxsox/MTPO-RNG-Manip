package RNG_files;

import Fight_States.FightState;

public class FightSimulator {

	FightState currentState;
	StateUpdateFunction stateUpdateFunction;
	
	public FightSimulator(FightState startingState, StateUpdateFunction f) {
		currentState = startingState;
		stateUpdateFunction = f;
	}
	
	public void nextFightState() {
		stateUpdateFunction.update(currentState);
	}
	
	public boolean isDone() {
		return currentState.isDone();
	}
	
	public FightState getState() {
		return currentState;
	}
}
