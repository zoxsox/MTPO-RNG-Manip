package RNG_files;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import Fight_States.Don2ILP1FightState;
import Fight_States.Don2P1FightState;
import Fight_States.FightState;
import Fight_States.FrameRule;
import Fight_Strategies.Don2ILP1FightStrategy;
import Fight_Strategies.Don2P1FightStrategy;
import Memory_Value.FramesIncrement;
import Memory_Value.FramesValue;
import Memory_Value.InputsIncrement;
import Memory_Value.InputsValue;
import Memory_Value.RngValue;
import Random_Events.Don2NoRandomStar;
import Random_Events.Don2RandomStar;
import Random_Events.GuaranteedEvent;
import Random_Events.HippoLongShortDelay;
import Random_Events.HippoOpen;
import Random_Events.HippoPunch2LeftJab;
import Random_Events.HippoPunch2Open;
import Random_Events.HippoPunch2RightJab;
import Random_Events.HippoShortLongDelay;

public class Testing {

	public static void main(String[] args) {
//		hippoCalculations();
		runILSimulation();
		// runSSSimulation();
		//frameRuleTesting();
//		testingIncreaseRandomness();
//		for(int k = 0; k<256; k++) {
//		int shortCounter = 0;
//		int totalCounter = 0;
//		for(int i = 0; i<256; i+=2) {
//			for(int j = 4; j<8; j++) {
//				InputsValue iv = new InputsValue(i);
//				FramesValue fv = new FramesValue(j);
//				RngValue rng = RngValue.scramble(iv, fv);
//				if((rng.getValue() >> 1) % 2 == 1) {
//					totalCounter++;
//					RngValue next = RngValue.predictFutureRng(iv, fv, new InputsIncrement(k), new FramesIncrement(2));
//					boolean shortDelay = (next.getValue() >> 1) % 2 == 1;
//					if(shortDelay) {
//						shortCounter++;
//					}
//				}
//			}
//		}
//		if(((double)shortCounter) / totalCounter > .51)
//			System.out.println(k + ": " + ((double)shortCounter) / totalCounter);
//		}
	}

