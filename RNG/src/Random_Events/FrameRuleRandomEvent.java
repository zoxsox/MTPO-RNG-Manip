package Random_Events;

import java.util.ArrayList;

import RNG_files.RngEventConditions;

public class FrameRuleRandomEvent extends RandomEvent {
	
	public FrameRuleRandomEvent(RandomEvent firstEvent) {
		super("frame_rule");
		conditions = new ArrayList<>();
		results = new ArrayList<>();
		conditions.add(new RngEventConditions("start fight", new GuaranteedEvent(), false));
		results.add(new RandomEventResult("started_fight", firstEvent));
	}
	

}
