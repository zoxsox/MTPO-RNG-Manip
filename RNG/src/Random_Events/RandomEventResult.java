package Random_Events;

public class RandomEventResult {
	String outcome;
	RandomEvent nextEvent;
	
	public RandomEventResult(String o, RandomEvent r) {
		outcome = o;
		nextEvent = r;
	}

	public String getOutcome() {
		return outcome;
	}

	public RandomEvent getNextEvent() {
		return nextEvent;
	}
}
