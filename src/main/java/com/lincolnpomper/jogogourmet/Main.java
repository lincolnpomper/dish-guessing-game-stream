package com.lincolnpomper.jogogourmet;

import com.lincolnpomper.jogogourmet.data.GameDataRepository;
import com.lincolnpomper.jogogourmet.file.DataLoader;
import com.lincolnpomper.jogogourmet.gui.Frame;
import com.lincolnpomper.jogogourmet.logic.Answer;
import com.lincolnpomper.jogogourmet.logic.Food;
import com.lincolnpomper.jogogourmet.logic.Game;
import com.lincolnpomper.jogogourmet.logic.GuessManager;

import javax.swing.*;

public class Main implements GuessManager, StartGameManager {

	private static final String BASE_PATH = "src/main/resources/";

	private Frame frame;
	private Food nextParent;

	private Main() {

		frame = new Frame(this);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		frame.showScreen();
	}

	private void startNewGame() {
		Runnable game = new Game(this, GameDataRepository.getInstance().findAll());
		Thread thread = new Thread(game);
		thread.start();
	}

	public static void main(String[] args) {

		DataLoader dataLoader = new DataLoader(GameDataRepository.getInstance());
		dataLoader.setupData(BASE_PATH);

		new Main();
	}

	@Override public void startAgain() {
		startNewGame();
	}

	@Override public boolean hasNext() {
		return frame.hasAnswer();
	}

	@Override public Answer getAnswer() {
		return frame.getAnswer();
	}

	@Override public void saveNewFoodBeforeStart(String newFoodName, String newFoodTipName) {
		if (newFoodName != null) {
			GameDataRepository.getInstance().save(new Food(newFoodName, newFoodTipName, nextParent));
		}
	}

	@Override public void showTipOrGuess(String tip) {
		frame.showTipOrGuess(tip);
	}

	@Override public void found(String rightGuess) {
		frame.showFinalAnswer(rightGuess);
	}

	@Override public void showInput() {
		frame.showInputForNewFood();
	}

	@Override public void rememberNextParent(Food nextParent) {
		this.nextParent = nextParent;
		if (nextParent != null) {
			frame.setParentFoodName(nextParent.getName());
		}
	}
}
