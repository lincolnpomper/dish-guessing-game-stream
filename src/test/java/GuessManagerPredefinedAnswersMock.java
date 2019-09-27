import com.lincolnpomper.jogogourmet.logic.Answer;
import com.lincolnpomper.jogogourmet.logic.Food;
import com.lincolnpomper.jogogourmet.logic.GuessManager;

public class GuessManagerPredefinedAnswersMock implements GuessManager {

	private String rightGuess;
	private boolean[] yesAnswers = {true, false, true, false, true, true};
	private boolean[] noAnswers = {false, true, false, true, false, false};
	private int pos = 0;

	@Override public boolean hasNext() {
		return true;
	}

	@Override public Answer getAnswer() {
		return new Answer(nextYesAnswer(), nextNoAnswer());
	}

	private boolean nextYesAnswer() {
		return yesAnswers[pos];
	}

	private boolean nextNoAnswer() {
		return noAnswers[pos++];
	}

	@Override public void showTipOrGuess(String tip) {
		System.out.println(tip);
	}

	@Override public void found(String rightGuess) {
		this.rightGuess = rightGuess;
	}

	@Override public void showInput() {
	}

	@Override public void rememberNextParent(Food lastParent) {
	}

	String getRightGuess() {
		return rightGuess;
	}
}
