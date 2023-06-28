package RNG_files;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

public class Testing {

	public static void main(String[] args) {
		// runCalculations();
		runSimulation();
	}

	public static void runCalculations() {
		RngEventConditions randomStarCondition = new RngEventConditions("test", new Don2RandomStar(), true);
		RngEventConditions noRandomStarCondition = new RngEventConditions("test", new Don2NoRandomStar(), true);

		InputBitInfo[] iArr = { InputBitInfo.UNKNOWN, InputBitInfo.UNKNOWN, InputBitInfo.UNKNOWN, InputBitInfo.UNKNOWN,
				InputBitInfo.UNKNOWN, InputBitInfo.UNKNOWN, InputBitInfo.UNKNOWN, InputBitInfo.UNKNOWN };
		KnownInputInfo knownInputInfo = new KnownInputInfo(iArr);

		// Gut star into gut
//		for(int j = 0; j < 256; j++) {
//			RngEventState s = new RngEventState(randomStarCondition, j, i);
//			RngEventGoal goal = new RngEventGoal(s, randomStarCondition, 49, 7);
//			for (Map.Entry<ManipControls, SuccessChance> entry : goal.inputEffects.entrySet()) {
//				if(entry.getKey().inputsAdded == 192 && /*entry.getKey().framesDelayed == 4 &&*/ entry.getValue().overallChance > 0.4) {
//					System.out.println(entry.getKey() + ", start at 001E=" + j + ": " + entry.getValue() );
//				}
//			}
//		}

		// Gut star into face punch
		for (int j = 0; j < 256; j++) {
			FramesValue onRngCheck001E = new FramesValue(j);
			RngEventState s = new RngEventState(randomStarCondition, onRngCheck001E, knownInputInfo);
			RngEventGoal goal = new RngEventGoal(s, randomStarCondition, new FramesIncrement(48),
					new FramesIncrement(7));

			for (Map.Entry<ManipControls, SuccessChance> entry : goal.inputEffects.entrySet()) {
				if (entry.getKey().getInputsAdded().getValue() == 88
						/* && entry.getKey().framesDelayed == 4 */ && entry.getValue().overallChance > 0.4) {
					// System.out.println(entry.getKey() + ", start at 001E=" + j + ": " +
					// entry.getValue() );
				}
			}

			for (Map.Entry<ManipControls, SuccessChanceModified> entry : goal.inputEffectsModified.entrySet()) {
				if (entry.getKey().getInputsAdded().getValue() == 88 /* && entry.getKey().framesDelayed == 4 */ ) {// &&
																													// entry.getValue().overallChance
																													// >
																													// 0.3)
																													// {
					System.out.println(entry.getKey().getInputsAdded() + "," + entry.getKey().getFramesDelayed() + ","
							+ j + "," + entry.getValue().overallChance);
				}
			}
		}
	}

	public static double getProbability3Stars(int fr, int d1and2, int d3, int numIters, int i1, int i2) {
		StateUpdateFunction stateUpdateFunction = new Don2P1StateUpdateFunction(
				new Don2P1FightStrategy(fr, d1and2, d3,i1,i2));
		int num3Stars = 0;
		for (int i = 0; i < numIters; i++) {
//			if(i == 200 && num3Stars == 0) {
//				//unlikely to be good 
//				return 0;
//			}
//			if(i == 1000 && num3Stars < 25) {
//				//unlikely to be good 
//				return 0;
//			}
			FightState startingState = new Don2P1FightState();
			FightSimulator simulator = new FightSimulator(startingState, stateUpdateFunction);
			while (!simulator.isDone()) {
				simulator.nextFightState();
			}
			if(((Don2P1FightState) simulator.getState()).numStars() == 3) {
				num3Stars++;
			}
		}
		return num3Stars / ((double)numIters);
	}

	public static List<Integer> getStarCounts(int fr, int d1and2, int d3, int numIters, int i1, int i2) {
		StateUpdateFunction stateUpdateFunction = new Don2P1StateUpdateFunction(
				new Don2P1FightStrategy(fr, d1and2, d3,i1,i2));
		List<Integer> starCounts = new ArrayList<>();
		for (int i = 0; i < numIters; i++) {
//			if(i == 300 && starCounts.stream().filter(a -> a == 3).mapToDouble(a -> a).count() == 0) {
//				//unlikely to be good 
//				return starCounts;
//			}
//			if(i == 1000 && starCounts.stream().filter(a -> a == 3).mapToDouble(a -> a).count() < 8) {
//				//unlikely to be good 
//				return starCounts;
//			}
			FightState startingState = new Don2P1FightState();
			FightSimulator simulator = new FightSimulator(startingState, stateUpdateFunction);
			while (!simulator.isDone()) {
				simulator.nextFightState();
			}
			starCounts.add(((Don2P1FightState) simulator.getState()).numStars());
		}
		return starCounts;
	}

	public static List<Boolean> getBlocks(int fr, int d1and2, int d3, int numIters, int inc1, int inc2) {
		StateUpdateFunction stateUpdateFunction = new Don2P1StateUpdateFunction(
				new Don2P1FightStrategy(fr, d1and2, d3,inc1,inc2));
		List<Boolean> blocks = new ArrayList<>();
		for (int i = 0; i < numIters; i++) {
			FightState startingState = new Don2P1FightState();
			FightSimulator simulator = new FightSimulator(startingState, stateUpdateFunction);
			while (!simulator.isDone()) {
				simulator.nextFightState();
			}
			blocks.add(((Don2P1FightState) simulator.getState()).isBlocked());
		}
		return blocks;
	}