	private static void hippoCalculations() {
		RngEventConditions hippoOpenCondition = new RngEventConditions("test", new HippoOpen(), false);
		RngEventConditions longDelayCondition = new RngEventConditions("test", new HippoLongShortDelay(), false);
		RngEventConditions shortDelayCondition = new RngEventConditions("test", new HippoShortLongDelay(), false);
		RngEventConditions hippo2ndOpenCondition = new RngEventConditions("test", new HippoPunch2Open(), false);
		RngEventConditions hippoPunch2LeftJabCondition = new RngEventConditions("test", new HippoPunch2LeftJab(), false);
		RngEventConditions hippoPunch2RightJabCondition = new RngEventConditions("test", new HippoPunch2RightJab(), false);
		InputBitInfo[] iArr = { InputBitInfo.UNKNOWN, InputBitInfo.UNKNOWN, InputBitInfo.UNKNOWN, InputBitInfo.UNKNOWN,
				InputBitInfo.UNKNOWN, InputBitInfo.UNKNOWN, InputBitInfo.ZERO, InputBitInfo.ZERO };
		KnownInputInfo knownInputInfo = new KnownInputInfo(iArr);
		
		//shorts blue to 1st punch check: 115
		//shorts blue to delay+2nd punch check: 358 (2 right guts)
		//shorts blue to 12-28 3rd punch check: 414 (2 right guts)
		//shorts blue to 12-28 4th punch check: 686 (5 total right guts)
		//shorts blue to 28-0 3rd punch check: 430 (2 right guts)
		//shorts blue to 28-0 4th punch check: 674 (5 total right guts)
		//5559 shorts blue
		//5674 first open check
		//5917 delay + 2nd punch check
		
		//5973 3rd punch check for 12-28 frame delay (early frame)
		//6245 4th punch check for 12-28 frame delay (early frame)
		
		//5989 3rd punch check for 28-0 frame delay (early frame)
		//6233 4th punch check for 28-0 frame delay (early frame)
		
		
		//673
		//6247 
		//new backup strat
		//shorts blue to delay+2nd punch check: 356
		//shorts to 12-28 3rd punch check (first frame cancel): 404
		//shorts to 28-0 3rd punch check (first frame cancel): 420
		//shorts to 12-28 4th punch check (first frame cancel): 673
		//shorts to 28-0 4th punch check (first frame cancel): 661
		//5963 3rd punch check for 12-28 (first frame cancel)
		
		//37/38 manip strat
		//shorts blue to delay+2nd punch check: 356
		//shorts to 12-28 3rd punch check (first frame cancel): 609
		//shorts to 28-0 3rd punch check (first frame cancel): 625
		//6168 12-28 3rd punch check (no slowdowns)
		
		for (int j = 4; j < 256; j+=8) { //when shorts turn blue
			//j=36 for 5:00 strat, j=20 for 5:04 strat
			FramesValue onRngCheck001E = new FramesValue(j);
			RngEventState s = new RngEventState(new RngEventConditions("test", new GuaranteedEvent(), false), onRngCheck001E, knownInputInfo);
			RngEventGoal goal = new RngEventGoal(s, hippoOpenCondition, new FramesIncrement(420),
					new FramesIncrement(70));
//			RngEventState s = new RngEventState(new RngEventConditions("test", new HippoPunch2LeftJab(), false), new FramesValue(j+356), knownInputInfo);
//			RngEventGoal goal = new RngEventGoal(s, shortDelayCondition, new FramesIncrement(0),
//					new FramesIncrement(0));

			for (Map.Entry<ManipControls, SuccessChanceModified> entry : goal.inputEffectsModified.entrySet()) {
				if ((j == 36) && entry.getKey().getInputsAdded().getValue() == 0 && entry.getValue().overallChance > 0.2) {
					System.out.println(entry.getKey().getInputsAdded() + "," + entry.getKey().getFramesDelayed() + ","
							+ j + "," + Math.round(entry.getValue().overallChance*1000)/1000.0);
				}
			}
		}
		
	}


