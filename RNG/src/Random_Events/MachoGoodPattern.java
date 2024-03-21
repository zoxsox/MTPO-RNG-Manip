package Random_Events;

import Memory_Value.RngValue;
import RNG_files.ValidRngValueCallable;

public class MachoGoodPattern implements ValidRngValueCallable {

	public boolean isValidRngValue(RngValue rngValue) {
		int val = rngValue.getValue() % 16;
		return val >= 6;
	}
	
}