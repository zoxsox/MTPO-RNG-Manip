package RNG_files;

import Memory_Value.InputsValue;

import java.util.concurrent.ThreadLocalRandom;

enum InputBitInfo {
  ONE,
  ZERO,
  UNKNOWN
}

public class KnownInputInfo {
	InputBitInfo[] knownInfo;
	
	public KnownInputInfo(InputBitInfo[] knownInfo) {
		this.knownInfo = knownInfo;
	}

	public boolean meetsConditions(InputsValue _0019)  {
		boolean[] _0019bitArray = _0019.getBitArray();
		for(int i = 0; i<8; i++) {
			if(knownInfo[i] == InputBitInfo.ZERO && _0019bitArray[i]
			|| knownInfo[i] == InputBitInfo.ONE && !_0019bitArray[i]) {
				return false;
			}
		}
		return true;
	}

	public InputsValue randomValidInputsValue() {
		int val = 0;
		for(int i = 0; i<8; i++) {
			if(knownInfo[i] == InputBitInfo.ONE || (knownInfo[i] == InputBitInfo.UNKNOWN && ThreadLocalRandom.current().nextDouble() < 0.5)) {
				val += 1<<(7-i);
			}
		}
		return new InputsValue(val);
	}
}
