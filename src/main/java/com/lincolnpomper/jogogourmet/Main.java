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

	private Frame frame;

	private Main() {

		frame = new Frame(this);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		frame.showScreen();

		startNewGame();
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
		final Answer answer = frame.getAnswer();
		if (answer.getNewFood() != null) {
			GameDataRepository.getInstance().save(new Food(answer.getNewFood()));
		}
		return answer;
	}

	@Override public void showTipOrGuess(String tip) {
		frame.showTipOrGuess(tip);
	}

	@Override public void found(Food rightGuess) {
		frame.showFinalAnswer(rightGuess);
	}
}
