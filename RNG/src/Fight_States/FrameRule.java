package Fight_States;

import Memory_Value.FramesIncrement;

public class FrameRule {
	
	int frameRuleId;
	
	public FrameRule(int f) {
		if(f < 0 || f >=32) {
			throw new RuntimeException("Invalid frame rule id: " + f);
		}
		frameRuleId = f;
	}

	public FramesIncrement framesToFirstPunch() {
		return new FramesIncrement((3 + 8*frameRuleId) % 0x100);
	}
	
	public String getWindows(boolean IL) {
		return "";
	}
	//frame 4050 -- 001E is 0

}
