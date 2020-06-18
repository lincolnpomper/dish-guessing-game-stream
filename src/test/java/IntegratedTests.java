import com.lincolnpomper.dishguessstream.data.GameDataRepository;
import com.lincolnpomper.dishguessstream.file.DataLoader;
import com.lincolnpomper.dishguessstream.logic.Game;
import com.lincolnpomper.dishguessstream.logic.GuessManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IntegratedTests {

	private GuessManager guessManager;

	@Before public void setup() {

		DataLoader dataLoader = new DataLoader(GameDataRepository.getInstance());
		dataLoader.setupData();

		guessManager = new GuessManagerPredefinedAnswersMock();
	}

	@Test public void whenPredefinedAnswerYesNoShouldFindMacarraoFood() {

		new Game(guessManager, GameDataRepository.getInstance().findAll()).run();
		final String guess = ((GuessManagerPredefinedAnswersMock) guessManager).getRightGuess();

		Assert.assertEquals("Macarrão", guess);
	}
}
