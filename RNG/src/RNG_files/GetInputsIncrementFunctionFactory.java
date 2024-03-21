package RNG_files;


import Memory_Value.InputsIncrement;

public class GetInputsIncrementFunctionFactory {

	public static GetInputsIncrementFunction getFunction(Button button) {
		return () -> new InputsIncrement(Buttons.buttonVals.get(button));
	}
}
