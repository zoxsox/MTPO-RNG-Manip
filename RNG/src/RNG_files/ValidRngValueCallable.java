package RNG_files;

import Memory_Value.RngValue;

@FunctionalInterface
public interface ValidRngValueCallable {
	boolean isValidRngValue(RngValue rngValue);
}
