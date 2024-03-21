package Fight_States;

import Memory_Value.FramesIncrement;
import Memory_Value.FramesValue;

public class FrameRule {
	
	static final int LAG_FRAMES_FROM_RESET = 6;
	static final int LAG_FRAMES_FROM_POWER_ON = 206;
	
	static final int LAG_FRAMES_FROM_TITLE_TO_DON_2 = 35;
	static final int LAG_FRAMES_FROM_TITLE_TO_DON_2_WITH_CODE = 4;
	static final int LAG_FRAMES_FROM_TITLE_TO_DON_2_WITH_CODE_AND_LOSS = 7;
	
	static final double FRAME_RATE = 60.098814;
	
	int frameRuleId;
	double target_001E;
	
	public FrameRule(int f) {
		if(f < 0 || f >=32) {
			throw new RuntimeException("Invalid frame rule id: " + f);
		}
		frameRuleId = f;
		target_001E = ((f+18)*8 + 1.5) % 0x100;
	}

	public FramesIncrement framesToFirstPunch() {
		return new FramesIncrement((5 + 8*frameRuleId) % 0x100);
	}

	public FramesValue macho001EAtGreenShorts() {
		return new FramesValue((6 + (frameRuleId+11)*8) % 0x100);
	}
	
	public String getWindows(boolean IL) {
		return "";
	}
	//frame 4050 -- 001E is 0
	
	public String getDon2SSWindows(int minMinutes, int minSeconds) {
		int totalMinSeconds = 60*minMinutes + minSeconds;
		double baseFrames = target_001E + LAG_FRAMES_FROM_RESET + LAG_FRAMES_FROM_TITLE_TO_DON_2;
		double f = baseFrames + 256*Math.floor((totalMinSeconds * FRAME_RATE) / 256);
		String s = "";
		for(int i = 0; i<50; i++) {
			double seconds = f / FRAME_RATE;
			s += String.format("%d:%06.3f\n", (int)Math.floor(seconds/60), seconds % 60);
			f += 256;
		}
		return s;
	}
	
	public String getDon2ILNoLossWindows(int minMinutes, int minSeconds) {
		int totalMinSeconds = 60*minMinutes + minSeconds;
		double baseFrames = target_001E + LAG_FRAMES_FROM_RESET + LAG_FRAMES_FROM_TITLE_TO_DON_2_WITH_CODE;
		double f = baseFrames + 256*Math.floor((totalMinSeconds * FRAME_RATE) / 256);
		String s = "";
		for(int i = 0; i<50; i++) {
			double seconds = f / FRAME_RATE;
			s += String.format("%d:%06.3f\n", (int)Math.floor(seconds/60), seconds % 60);
			f += 256;
		}
		return s;
	}
	
	public String getDon2ILWithLossWindows(int minMinutes, int minSeconds) {
		int totalMinSeconds = 60*minMinutes + minSeconds;
		double baseFrames = target_001E + LAG_FRAMES_FROM_RESET + LAG_FRAMES_FROM_TITLE_TO_DON_2_WITH_CODE_AND_LOSS;
		double f = baseFrames + 256*Math.floor((totalMinSeconds * FRAME_RATE) / 256);
		String s = "";
		for(int i = 0; i<50; i++) {
			double seconds = f / FRAME_RATE;
			s += String.format("%d:%06.3f\n", (int)Math.floor(seconds/60), seconds % 60);
			f += 256;
		}
		return s;
	}

}
