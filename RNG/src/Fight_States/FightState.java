package Fight_States;

import Fight_Results.FightResults;
import Memory_Value.FramesValue;
import Memory_Value.InputsValue;
import RNG_files.ManipControls;
import Random_Events.FrameRuleRandomEvent;
import Random_Events.RandomEvent;

public abstract class FightState {
	protected RandomEvent nextEvent;
	protected FramesValue _001E;
	protected InputsValue _0019;
	protected int framesElapsed;
	
	public FightState(FrameRuleRandomEvent frameRuleEvent) {
		nextEvent = frameRuleEvent;
		_001E = new FramesValue(0);
		_0019 = new InputsValue(0);
		framesElapsed = 0;
	}

	public void add(ManipControls manipControls) {
		_001E = _001E.add(manipControls.getFramesDelayed());
		_0019 = _0019.add(manipControls.getInputsAdded());
	}

	public abstract boolean nextState();

	public abstract FightResults getFightResults();

	public RandomEvent getNextEvent() {
		return nextEvent;
	}
}