	private static void frameRuleTesting() {
		FrameRule fr = new FrameRule(0);

		System.out.println(fr.getDon2SSWindows(10, 0));
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
		StateUpdateFunction stateUpdateFunction = new Don2ILP1StateUpdateFunction(
				new Don2ILP1FightStrategy(fr, d1and2, d3, i1, i2));
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
			FightState startingState = new Don2ILP1FightState();
			FightSimulator simulator = new FightSimulator(startingState, stateUpdateFunction);
			while (!simulator.isDone()) {
				simulator.nextFightState();
			}
			if (((Don2ILP1FightState) simulator.getState()).numStars() == 3) {
				num3Stars++;
			}
		}
		return num3Stars / ((double) numIters);
	}

	public static List<Integer> getStarCounts(int fr, int d1and2, int d3, int numIters, int i1, int i2) {
		StateUpdateFunction stateUpdateFunction = new Don2ILP1StateUpdateFunction(
				new Don2ILP1FightStrategy(fr, d1and2, d3, i1, i2));
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
			FightState startingState = new Don2ILP1FightState();
			FightSimulator simulator = new FightSimulator(startingState, stateUpdateFunction);
			while (!simulator.isDone()) {
				simulator.nextFightState();
			}
			starCounts.add(((Don2ILP1FightState) simulator.getState()).numStars());
		}
		return starCounts;
	}

	public static List<Boolean> getBlocks(int fr, int d1and2, int d3, int numIters, int inc1, int inc2) {
		StateUpdateFunction stateUpdateFunction = new Don2ILP1StateUpdateFunction(
				new Don2ILP1FightStrategy(fr, d1and2, d3, inc1, inc2));
		List<Boolean> blocks = new ArrayList<>();
		for (int i = 0; i < numIters; i++) {
			FightState startingState = new Don2ILP1FightState();
			FightSimulator simulator = new FightSimulator(startingState, stateUpdateFunction);
			while (!simulator.isDone()) {
				simulator.nextFightState();
			}
			blocks.add(((Don2ILP1FightState) simulator.getState()).isBlocked());
		}
		return blocks;
	}

	public static void runILSimulation() {
		double maxProb = 0;
		int maxfr = -1, maxd1 = -1, maxInc1 = -1, maxInc2 = -1;
		// IL testing

		// human possible increments
		List<List<Integer>> incsList = new ArrayList<>();
		incsList.add(Arrays.asList(64, 160));
		incsList.add(Arrays.asList(224, 176));
		incsList.add(Arrays.asList(192, 224));
		incsList.add(Arrays.asList(96, 240));
		//		incsList.add(Arrays.asList(104,164));
		//		incsList.add(Arrays.asList(8, 180));
		//		incsList.add(Arrays.asList(232,228));
		//		incsList.add(Arrays.asList(136,244));

		for (int fr = 0; fr < 32; fr++) {
			for (int d1and2 = 0; d1and2 < 8; d1and2++) {
				for (List<Integer> l : incsList) {
					double probSum = 0;
					int count = 0;
					for (int i = d1and2; i <= d1and2; i++) {
						probSum += getProbability3Stars(fr, i, 0, 100000, l.get(0), l.get(1));
						count++;
					}
					double prob = probSum / count;
					if (prob > maxProb) {
						maxProb = prob;
						maxd1 = d1and2;
						maxfr = fr;
						maxInc1 = l.get(0);
						maxInc2 = l.get(1);
					}
				}
			}
		}
		System.out.println("Best strategy:");
		System.out.printf("d1and2: %d, fr: %d, inc1: %d, inc2: %d, prob: %f%n", maxd1, maxfr, maxInc1,
				maxInc2, maxProb);

		//		d1and2: 6, fr: 24, inc1: 9, inc2: 55, prob: 0.063000
		//		0.85967
		//		0.081475
		//		0.058855
		//		0.006489

		//		d1and2: 5, fr: 22, inc1: 192, inc2: 224, prob: 0.033550

		int numIters = 10000000;
		for(int i = 0; i<10; i++) {
			printILStratStats(18, i, numIters, 64, 160);
		}

		FrameRule fr18 = new FrameRule(18);
		System.out.println("\nFirst try don 2 timings:");
		System.out.println(fr18.getDon2ILNoLossWindows(0, 30));
		System.out.println("Second try don 2 timings:");
		System.out.println(fr18.getDon2ILWithLossWindows(1, 0));


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

	}

	private static void printILStratStats(int fr, int delay, int numIters, int inc1, int inc2) {
		System.out.printf("\nDelay %d frames: ", delay);
		List<Integer> starCounts1 = getStarCounts(fr, delay, 0, numIters,inc1,inc2);
		List<Boolean> blocks1 = getBlocks(fr, delay, 0, numIters,inc1,inc2);
		System.out.println("Chance don't get 1st star: " + starCounts1.stream().filter(a -> a == 1).mapToDouble(a -> a).count() / ((double) numIters));
		System.out.println("Chance get 1st star but not 2nd " + starCounts1.stream().filter(a -> a == 2).mapToDouble(a -> a).count() / ((double) numIters));
		System.out.println("Chance get both stars: " + starCounts1.stream().filter(a -> a == 3).mapToDouble(a -> a).count() / ((double) numIters));
		System.out.println("Chance get blocked after getting 1st star: " + blocks1.stream().filter(a -> a).count() / ((double) numIters));
		System.out.println();
	}

	private static void testingIncreaseRandomness() {
		//factors that are random but could be consistent by person
		//time pressing any buttons
		//delays between punches

		int innerIters = 100000;
		int numParams = 200;

		List<List<Double>> results = new ArrayList<>();
		for(int j = 0; j<numParams; j++) {
			List<Integer> delaysList = new ArrayList<>();
			for(int i = 0; i < 11; i++) {
				delaysList.add(ThreadLocalRandom.current().nextInt(0,5));
				//delaysList.add(j);
			}
			int standardAPress = ThreadLocalRandom.current().nextInt(0,16);
			int standardUpOnFace = ThreadLocalRandom.current().nextInt(0,8);
			int standardBOnFace = ThreadLocalRandom.current().nextInt(0,32);
			int standardStartOnStar = ThreadLocalRandom.current().nextInt(0,64);
			int[] starCounts = new int[4];
			int totalStars = 0;
			for (int i = 0; i < innerIters; i++) {	
				int fr = ThreadLocalRandom.current().nextInt(0,32);
				StateUpdateFunction stateUpdateFunction = new Don2P1StateUpdateFunction(new Don2P1FightStrategy(fr, new HashMap<>(), delaysList, standardAPress, standardBOnFace, standardUpOnFace, standardStartOnStar));
				FightState startingState = new Don2P1FightState();
				FightSimulator simulator = new FightSimulator(startingState, stateUpdateFunction);
				while (!simulator.isDone()) {
					simulator.nextFightState();
				}
				Don2P1FightState finalState = (Don2P1FightState) simulator.getState();
				starCounts[finalState.numStars()]++;
				totalStars += finalState.numStars();
			}
			List<Double> trialResults = new ArrayList<>();
			for(int i = 0; i<4; i++) {
				trialResults.add(starCounts[i] / ((double)innerIters));
			}
			trialResults.add(totalStars / ((double)innerIters));
			results.add(trialResults);
		}
		//System.out.println(results);
		
		List<Double> zeroStarPs = results.stream().map(l -> l.get(0)).sorted().collect(Collectors.toList());
		List<Double> oneStarPs = results.stream().map(l -> l.get(1)).sorted().collect(Collectors.toList());
		List<Double> twoStarPs = results.stream().map(l -> l.get(2)).sorted().collect(Collectors.toList());
		List<Double> threeStarPs = results.stream().map(l -> l.get(3)).sorted().collect(Collectors.toList());
		
		System.out.println("zero="+zeroStarPs);
		System.out.println("one="+oneStarPs);
		System.out.println("two="+twoStarPs);
		System.out.println("three="+threeStarPs);
		
		System.out.println("0 star sd: " + sd(zeroStarPs));
		System.out.println("1 star sd: " + sd(oneStarPs));
		System.out.println("2 star sd: " + sd(twoStarPs));
		System.out.println("3 star sd: " + sd(threeStarPs));

//		List<Double> avgPerParamsDoubles = results.stream().map(l -> l.stream().map(il -> il.get(4)).mapToDouble(a -> a).average().orElse(0)).collect(Collectors.toList());
//
//		System.out.println(avgPerParamsDoubles);
//		System.out.println(sd(avgPerParamsDoubles));
//		System.out.println(sd(avgPerParamsDoubles) / avgPerParamsDoubles.stream().mapToDouble(a -> a).average().orElse(0));
//
//		List<List<Double>> avgsList = results.stream().map(l -> l.stream().map(il -> il.get(4)).collect(Collectors.toList())).collect(Collectors.toList());
//		System.out.println(avgsList);
//		List<Double> sdOfAvgs = avgsList.stream().map(l -> sd(l)).collect(Collectors.toList());
//		System.out.println(sdOfAvgs);
	}

	private static double sd(List<Double> list) {
		// Calculate sum.
		double sum = list.stream().mapToDouble(i -> i).sum();

		// Calculate mean.
		double mean = list.stream().mapToDouble(i -> i).average().orElse(0);

		// Calculate sd partial.
		double sd = list.stream().collect(Collectors.summingDouble(val -> Math.pow(val - mean, 2)));
		// or list.stream().mapToDouble(val -> Math.pow(val - mean, 2)).sum();

		return Math.sqrt(sd / list.size());
	}


	public static void runSSSimulation() {
		double max = 0;
		int maxfr = -1;

		// test match
		//		System.out.println(Don2P1FightStrategy.holdAManip.getManipControls());
		//		System.out.println(CreateDon2P1ManipControlsFunctionFactory
		//				.getFunction(Collections.emptyList(), Arrays.asList(Button.A), 0).getManipControls());
		//
		//		System.out.println(Don2P1FightStrategy.holdAandSelectAndPunchBManip.getManipControls());
		//		System.out.println(CreateDon2P1ManipControlsFunctionFactory
		//				.getFunction(Arrays.asList(Button.A, Button.SELECT), Arrays.asList(Button.B), 0).getManipControls());
		//
		//		System.out.println(Don2P1FightStrategy.misdirectManip.getManipControls());
		//		System.out.println(CreateDon2P1ManipControlsFunctionFactory
		//				.getFunction(Arrays.asList(Button.UP), Arrays.asList(Button.B), 0).getManipControls());

		Map<String, Collection<Button>> holdButtons = new HashMap<>();
		Map<String, Collection<Button>> punchButtons = new HashMap<>();
		holdButtons.put("gutHoldAManip", Arrays.asList());
		punchButtons.put("gutHoldAManip", Arrays.asList(Button.A));
		holdButtons.put("gutHoldBManip", Arrays.asList());
		punchButtons.put("gutHoldBManip", Arrays.asList(Button.B));
		holdButtons.put("gutHoldSelectAndPunchAManip", Arrays.asList(Button.SELECT));
		punchButtons.put("gutHoldSelectAndPunchAManip", Arrays.asList(Button.A));
		holdButtons.put("gutHoldSelectAndPunchBManip", Arrays.asList(Button.SELECT));
		punchButtons.put("gutHoldSelectAndPunchBManip", Arrays.asList(Button.B));
		holdButtons.put("gutHoldAandPunchBManip", Arrays.asList(Button.A));
		punchButtons.put("gutHoldAandPunchBManip", Arrays.asList(Button.B));
		holdButtons.put("gutHoldBandPunchAManip", Arrays.asList(Button.B));
		punchButtons.put("gutHoldBandPunchAManip", Arrays.asList(Button.A));
		holdButtons.put("gutHoldAandSelectAndPunchBManip", Arrays.asList(Button.A, Button.SELECT));
		punchButtons.put("gutHoldAandSelectAndPunchBManip", Arrays.asList(Button.B));
		holdButtons.put("gutHoldBandSelectAndPunchAManip", Arrays.asList(Button.B, Button.SELECT));
		punchButtons.put("gutHoldBandSelectAndPunchAManip", Arrays.asList(Button.A));

		holdButtons.put("faceMisdirectManip", Arrays.asList(Button.UP));
		punchButtons.put("faceMisdirectManip", Arrays.asList(Button.B));
		holdButtons.put("faceMisdirectAndHoldAManip", Arrays.asList(Button.UP, Button.A));
		punchButtons.put("faceMisdirectAndHoldAManip", Arrays.asList(Button.B));
		holdButtons.put("faceHoldUpandBManip", Arrays.asList());
		punchButtons.put("faceHoldUpandBManip", Arrays.asList(Button.UP, Button.B));

		Set<String> possibleManips = holdButtons.keySet();

		for (int fr = 0; fr < 32; fr++) {
			double frameRuleScore = 0;
			Map<String, Pair<String, Integer>> bestManips = new HashMap<>();
			List<String> bestScoreSequence = new ArrayList<>();
			for (String punchManiping : Don2P1FightStrategy.manipNames) {
				double maxScore = 0;
				String maxScoreStrat = "";
				Pair<String, Integer> maxManip = null;
				for (String manip : possibleManips) {
					if((punchManiping.startsWith("gut") && !manip.startsWith("gut")) || 
							(punchManiping.startsWith("face") && !manip.startsWith("face"))) {
						continue;
					}
					for (int delay = 0; delay < 8; delay++) {
						CreateDon2P1ManipControlsFunction function = CreateDon2P1ManipControlsFunctionFactory
								.getFunction(holdButtons.get(manip), punchButtons.get(manip), delay);
						Map<String, CreateDon2P1ManipControlsFunction> manips = Map.of(punchManiping, function);
						double score = getScore(100000, fr, manips, punchManiping);
						if (score > maxScore) {
							maxScore = score;
							maxScoreStrat = String.format("Punch: %s, Manip: %s, Delay: %d, Score: %f", punchManiping, manip, delay, score);
							maxManip = new Pair<>(manip, delay);
						}
					}
				}
				bestScoreSequence.add(maxScoreStrat);
				frameRuleScore += maxScore;
				bestManips.put(punchManiping, maxManip);
			}
			System.out.println(String.format("Max score for fr %d: %f", fr, frameRuleScore));
			for(String s: bestScoreSequence) {
				System.out.println(s);
			}
			Map<String, CreateDon2P1ManipControlsFunction> manips = new HashMap<>();
			for(String m: bestManips.keySet()) {
				CreateDon2P1ManipControlsFunction function = CreateDon2P1ManipControlsFunctionFactory
						.getFunction(holdButtons.get(bestManips.get(m).first), punchButtons.get(bestManips.get(m).first), bestManips.get(m).second);
				manips.put(m, function);
			}
			System.out.println(getStarResults(1000000, fr, manips));
			System.out.println("Normal: " +getStarResults(1000000, fr, new HashMap<>()));
		}
	}

	public static String getStarResults(int numIters, int fr, Map<String, CreateDon2P1ManipControlsFunction> manips) {
		StateUpdateFunction stateUpdateFunction = new Don2P1StateUpdateFunction(new Don2P1FightStrategy(fr, manips));
		int totalStars = 0;
		int[] starCounts = new int[4];
		for (int i = 0; i < numIters; i++) {
			FightState startingState = new Don2P1FightState();
			FightSimulator simulator = new FightSimulator(startingState, stateUpdateFunction);
			while (!simulator.isDone()) {
				simulator.nextFightState();
			}
			Don2P1FightState finalState = (Don2P1FightState) simulator.getState();
			int numStars = finalState.numStars();
			starCounts[numStars]++;
			totalStars += numStars;
		}
		String resultString = "";
		for(int i = 0; i<4; i++) {
			resultString += String.format("Chance of %d stars: %f\n", i, starCounts[i] / ((double)numIters));
		}
		resultString += String.format("Average stars: %f\n", totalStars / ((double)numIters));
		return resultString;
	}

	public static double getScore(int numIters, int fr, Map<String, CreateDon2P1ManipControlsFunction> manips, String manipToScore) {
		StateUpdateFunction stateUpdateFunction = new Don2P1StateUpdateFunction(new Don2P1FightStrategy(fr, manips));
		Map<String, Integer> manipSuccesses = new HashMap<>();
		Map<String, Integer> manipAttempts = new HashMap<>();
		for (int i = 0; i < numIters; i++) {
			FightState startingState = new Don2P1FightState();
			FightSimulator simulator = new FightSimulator(startingState, stateUpdateFunction);
			while (!simulator.isDone()) {
				simulator.nextFightState();
			}
			Don2P1FightState finalState = (Don2P1FightState) simulator.getState();
			if (!finalState.getAttemptedManip().equals("none")) {
				manipAttempts.merge(finalState.getAttemptedManip(), 1, Integer::sum);
				if (finalState.isSuccessfulManip())
					manipSuccesses.merge(finalState.getAttemptedManip(), 1, Integer::sum);
			}
		}
		return manipSuccesses.get(manipToScore) / ((double) manipAttempts.get(manipToScore));
	}

}
