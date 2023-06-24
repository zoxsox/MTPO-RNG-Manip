package Random_Events;

import Memory_Value.RngValue;
import RNG_files.ValidRngValueCallable;

public class Don2Delay implements ValidRngValueCallable {

	public boolean isValidRngValue(RngValue rngValue) {
		return isValidBitArray(rngValue.getBitArray());
	}
	
	private boolean isValidBitArray(boolean[] bitArray) {
		return !bitArray[3];
	}	
}