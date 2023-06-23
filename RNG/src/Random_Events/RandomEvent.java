package Random_Events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Memory_Value.FramesValue;
import Memory_Value.InputsValue;
import RNG_files.RngEventConditions;

public class RandomEvent {
	String name;
	List<RngEventConditions> conditions;
	List<RandomEventResult> results;
	
	static Random rand = new Random();
	
	public RandomEvent(String n) {
		name = n;
		conditions = new ArrayList<>();
		results = new ArrayList<>();
	}
	
	public void addEventResult(RngEventConditions cond, String outcome, RandomEvent event) {
		conditions.add(cond);
		results.add(new RandomEventResult(outcome, event));
	}

	public RandomEventResult getResult(InputsValue _0019, FramesValue _001E) {
		boolean crowdMoved = rand.nextDouble() < (8/19.25);
		for(int i = 0; i < conditions.size(); i++) {
			if(conditions.get(i).meetsConditions(_0019, _001E, crowdMoved)) {
				return results.get(i);
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

}
