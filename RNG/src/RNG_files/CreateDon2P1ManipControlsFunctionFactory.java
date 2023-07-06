package RNG_files;


import java.util.Collection;

import Memory_Value.FramesIncrement;
import Memory_Value.InputsIncrement;

public class CreateDon2P1ManipControlsFunctionFactory {
	
	//assume star punch before
	public static CreateDon2P1ManipControlsFunction getFunction(Collection<Button> holdButtons, Collection<Button> punchButtons, int delayFrames) {
		int totalFrames = delayFrames;
		InputsIncrement inputsIncrement;
		FramesIncrement framesIncrement;
		
		if(holdButtons.contains(Button.UP) || punchButtons.contains(Button.UP)) {
			//face punch
			totalFrames += 48;
			framesIncrement = new FramesIncrement(delayFrames);
			inputsIncrement = new InputsIncrement(totalFrames*Buttons.getValue(holdButtons) + 11*Buttons.getValue(punchButtons));
		}else if(punchButtons.contains(Button.A)) {
			//right gut
			totalFrames += 49;
			framesIncrement = new FramesIncrement(delayFrames);
			inputsIncrement = new InputsIncrement(totalFrames*Buttons.getValue(holdButtons) + 12*Buttons.getValue(punchButtons));
		}else if(punchButtons.contains(Button.B)) {
			//left gut
			totalFrames += 48;
			framesIncrement = new FramesIncrement(delayFrames-1);
			inputsIncrement = new InputsIncrement(totalFrames*Buttons.getValue(holdButtons) + 10*Buttons.getValue(punchButtons));
		}else {
			throw new RuntimeException("Unknown punch thrown");
		}
		return () -> {return new ManipControls(inputsIncrement, framesIncrement, true);};
	}
}
