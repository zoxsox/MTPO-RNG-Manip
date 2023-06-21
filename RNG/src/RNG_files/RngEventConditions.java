package RNG_files;

import Memory_Value.FramesValue;
import Memory_Value.InputsValue;
import Memory_Value.RngValue;

public class RngEventConditions {

	String name;
	ValidRngValueCallable validByteCallable;
	boolean afterCrowd;
	
	public RngEventConditions(String name, ValidRngValueCallable validMemoryValueCallable, boolean afterCrowd) {
		this.name = name;
		this.validByteCallable = validMemoryValueCallable;
		this.afterCrowd = afterCrowd;
	}

	public boolean meetsConditions(InputsValue _0019, FramesValue _001E, boolean rotateHappened) {
		RngValue _0018 = RngValue.scramble(_0019, _001E);
		if(rotateHappened)
			_0018 = RngValue.rotateRng(_0018);
		return validByteCallable.isValidRngValue(_0018);
	}
}
