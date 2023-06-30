package RNG_files;

import Memory_Value.FramesIncrement;
import Memory_Value.InputsIncrement;

public class ManipControls {
	InputsIncrement inputsAdded;
	FramesIncrement framesDelayed;
	boolean manip;
	
	public ManipControls(InputsIncrement inputsAdded, FramesIncrement framesDelayed) {
		this.inputsAdded = inputsAdded;
		this.framesDelayed = framesDelayed;
		this.manip = false;
	}
	
	public ManipControls(InputsIncrement inputsAdded, FramesIncrement framesDelayed, boolean m) {
		this.inputsAdded = inputsAdded;
		this.framesDelayed = framesDelayed;
		this.manip = m;
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
