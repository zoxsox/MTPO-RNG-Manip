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
	    return getInputsAdded().hashCode() * 31 + getFramesDelayed().hashCode();
	}
	
	@Override
	public String toString() {
		return "Add " + getInputsAdded() + " to 0019 and delay " + getFramesDelayed() + " frames";
	}

	public FramesIncrement getFramesDelayed() {
		return framesDelayed;
	}

	public InputsIncrement getInputsAdded() {
		return inputsAdded;
	}
}
