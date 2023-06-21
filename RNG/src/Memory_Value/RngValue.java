package Memory_Value;

public final class RngValue extends MemoryValue {

	private RngValue(int memoryValue) {
		super(memoryValue);
	}
	
	public static RngValue scramble(InputsValue inputsValue, FramesValue framesValue) {
		InputsRngValue inputsRngValue = inputsValue.convertToRngValue();
		FramesRngValue framesRngValue = framesValue.convertToRngValue();
		
		return new RngValue(combineValues(inputsRngValue.value, framesRngValue.value));
	}
	

	// This method calculates what 0018 will be on some future frame, given the 001E
	// value of the reference frame
	// and how many frames in the future you want to look
	// increment_0019 represents what inputs are pressed during that time
	public static RngValue predictFutureRng(
			InputsValue current_0019, 
			FramesValue current_001E, 
			InputsIncrement increment_0019, 
			FramesIncrement increment_001E) {
		InputsValue _0019 = current_0019.add(increment_0019);
		FramesValue _001E = current_001E.add(increment_001E);
		return scramble(_0019, _001E);
	}

	public static RngValue rotateRng(RngValue _0018) {
		int new_0018 = ((_0018.value >> 3) | (_0018.value << 6)) % 0x100;
		return new RngValue(new_0018);
	}
}
