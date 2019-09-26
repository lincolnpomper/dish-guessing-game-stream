package com.lincolnpomper.jogogourmet.logic;

import java.util.Optional;
import java.util.Set;

public class Game implements Runnable {

	private final GuessManager guessManager;

	private Set<Food> foodSet;
	private Food currentGuess;

	public Game(GuessManager guessManager, Set<Food> foodSet) {

		this.guessManager = guessManager;
		this.foodSet = foodSet;

		setupFirstGuess(foodSet);
	}

	private void setupFirstGuess(Set<Food> foodSet) {

		final Optional<Food> foodOptional = foodSet.stream().filter(Food::noParent).findFirst();

		if (foodOptional.isPresent()) {
			currentGuess = foodOptional.get();
		} else {
			throw new RuntimeException("must have one food without parent");
		}
	}

	@Override public void run() {

		boolean run = true;

		while (run) {

			String tipOrGuess = currentGuess.hasTip() ? currentGuess.getTip() : currentGuess.getName();
			guessManager.showTipOrGuess(tipOrGuess);

			while (!guessManager.hasNext()) {
				waitForInput();
			}

			Answer answer = guessManager.getAnswer();

			if (check(answer)) {
				run = false;
			} else {
				getNextGuess(answer, foodSet);
			}
		}

		guessManager.found(currentGuess);
	}

	private boolean check(Answer answer) {

		if (answer == null) {
			throw new RuntimeException("must have an answer");
		}

		return answer.isYes() && foodSet.stream().noneMatch(f -> f.hasParent() && f.getParent().equals(currentGuess));
	}

	private void getNextGuess(Answer answer, Set<Food> foodSet) {

		Optional<Food> foodOptional = Optional.empty();

		if (answer.isYes()) {

			foodOptional = foodSet.stream().filter(f -> f.hasTip() && f.hasParent() && f.getParent().equals(currentGuess)).findFirst();
			if (foodOptional.isEmpty()) {
				foodOptional = foodSet.stream().filter(f -> !f.hasTip() && f.hasParent() && f.getParent().equals(currentGuess)).findFirst();
			}

		} else if (answer.isNo()) {
			foodOptional = foodSet.stream().filter(f -> !f.hasTip() && f.hasParent() && f.getParent().equals(currentGuess)).findFirst();
		}

		if (foodOptional.isPresent()) {
			currentGuess = foodOptional.get();
		} else {
			throw new RuntimeException("could not find the next guess");
		}
	}

	private void waitForInput() {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
