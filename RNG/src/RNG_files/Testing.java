package RNG_files;
import java.util.Map;

import Fight_Results.FightResults;
import Fight_States.Don2P1FightState;
import Fight_States.FightState;
import Fight_Strategies.Don2P1FightStrategy;
import Fight_Strategies.FightStrategy;
import Memory_Value.FramesIncrement;
import Memory_Value.FramesValue;
import Random_Events.Don2NoRandomStar;
import Random_Events.Don2RandomStar;
import Random_Events.FrameRuleRandomEvent;
import Random_Events.RandomEvent;
import Starting_States.StartingStateFactory;


public class Testing {

	public static void main(String[] args) {
		//runCalculations();
		runSimulation();
	}
	
	public static void runCalculations() {
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
				if(entry.getKey().getInputsAdded().getValue() == 88 /*&& entry.getKey().framesDelayed == 4*/ && entry.getValue().overallChance > 0.4) {
					//System.out.println(entry.getKey() + ", start at 001E=" + j + ": " + entry.getValue() );
				}
			}
			

			for (Map.Entry<ManipControls, SuccessChanceModified> entry : goal.inputEffectsModified.entrySet()) {
				if(entry.getKey().getInputsAdded().getValue() == 88 /*&& entry.getKey().framesDelayed == 4*/ ){//&& entry.getValue().overallChance > 0.3) {
					System.out.println(entry.getKey().getInputsAdded() + "," + entry.getKey().getFramesDelayed() + "," + j + "," + entry.getValue().overallChance );
				}
			}
		}
	}
	
	public static void runSimulation() {
		FightStrategy strategy = new Don2P1FightStrategy();
		StartingStateFactory startingStateFactory = new StartingStateFactory();
		FightSimulator simulator = new FightSimulator(startingStateFactory.getStartingState(FightName.DON_2_P1), strategy);
		
		FightResults results = simulator.getFightResults();
		System.out.println(results.getFramesTaken());
	}

}
