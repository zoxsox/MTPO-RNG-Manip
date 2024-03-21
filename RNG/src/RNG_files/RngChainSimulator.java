package RNG_files;

import Fight_States.FrameRule;
import Memory_Value.FramesIncrement;
import Memory_Value.FramesValue;
import Memory_Value.InputsValue;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RngChainSimulator {


	FrameRule frameRule;
	KnownInputInfo knownInputInfo;
	RngChainEvent firstEvent;



	public RngChainSimulator(FrameRule frameRule, KnownInputInfo knownInputInfo, RngChainEvent firstEvent) {
		this.frameRule = frameRule;
		this.knownInputInfo = knownInputInfo;
		this.firstEvent = firstEvent;
	}


	public Map<Map<String, FramesIncrement>, Double> simulate(int numIters) {
		Map<String, Integer> delayMapping = new HashMap<>();
		traverseGraph(firstEvent, delayMapping);
		Map<Map<String, FramesIncrement>, Double> results = new HashMap<>();
		simulateCombinations(delayMapping, new HashMap<>(), delayMapping.keySet().toArray(new String[0]), 0, numIters, results);
		return results;
	}

	private static void traverseGraph(RngChainEvent event, Map<String, Integer> delayMapping) {
		if (event.nextEvents.isEmpty() || delayMapping.containsKey(event.name)) {
			return;
		}

		delayMapping.put(event.name, event.maxDelay.getValue());
		for (RngChainEvent child : event.nextEvents) {
			traverseGraph(child, delayMapping);
		}
	}

	private void simulateCombinations(Map<String, Integer> map, Map<String, FramesIncrement> combination, String[] labels, int index, int numIters, Map<Map<String, FramesIncrement>, Double> results) {
		if (index == labels.length) {
			simulate(new HashMap<>(combination), numIters, results);
			return;
		}

		String label = labels[index];
		int maxVal = map.get(label);

		for (int i = 0; i <= maxVal; i++) {
			combination.put(label, new FramesIncrement(i));
			simulateCombinations(map, combination, labels, index + 1, numIters, results);
		}
	}

	private void simulate(Map<String, FramesIncrement> delays, int numIters, Map<Map<String, FramesIncrement>, Double> results) {
		int successes = 0;
		for (int i = 0; i < numIters; i++) {
			FramesValue startingFrame = frameRule.macho001EAtGreenShorts();
			InputsValue startingInputs = knownInputInfo.randomValidInputsValue();
			if(success(firstEvent, startingFrame, startingInputs, delays)) {
				successes++;
			}
		}
		results.put(delays, ((double)successes)/numIters);
	}

	private boolean success(RngChainEvent event, FramesValue _001E, InputsValue _0019, Map<String, FramesIncrement> delays) {
		if (event.conditions.meetsConditions(_0019, _001E, false)) { // TODO: Add rotate logic
			if (event.nextEvents.isEmpty()) {
				// base case
				return true;
			}
			boolean success = false;
			for (RngChainEvent nextEvent: event.nextEvents) {
				// 1 child event must succeed
				if (success(nextEvent, _001E.add(delays.get(event.name)), _0019.add(event.getInputsIncrementFunction.getInputsIncrement()), delays)) {
					success = true;
					break;
				}
			}
			return success;
		}
		return false;
	}


}
