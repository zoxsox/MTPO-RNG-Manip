package Random_Events;

import Memory_Value.RngValue;
import RNG_files.ValidRngValueCallable;

public class GuaranteedEvent implements ValidRngValueCallable {

	@Override
	public boolean isValidRngValue(RngValue rngValue) {
		return true;
	}

}
