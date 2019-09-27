package com.lincolnpomper.jogogourmet.logic;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Game implements Runnable {

	private final GuessManager guessManager;

	private Set<FoodGuess> foodGuessSet;

	public Game(GuessManager guessManager, Set<Food> foodSet) {

		this.guessManager = guessManager;
		this.foodGuessSet = foodSet.stream().map(FoodGuess::new).collect(Collectors.toSet());

		setupFirstGuess(foodGuessSet);
	}

	private void setupFirstGuess(Set<FoodGuess> foodSet) {

		final Optional<FoodGuess> foodOptional = foodSet.stream().filter(Food::noParent).findFirst();

		if (foodOptional.isPresent()) {

			FoodGuess guess = foodOptional.get();
			guess.setCurrent(true);
			guess.setTip(true);

		} else {
			throw new RuntimeException("must have one food without parent");
		}
	}

	@Override public void run() {

		boolean run = true;

		while (run) {

			FoodGuess currentGuess = getCurrent();
			guessManager.showTipOrGuess(currentGuess.getGuess());

			while (!guessManager.hasNext()) {
				waitForInput();
			}

			Answer answer = guessManager.getAnswer();

			if (check(answer)) {
				run = false;
			} else {

				processNextGuess(answer);

				if (!hasCurrent()) {
					run = false;
				}
			}
		}

		final Optional<FoodGuess> optionalFoodGuess = foodGuessSet.stream().filter(FoodGuess::isCurrent).findFirst();

		if (optionalFoodGuess.isPresent()) {
			guessManager.found(optionalFoodGuess.get().getGuess());
		} else {
			guessManager.showInput();
		}
	}

	private boolean check(Answer answer) {

		if (answer == null) {
			throw new RuntimeException("must have an answer");
		}

		if (answer.isNo()) {
			return false;
		}

		return answer.isYes() && !getCurrent().isTip();
	}

	private void processNextGuess(Answer answer) {

		final FoodGuess currentGuess = getCurrent();
		FoodGuess nextCurrentGuess = null;

		if (answer.isYes()) {

			if (currentGuess.isTip()) {
				currentGuess.setTip(false);
			} else {
				currentGuess.setVisited(true);
				currentGuess.setCurrent(false);
				markNextCurrent(currentGuess);
			}

		} else if (answer.isNo()) {

			currentGuess.setVisited(true);
			currentGuess.setCurrent(false);

			Optional<FoodGuess> optionalNext = Optional.empty();

			if (currentGuess.isTip() && !currentGuess.hasParent()) {
				optionalNext = foodGuessSet.stream()
						.filter(Predicate.not(Food::hasParent))
						.filter(Predicate.not(FoodGuess::isVisited))
						.findFirst();
			}

			if (optionalNext.isEmpty()) {
				optionalNext = foodGuessSet.stream().filter(Food::hasParent).filter(f -> f.getParent().equals(currentGuess)).findFirst();
			}

			optionalNext.ifPresent(f -> f.setCurrent(true));

			if (hasCurrent()) {

				nextCurrentGuess = getCurrent();

				if (nextCurrentGuess.hasTip()) {
					nextCurrentGuess.setTip(true);
				}

			} else {
				System.out.println("Log: could not find the next guess");
			}
		}

		if (nextCurrentGuess != null || hasCurrent()) {
			guessManager.rememberNextParent(nextCurrentGuess != null ? nextCurrentGuess : currentGuess);
		}
	}

	private void markNextCurrent(FoodGuess currentGuess) {

		Optional<FoodGuess> optionalNext =
				foodGuessSet.stream().filter(Food::hasParent).filter(f -> f.getParent().equals(currentGuess)).findFirst();

		optionalNext.ifPresent(f -> f.setCurrent(true));
	}

	private FoodGuess getCurrent() {

		final Optional<FoodGuess> optional = foodGuessSet.stream().filter(FoodGuess::isCurrent).findFirst();
		if (optional.isEmpty()) {
			throw new RuntimeException("should have a current food");
		}

		return optional.get();
	}

	private boolean hasCurrent() {
		return foodGuessSet.stream().anyMatch(FoodGuess::isCurrent);
	}

	private void waitForInput() {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
