package Random_Events;

import Memory_Value.RngValue;
import RNG_files.ValidRngValueCallable;

public class HippoRightJab implements ValidRngValueCallable {

	public boolean isValidRngValue(RngValue rngValue) {
		int val = rngValue.getValue() % 8;
		return (val == 5) || (val == 7);
	}
	
}