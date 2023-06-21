package RNG_files;
import java.util.HashSet;
import java.util.Set;

import Memory_Value.FramesValue;
import Memory_Value.InputsIncrement;
import Memory_Value.InputsValue;

public class PossibleInputValues {
	Set<InputsValue> possibleValuesWithoutCrowd;
	Set<InputsValue> possibleValuesWithCrowd;

	public PossibleInputValues(RngEventConditions conditions, FramesValue onRngCheck001E, KnownInputInfo knownInputInfo) {
		possibleValuesWithoutCrowd = new HashSet<>();
		for (int i = 0; i < 256; i++) {
			InputsValue _0019 = new InputsValue(i);
			if (knownInputInfo.meetsConditions(_0019) && conditions.meetsConditions(_0019, onRngCheck001E, false)) {
				possibleValuesWithoutCrowd.add(_0019);
			}
		}
		if (conditions.afterCrowd) {
			possibleValuesWithCrowd = new HashSet<>();
			for (int i = 0; i < 256; i++) {
				InputsValue _0019 = new InputsValue(i);
				if (knownInputInfo.meetsConditions(_0019) && conditions.meetsConditions(_0019, onRngCheck001E, true)) {
					possibleValuesWithCrowd.add(_0019);
				}
			}
		} else {
			possibleValuesWithCrowd = possibleValuesWithoutCrowd;
		}
	}

	public double chanceSuccess(RngEventConditions conditions, InputsIncrement inc_0019, FramesValue _001E, boolean firstCrowd,
			boolean secondCrowd) {
		Set<InputsValue> usedSet;
		if (firstCrowd)
			usedSet = possibleValuesWithCrowd;
		else {
			usedSet = possibleValuesWithoutCrowd;
		}
		int countSuccess = 0;
		for (InputsValue _0019 : usedSet) {
			InputsValue new_0019 = _0019.add(inc_0019);
			if (conditions.meetsConditions(new_0019, _001E, secondCrowd)) {
				countSuccess++;
			}

		}
		return ((double) countSuccess) / usedSet.size();
	}

}
