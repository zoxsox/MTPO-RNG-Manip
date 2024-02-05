package Random_Events;

import Memory_Value.RngValue;
import RNG_files.ValidRngValueCallable;


//28 frame delay then 0 frame delay
public class HippoLongShortDelay implements ValidRngValueCallable {

	public boolean isValidRngValue(RngValue rngValue) {
		return (rngValue.getValue() % 16) >= 10;
	}
	
}