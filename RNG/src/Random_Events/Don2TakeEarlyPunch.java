package Random_Events;

import Memory_Value.RngValue;
import RNG_files.ValidRngValueCallable;

public class Don2TakeEarlyPunch implements ValidRngValueCallable {

	public boolean isValidRngValue(RngValue rngValue) {
		return isValidBitArray(rngValue.getBitArray());
	}
	
	private boolean isValidBitArray(boolean[] bitArray) {
		if(bitArray == null) return false;
		if(bitArray.length != 8) return false;
		return bitArray[7];
	}
	
}