package RNG_files;
import java.util.Map;

import Memory_Value.FramesIncrement;
import Memory_Value.FramesValue;
import Random_Events.Don2NoRandomStar;
import Random_Events.Don2RandomStar;


public class Testing {

	public static void main(String[] args) {

		RngEventConditions randomStarCondition = new RngEventConditions("test", new Don2RandomStar(), true);
		RngEventConditions noRandomStarCondition = new RngEventConditions("test", new Don2NoRandomStar(), true);
		
		InputBitInfo[] iArr = {InputBitInfo.UNKNOWN, InputBitInfo.UNKNOWN, InputBitInfo.UNKNOWN, 
				InputBitInfo.UNKNOWN, InputBitInfo.UNKNOWN, InputBitInfo.UNKNOWN, 
				InputBitInfo.UNKNOWN, InputBitInfo.UNKNOWN};
		KnownInputInfo knownInputInfo = new KnownInputInfo(iArr);
		
		//Gut star into gut
//		for(int j = 0; j < 256; j++) {
//			RngEventState s = new RngEventState(randomStarCondition, j, i);
//			RngEventGoal goal = new RngEventGoal(s, randomStarCondition, 49, 7);
//			for (Map.Entry<ManipControls, SuccessChance> entry : goal.inputEffects.entrySet()) {
//				if(entry.getKey().inputsAdded == 192 && /*entry.getKey().framesDelayed == 4 &&*/ entry.getValue().overallChance > 0.4) {
//					System.out.println(entry.getKey() + ", start at 001E=" + j + ": " + entry.getValue() );
//				}
//			}
//		}
		
		//Gut star into face punch
		for(int j = 0; j < 256; j++) {
			FramesValue onRngCheck001E = new FramesValue(j);
			RngEventState s = new RngEventState(randomStarCondition, onRngCheck001E, knownInputInfo);
			RngEventGoal goal = new RngEventGoal(s, randomStarCondition, new FramesIncrement(48), new FramesIncrement(7));
			
			
			for (Map.Entry<ManipControls, SuccessChance> entry : goal.inputEffects.entrySet()) {
				if(entry.getKey().inputsAdded.getValue() == 88 /*&& entry.getKey().framesDelayed == 4*/ && entry.getValue().overallChance > 0.4) {
					//System.out.println(entry.getKey() + ", start at 001E=" + j + ": " + entry.getValue() );
				}
			}
			

			for (Map.Entry<ManipControls, SuccessChanceModified> entry : goal.inputEffectsModified.entrySet()) {
				if(entry.getKey().inputsAdded.getValue() == 88 /*&& entry.getKey().framesDelayed == 4*/ ){//&& entry.getValue().overallChance > 0.3) {
					System.out.println(entry.getKey().inputsAdded + "," + entry.getKey().framesDelayed + "," + j + "," + entry.getValue().overallChance );
				}
			}
		}
	}

}
