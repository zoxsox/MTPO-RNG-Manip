//package RNG_files;
//
//public class RngHelper {
//
//	// This RNG model was found by lucandor158
//	// The 0019 memory value is determined by inputs
//	// On each frame, some number is added to 0019 based on which buttons are
//	// pressed on that frame
//	// The 001E memory value is a frame counter
//	// Each frame it counts up 1, and it loops back to 0 when it reaches 256
//	// These two memory values are used to determine 0018, the RNG value
//	// 0018 is used for deciding which actions the opponent will take
//	// By controlling 001E and partially controlling 0019, we can partially control
//	// 0018, and manipulate opponents's actions
//
//	// To convert 0019 and 001E to 0018, the bits are shuffled around, then the two
//	// values are added together
//	// These arrays represent how far each bit is shifted to the left
//	public static final int[] _0019_BIT_SHIFTS = { +0, +1, +2, +3, -3, -2, -1, -0 };
//	public static final int[] _001E_BIT_SHIFTS = { +7, -1, +4, -2, +1, -3, -2, -4 };
//
//	// This method shuffles the bits of 0019
//	public static int convert0019To0018(int _0019) {
//		int _0018 = 0;
//		for (int i = 0; i < 8; i++) {
//			if (_0019_BIT_SHIFTS[i] >= 0) {
//				// |= is like += except with bitwise OR instead of addition
//				// & is the bitwise AND operator
//				// << is the left shift operator
//				_0018 |= (_0019 & (1 << i)) << _0019_BIT_SHIFTS[i];
//			} else {
//				// >> is the right shift operator
//				_0018 |= (_0019 & (1 << i)) >> -_0019_BIT_SHIFTS[i];
//			}
//		}
//		return _0018;
//	}
//
//	// This method shuffles the bits of 001E
//	public static int convert001ETo0018(int _001E) {
//		int _0018 = 0;
//		for (int i = 0; i < 8; i++) {
//			if (_001E_BIT_SHIFTS[i] >= 0) {
//				_0018 |= (_001E & (1 << i)) << _001E_BIT_SHIFTS[i];
//			} else {
//				_0018 |= (_001E & (1 << i)) >> -_001E_BIT_SHIFTS[i];
//			}
//		}
//		return _0018;
//	}
//
//	// This method shuffles the bits of 0019 and 001E then adds them together
//	public static int convertBothTo0018(int _0019, int _001E) {
//		return (convert0019To0018(_0019) + convert001ETo0018(_001E)) % 0x100;
//	}
//
//	// This method calculates what 0018 will be on some future frame, given the 001E
//	// value of the reference frame
//	// and how many frames in the future you want to look
//	// increment_0019 represents what inputs are pressed during that time
//	public static int predictFutureRng(int current_0019, int current_001E, int increment_0019, int increment_001E) {
//
//		return convertBothTo0018((current_0019 + increment_0019) % 0x100, (current_001E + increment_001E) % 0x100);
//	}
//	
//	public static int rotateRng(int _0018) {
//		return ((_0018 >> 3) | (_0018 << 6)) % 0x100;
//	}
//
//	private static String integerToUpperCaseHex(int number) {
//		String h = Integer.toHexString(number).toUpperCase();
//		if (h.length() == 2)
//			return h;
//		return "0" + h;
//	}
//
//}
