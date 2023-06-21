package RNG_files;
import java.util.HashMap;
import java.util.Map;

import Memory_Value.FramesIncrement;
import Memory_Value.FramesValue;
import Memory_Value.InputsIncrement;


public class RngEventGoal {

	RngEventState prevEvent;
	RngEventConditions conditions;
	FramesIncrement framesBetween;
	FramesIncrement maxDelay;
	
	Map<ManipControls, SuccessChance> inputEffects;
	Map<ManipControls, SuccessChanceModified> inputEffectsModified;
	
	public RngEventGoal(RngEventState prevEvent, RngEventConditions conditions, FramesIncrement framesBetween, FramesIncrement maxDelay) {
		this.prevEvent = prevEvent;
		this.conditions = conditions;
		this.framesBetween = framesBetween;
		this.maxDelay = maxDelay;
		inputEffects = new HashMap<>();
		inputEffectsModified = new HashMap<>();
		
		FramesValue firstFrame = prevEvent.onRngCheck001E.add(framesBetween);
		for(int i = 0; i<256; i++) {
			InputsIncrement inputsAdded = new InputsIncrement(i);
			for(int j = 0; j<maxDelay.getValue(); j++) {
				FramesIncrement framesDelayed = new FramesIncrement(j);
				FramesValue new_001E = firstFrame.add(framesDelayed);
				inputEffects.put(new ManipControls(inputsAdded, framesDelayed), 
						new SuccessChance(prevEvent.possibleInputValues.chanceSuccess(conditions, inputsAdded, new_001E, false, false), 
								prevEvent.possibleInputValues.chanceSuccess(conditions, inputsAdded, new_001E, true, false), 
								prevEvent.possibleInputValues.chanceSuccess(conditions, inputsAdded, new_001E, false, true), 
								prevEvent.possibleInputValues.chanceSuccess(conditions, inputsAdded, new_001E, true, true)));
				inputEffectsModified.put(new ManipControls(inputsAdded, framesDelayed), 
						new SuccessChanceModified(prevEvent.possibleInputValues.chanceSuccess(conditions, inputsAdded, new_001E, false, false), 
								prevEvent.possibleInputValues.chanceSuccess(conditions, inputsAdded, new_001E, true, false), 
								prevEvent.possibleInputValues.chanceSuccess(conditions, inputsAdded, new_001E, false, true), 
								prevEvent.possibleInputValues.chanceSuccess(conditions, inputsAdded, new_001E, true, true)));
			}
		}
	}
}
