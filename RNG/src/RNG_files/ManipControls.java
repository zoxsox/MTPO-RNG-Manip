package RNG_files;

import Memory_Value.FramesIncrement;
import Memory_Value.InputsIncrement;

public class ManipControls {
	InputsIncrement inputsAdded;
	FramesIncrement framesDelayed;
	
	public ManipControls(InputsIncrement inputsAdded, FramesIncrement framesDelayed) {
		this.inputsAdded = inputsAdded;
		this.framesDelayed = framesDelayed;
	}
	
	@Override
	public int hashCode() {
	    return inputsAdded.hashCode() * 31 + framesDelayed.hashCode();
	}
	
	@Override
	public String toString() {
		return "Add " + inputsAdded + " to 0019 and delay " + framesDelayed + " frames";
	}
}
