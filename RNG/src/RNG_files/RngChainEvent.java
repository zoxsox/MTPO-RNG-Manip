package RNG_files;

import Memory_Value.FramesIncrement;

import java.util.List;

public class RngChainEvent {

	String name;
	RngEventConditions conditions;
	FramesIncrement framesToNextCheck;
	FramesIncrement maxDelay;
	GetInputsIncrementFunction getInputsIncrementFunction;
	List<RngChainEvent> nextEvents;



	public RngChainEvent(String name, RngEventConditions conditions, FramesIncrement framesToNextCheck, FramesIncrement maxDelay, GetInputsIncrementFunction getInputsIncrementFunction) {
		this.name = name;
		this.conditions = conditions;
		this.framesToNextCheck = framesToNextCheck;
		this.maxDelay = maxDelay;
		this.getInputsIncrementFunction = getInputsIncrementFunction;
	}


	

}
