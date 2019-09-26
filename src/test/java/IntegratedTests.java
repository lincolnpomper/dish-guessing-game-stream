import com.lincolnpomper.jogogourmet.data.GameDataRepository;
import com.lincolnpomper.jogogourmet.file.DataLoader;
import com.lincolnpomper.jogogourmet.logic.Food;
import com.lincolnpomper.jogogourmet.logic.Game;
import com.lincolnpomper.jogogourmet.logic.GuessManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IntegratedTests {

	private static final String BASE_PATH = "src/test/resources/";

	private GuessManager guessManager;

	@Before public void setup() {

		DataLoader dataLoader = new DataLoader(GameDataRepository.getInstance());
		dataLoader.setupData(BASE_PATH);

		guessManager = new GuessManagerAlwaysAnswerYesMock();
	}

	@Test public void whenAlwaysAnswerYesShouldFindMacarraoFood() {

		new Game(guessManager, GameDataRepository.getInstance().findAll()).run();
		final Food guess = ((GuessManagerAlwaysAnswerYesMock) guessManager).getRightGuess();

		Assert.assertEquals("Macarrão", guess.getName());
	}

}
