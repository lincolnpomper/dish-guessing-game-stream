import com.lincolnpomper.jogogourmet.logic.Answer;
import com.lincolnpomper.jogogourmet.logic.Food;
import com.lincolnpomper.jogogourmet.logic.GuessManager;

public class GuessManagerAlwaysAnswerYesMock implements GuessManager {

	private Food rightGuess;

	@Override public boolean hasNext() {
		return true;
	}

	@Override public Answer getAnswer() {
		return new Answer(true, false, "");
	}

	@Override public void showTipOrGuess(String tip) {
		System.out.println(tip);
	}

	@Override public void found(Food rightGuess) {
		this.rightGuess = rightGuess;
	}

	Food getRightGuess() {
		return rightGuess;
	}
}
