package com.lincolnpomper.dishguessstream.logic;

public interface GuessManager {

	boolean hasNext();

	Answer getAnswer();

	void showTipOrGuess(String tip);

	void found(String rightGuess);

	void showInput();

	void rememberNextParent(Food forParent);
}
