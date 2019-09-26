package com.lincolnpomper.jogogourmet.logic;

public interface GuessManager {

	boolean hasNext();

	Answer getAnswer();

	void showTipOrGuess(String tip);

	void found(Food rightGuess);
}
