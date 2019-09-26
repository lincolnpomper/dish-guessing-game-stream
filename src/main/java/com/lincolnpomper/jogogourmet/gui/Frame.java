package com.lincolnpomper.jogogourmet.gui;

import com.lincolnpomper.jogogourmet.Main;
import com.lincolnpomper.jogogourmet.StartGameManager;
import com.lincolnpomper.jogogourmet.logic.Answer;
import com.lincolnpomper.jogogourmet.logic.Food;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Frame extends JFrame implements MouseListener, AnswerProvider {

	private JLabel labelQuestion;
	private JTextField inputNewFood;

	private JButton buttonYes;
	private JButton buttonNo;
	private JButton buttonAgain;

	private boolean buttonYesPressed;
	private boolean buttonNoPressed;

	private StartGameManager startGameManager;

	public Frame(StartGameManager startGameManager) {

		super("Jogo Gourmet");

		this.startGameManager = startGameManager;

		labelQuestion = new JLabel("Pense em um prato que gosta.");
		inputNewFood = new JTextField(10);

		buttonYes = new JButton("Sim");
		buttonYes.addMouseListener(this);
		buttonNo = new JButton("Não");
		buttonNo.addMouseListener(this);

		buttonAgain = new JButton("Jogar novamente");
		buttonAgain.addMouseListener(this);

		this.getContentPane().setLayout(new BorderLayout());

		JPanel compNorth = new JPanel();
		compNorth.add(labelQuestion);

		JPanel compCenter = new JPanel();
		compCenter.add(inputNewFood);

		JPanel compSouthButtons = new JPanel();
		compSouthButtons.setLayout(new FlowLayout());
		compSouthButtons.add(Box.createHorizontalStrut(100));
		compSouthButtons.add(buttonNo);
		compSouthButtons.add(buttonYes);
		compSouthButtons.add(buttonAgain);
		compSouthButtons.add(Box.createHorizontalStrut(100));

		this.getContentPane().add(compNorth, BorderLayout.NORTH);
		this.getContentPane().add(compCenter, BorderLayout.CENTER);
		this.getContentPane().add(compSouthButtons, BorderLayout.SOUTH);

		disableAnswerButtons();
		hidePlayAgainButton();
		hideNewFoodInput();
	}

	public void showScreen() {
		this.pack();
		this.setVisible(true);
	}

	public Answer getAnswer() {

		String newFoodName = getNewFoodName();
		Answer answer = new Answer(buttonYesPressed, buttonNoPressed, newFoodName);

		resetAnswer();

		return answer;
	}

	private String getNewFoodName() {

		String newFoodName = inputNewFood.getText();
		if (newFoodName != null && newFoodName.isEmpty()) {
			newFoodName = null;
		}
		return newFoodName;
	}

	public boolean hasAnswer() {
		return buttonYesPressed || buttonNoPressed;
	}

	private void resetAnswer() {
		buttonYesPressed = false;
		buttonNoPressed = false;
		inputNewFood.setText("");
	}

	@Override public void mouseClicked(MouseEvent e) {
		if (e.getSource() != null && e.getSource().equals(buttonYes)) {
			this.buttonYesPressed = true;
			disableAnswerButtons();
		} else if (e.getSource() != null && e.getSource().equals(buttonNo)) {
			this.buttonNoPressed = true;
			disableAnswerButtons();
		} else if (e.getSource() != null && e.getSource().equals(buttonAgain)) {
			hidePlayAgainButton();
			showYesNoButtons();
			startGameManager.startAgain();
		}
	}

	@Override public void mousePressed(MouseEvent e) {
	}

	@Override public void mouseReleased(MouseEvent e) {
	}

	@Override public void mouseEntered(MouseEvent e) {
	}

	@Override public void mouseExited(MouseEvent e) {
	}

	public void showFinalAnswer(Food finalAnswer) {
		this.labelQuestion.setText("Acertei de novo! A comida é " + finalAnswer.getName());

		showPlayAgainButton();
		hideYesNoButtons();
	}

	public void showTipOrGuess(String tipOrGuess) {
		labelQuestion.setText("O prato que você pensou é " + tipOrGuess + "?");
		offerAnswerButtons();
	}

	private void disableAnswerButtons() {
		buttonYes.setEnabled(false);
		buttonNo.setEnabled(false);
	}

	private void offerAnswerButtons() {
		buttonYes.setEnabled(true);
		buttonNo.setEnabled(true);
	}

	private void hidePlayAgainButton() {
		buttonAgain.setVisible(false);
		this.pack();
	}

	private void showPlayAgainButton() {
		buttonAgain.setVisible(true);
		this.pack();
	}

	private void showYesNoButtons() {
		buttonYes.setVisible(true);
		buttonNo.setVisible(true);
		this.pack();
	}

	private void hideYesNoButtons() {
		buttonYes.setVisible(false);
		buttonNo.setVisible(false);
		this.pack();
	}

	private void hideNewFoodInput() {
		inputNewFood.setVisible(false);
		this.pack();
	}

	private void showNewFoodInput() {
		inputNewFood.setVisible(true);
		this.pack();
	}
}