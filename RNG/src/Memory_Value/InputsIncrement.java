package Memory_Value;

import java.util.concurrent.ThreadLocalRandom;

public final class InputsIncrement extends MemoryValue {

	public InputsIncrement(int memoryValue) {
		super(memoryValue);
	}
	
	public static InputsIncrement randomIncrement() {
		return new InputsIncrement(ThreadLocalRandom.current().nextInt(0, 256));
	}
	
	public static InputsIncrement randomIncrementNoRight() {
		return new InputsIncrement(ThreadLocalRandom.current().nextInt(0, 128)*2);
	}
	
	public static InputsIncrement randomIncrementNoRightOrDown() {
		return new InputsIncrement(ThreadLocalRandom.current().nextInt(0, 64)*4);
	}
}
