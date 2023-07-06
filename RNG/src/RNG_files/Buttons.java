package RNG_files;

import java.util.Collection;
import java.util.Map;

public class Buttons {
	static Map<Button, Integer> buttonVals = Map.of(
			Button.RIGHT, 1,
			Button.DOWN, 2,
			Button.START, 4,
			Button.B, 8,
			Button.A, 16,
			Button.UP, 32,
			Button.SELECT, 64,
			Button.LEFT, 128
			);
	
	static int getValue(Collection<Button> buttons) {
		int val = 0;
		for(Button b: buttons) {
			val |= buttonVals.get(b);
		}
		return val;
	}
}
