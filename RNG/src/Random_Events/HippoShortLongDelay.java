package Random_Events;

import Memory_Value.RngValue;
import RNG_files.ValidRngValueCallable;


//12 frame delay then 28 frame delay
public class HippoShortLongDelay implements ValidRngValueCallable {

	public boolean isValidRngValue(RngValue rngValue) {
		return (rngValue.getValue() % 16) <= 9;
	}
	
}