package Fight_States;

import Memory_Value.FramesValue;
import Memory_Value.InputsValue;

public abstract class FightState {
	protected FramesValue _001E;
	protected InputsValue _0019;
	protected int framesElapsed;
	
	public FightState() {
		_001E = new FramesValue(0);
		_0019 = new InputsValue(0);
		framesElapsed = 0;
	}

	public InputsValue get_0019() {
		return _0019;
	}

	public FramesValue get_001E() {
		return _001E;
	}

	public abstract boolean isDone();
}