	public static void runSimulation() {
//		double maxProb = 0;
//		int maxfr = -1, maxd1 = -1, maxInc1 = -1, maxInc2 = -1;
//		// IL testing
//		
//		//human possible increments
//		List<List<Integer>> incsList = new ArrayList<>();
//		incsList.add(Arrays.asList(64,160));
//		incsList.add(Arrays.asList(224,176));
//		incsList.add(Arrays.asList(192,224));
//		incsList.add(Arrays.asList(96,240));
//		
//		
//		for (int fr = 0; fr < 32; fr++) {
//			for (int d1and2 = 0; d1and2 < 8; d1and2++) {
//				for(List<Integer> l: incsList) {
//					double probSum = 0;
//					int count = 0;
//					for (int i = d1and2; i <= d1and2; i++) {
//						probSum += getProbability3Stars(fr, i, 0, 100000, l.get(0), l.get(1));
//						count++;
//					}
//					double prob = probSum / count;
//					if (prob > maxProb) {
//						maxProb = prob;
//						maxd1 = d1and2;
//						maxfr = fr;
//						maxInc1 = l.get(0);
//						maxInc2 = l.get(1);
//					}
//				}
//			}
//		}
//		System.out.println(String.format("d1and2: %d, fr: %d, inc1: %d, inc2: %d, prob: %f", maxd1, maxfr, maxInc1, maxInc2, maxProb));

		
//		d1and2: 6, fr: 24, inc1: 9, inc2: 55, prob: 0.063000
//		0.85967
//		0.081475
//		0.058855
//		0.006489

//		d1and2: 5, fr: 22, inc1: 192, inc2: 224, prob: 0.033550
		
		
		int numIters = 1000000;
		List<Integer> starCounts1 = getStarCounts(22, 5, 0, numIters,192,224);
		//List<Integer> starCounts2 = getStarCounts(29, 2, 0, numIters);
		List<Boolean> blocks1 = getBlocks(22, 5, 0, numIters,192,224);
		//List<Boolean> blocks2 = getBlocks(29, 2, 0, numIters);
		System.out.println(starCounts1.stream().filter(a -> a == 1).mapToDouble(a -> a).count() / ((double) numIters));
		System.out.println(starCounts1.stream().filter(a -> a == 2).mapToDouble(a -> a).count() / ((double) numIters));
		System.out.println(starCounts1.stream().filter(a -> a == 3).mapToDouble(a -> a).count() / ((double) numIters));
		//System.out.println(starCounts2.stream().filter(a -> a == 3).mapToDouble(a -> a).count() / ((double) numIters));
		System.out.println(blocks1.stream().filter(a -> a).count() / ((double) numIters));
		//System.out.println(blocks1.stream().limit(100).collect(Collectors.toList()));

//		double maxProb = 0;
//		int maxfr = -1, maxd1 = -1, maxd3 = -1;
//		// IL testing
//		for (int fr = 0; fr < 32; fr++) {
//			for (int d1and2 = 1; d1and2 < 10; d1and2++) {
//				for (int d3 = 0; d3 < 8; d3++) {
//					double probSum = 0;
//					for(int i = d1and2-1; i<=d1and2+1; i++) {
//						for(int j = d3; j<=d3; j++) {
//							probSum += getProbability(fr, i, j, 100000);
//						}
//					}
//					double prob = probSum/3;
//					if (prob > maxProb) {
//						maxProb = prob;
//						maxd1 = d1and2;
//						maxd3 = d3;
//						maxfr = fr;
//					}
//				}
//			}
//		}
//		System.out.println(String.format("d1and2: %d, d3: %d, fr: %d, prob: %f", maxd1, maxd3, maxfr, maxProb));

//		for(int j = 0; j<32; j++) {
//		StateUpdateFunction stateUpdateFunction = new Don2P1StateUpdateFunction(new Don2P1FightStrategy(j));
//		List<Integer> starCounts = new ArrayList<>();
//		int numIters = 1000000;
//		for(int i = 0; i<numIters; i++) {
//			FightState startingState = new Don2P1FightState();
//			FightSimulator simulator = new FightSimulator(startingState, stateUpdateFunction);
//			while(!simulator.isDone()) {
//				simulator.nextFightState();
//			}
//			starCounts.add(((Don2P1FightState)simulator.getState()).numStars());
//		}
//		System.out.println(starCounts.stream().filter(a -> a == 0).mapToDouble(a -> a).count() / ((double)numIters));
//		System.out.println(starCounts.stream().filter(a -> a == 1).mapToDouble(a -> a).count() / ((double)numIters));
//		System.out.println(starCounts.stream().filter(a -> a == 2).mapToDouble(a -> a).count() / ((double)numIters));
//		System.out.println(starCounts.stream().filter(a -> a == 3).mapToDouble(a -> a).count() / ((double)numIters));
//		System.out.println(starCounts.stream().mapToDouble(a -> a).average());
//		}
		
//		StateUpdateFunction stateUpdateFunction = new Don2P1StateUpdateFunction(
//				new Don2P1FightStrategy(0, 0, 0));
//		FightState startingState = new Don2P1FightState();
//		FightSimulator simulator = new FightSimulator(startingState, stateUpdateFunction);
//		while (!simulator.isDone()) {
//			System.out.println(simulator.getState());
//			simulator.nextFightState();
//		}
//		System.out.println(((Don2P1FightState)simulator.getState()).isBlocked());

	}

}
