package RNG_files;

import Memory_Value.FramesValue;

public class RngEventState {
	
	
	RngEventConditions conditions;
	FramesValue onRngCheck001E;
	KnownInputInfo knownInputInfo;
	
	PossibleInputValues possibleInputValues;
	
	public RngEventState(RngEventConditions conditions, FramesValue onRngCheck001E, KnownInputInfo knownInputInfo) {
		this.conditions = conditions;
		this.onRngCheck001E = onRngCheck001E;
		this.knownInputInfo = knownInputInfo;
		this.possibleInputValues = new PossibleInputValues(conditions, onRngCheck001E, knownInputInfo);
		
	}


	

}
