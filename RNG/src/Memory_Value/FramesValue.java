package Memory_Value;

public final class FramesValue extends MemoryValue {

	public FramesValue(int memoryValue) {
		super(memoryValue);
	}
	

	// To convert 0019 and 001E to 0018, the bits are shuffled around, then the two
	// values are added together
	// These arrays represent how far each bit is shifted to the left
	private static final int[] _001E_BIT_SHIFTS = { +7, -1, +4, -2, +1, -3, -2, -4 };

	// This method shuffles the bits of 001E
	protected FramesRngValue convertToRngValue() {
		int _0018 = 0;
		for (int i = 0; i < 8; i++) {
			if (_001E_BIT_SHIFTS[i] >= 0) {
				_0018 |= (value & (1 << i)) << _001E_BIT_SHIFTS[i];
			} else {
				_0018 |= (value & (1 << i)) >> -_001E_BIT_SHIFTS[i];
			}
		}
		return new FramesRngValue(_0018);
	}
	

	// Add a FramesIncrement and return a new FramesValue
	public FramesValue add(FramesIncrement increment) {
		return new FramesValue(combineValues(value, increment.value));
	}
}
