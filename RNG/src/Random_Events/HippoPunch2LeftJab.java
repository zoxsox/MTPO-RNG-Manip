package Random_Events;

import Memory_Value.RngValue;
import RNG_files.ValidRngValueCallable;

public class HippoPunch2LeftJab implements ValidRngValueCallable {

	public boolean isValidRngValue(RngValue rngValue) {
		int val = (rngValue.getValue() >>> 3) % 8;
		return (val == 0) || (val == 2) || (val == 4);
	}
	
}