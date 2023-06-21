package Memory_Value;

public final class InputsValue extends MemoryValue {

	public InputsValue(int memoryValue) {
		super(memoryValue);
	}
	

	// To convert 0019 and 001E to 0018, the bits are shuffled around, then the two
	// values are added together
	// These arrays represent how far each bit is shifted to the left
	private static final int[] _0019_BIT_SHIFTS = { +0, +1, +2, +3, -3, -2, -1, -0 };

	// This method shuffles the bits of 0019
	// Planning to simplify conversion
	protected InputsRngValue convertToRngValue() {
		int _0018 = 0;
		for (int i = 0; i < 8; i++) {
			if (_0019_BIT_SHIFTS[i] >= 0) {
				// |= is like += except with bitwise OR instead of addition
				// & is the bitwise AND operator
				// << is the left shift operator
				_0018 |= (value & (1 << i)) << _0019_BIT_SHIFTS[i];
			} else {
				// >> is the right shift operator
				_0018 |= (value & (1 << i)) >> -_0019_BIT_SHIFTS[i];
			}
		}
		return new InputsRngValue(_0018);
	}
	
	// Add a InputsIncrement and return a new InputsValue
	public InputsValue add(InputsIncrement increment) {
		return new InputsValue(combineValues(value,increment.value));
	}
}
