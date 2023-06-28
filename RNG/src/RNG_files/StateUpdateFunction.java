package RNG_files;

import Fight_States.FightState;

@FunctionalInterface
public interface StateUpdateFunction {
	void update(FightState state);
}
