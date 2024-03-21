package Random_Events;

import Memory_Value.RngValue;
import RNG_files.ValidRngValueCallable;

public class MachoPunch3Upper implements ValidRngValueCallable {

	public boolean isValidRngValue(RngValue rngValue) {
		int val = rngValue.getValue() % 8;
		return (val != 0) && (val != 1) && (val != 7);
	}
	
}