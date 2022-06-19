import java.util.LinkedList;

public class Automaton implements IStringMatcher {

	private static final int CHARS_COUNT = 256;

	private static int getNextState(char[] patternArray, int patLength, int state, int character) {
		//int test = patternArray[state];
		if (state < patLength && character == patternArray[state])
			return state + 1;
		int i;
		for (int nextState = state; nextState > 0; nextState--) {
			if (patternArray[nextState - 1] == character) {
				for (i = 0; i < nextState - 1; i++) {
					if (patternArray[i] != patternArray[state - nextState + 1 + i])
						break;
				}
				if (i == nextState - 1)
					return nextState;
			}
		}
		return 0;
	}

	private static void computeSetOfStates(char[] patternArray, int patLength, int[][] setOfStates) {
		for (int state = 0; state <= patLength; ++state) {
			for (int character = 0; character < CHARS_COUNT; ++character) {
				setOfStates[state][character] = getNextState(patternArray, patLength, state, character);
			}
		}
	}

	@Override
	public LinkedList<Integer> validShifts(String pattern, String text) {
		LinkedList<Integer> result = new LinkedList<>();
		char[] patternArray = pattern.toCharArray();
		char[] txtArray = text.toCharArray();
		int patLength = patternArray.length;
		int textLength = txtArray.length;
		int[][] setOfStates = new int[patLength + 1][CHARS_COUNT];
		computeSetOfStates(patternArray, patLength, setOfStates);
		int i, state = 0;
		for (i = 0; i < textLength; i++) {
			state = setOfStates[state][txtArray[i]];
			if (state == patLength)
				result.add((i - patLength + 1));
		}
		return result;
	}

}