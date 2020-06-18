package com.lincolnpomper.dishguessstream;

import com.lincolnpomper.dishguessstream.data.GameDataRepository;
import com.lincolnpomper.dishguessstream.file.DataLoader;
import com.lincolnpomper.dishguessstream.gui.Frame;
import com.lincolnpomper.dishguessstream.logic.Answer;
import com.lincolnpomper.dishguessstream.logic.Food;
import com.lincolnpomper.dishguessstream.logic.Game;
import com.lincolnpomper.dishguessstream.logic.GuessManager;

import javax.swing.*;

public class Main implements GuessManager, StartGameManager {

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
		dataLoader.setupData();

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
