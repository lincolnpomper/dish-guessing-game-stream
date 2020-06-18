package com.lincolnpomper.dishguessstream.gui;

import com.lincolnpomper.dishguessstream.StartGameManager;
import com.lincolnpomper.dishguessstream.logic.Answer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Frame extends JFrame implements MouseListener, AnswerProvider {

	private JLabel labelQuestion;
	private JTextField inputNewFood;
	private JTextField inputNewFoodTip;

	private final JButton buttonStart = new JButton("Iniciar");
	private final JButton buttonYes = new JButton("Sim");
	private final JButton buttonNo = new JButton("Não");
	private final JButton buttonAgain = new JButton("Jogar novamente");
	private final JButton buttonInputOk = new JButton("Ok");
	private final JButton buttonInputTipOk = new JButton("Ok");

	private boolean buttonYesPressed;
	private boolean buttonNoPressed;

	private String parentFoodName;

	private StartGameManager startGameManager;

	public Frame(StartGameManager startGameManager) {

		super("Jogo Gourmet");

		this.startGameManager = startGameManager;

		labelQuestion = new JLabel();
		inputNewFood = new JTextField(10);
		inputNewFoodTip = new JTextField(10);

		buttonStart.addMouseListener(this);
		buttonYes.addMouseListener(this);
		buttonNo.addMouseListener(this);
		buttonAgain.addMouseListener(this);
		buttonInputOk.addMouseListener(this);
		buttonInputTipOk.addMouseListener(this);

		this.getContentPane().setLayout(new BorderLayout());

		JPanel compNorth = new JPanel();
		compNorth.add(labelQuestion);

		JPanel compCenter = new JPanel();
		compCenter.add(inputNewFood);
		compCenter.add(inputNewFoodTip);

		JPanel compSouthButtons = new JPanel();
		compSouthButtons.setLayout(new FlowLayout());
		compSouthButtons.add(Box.createHorizontalStrut(100));
		compSouthButtons.add(buttonStart);
		compSouthButtons.add(buttonYes);
		compSouthButtons.add(buttonNo);
		compSouthButtons.add(buttonAgain);
		compSouthButtons.add(buttonInputOk);
		compSouthButtons.add(buttonInputTipOk);
		compSouthButtons.add(Box.createHorizontalStrut(100));

		this.getContentPane().add(compNorth, BorderLayout.NORTH);
		this.getContentPane().add(compCenter, BorderLayout.CENTER);
		this.getContentPane().add(compSouthButtons, BorderLayout.SOUTH);

		hidePlayAgainButton();
		hideNewFoodInputAndOkButton();
		hideNewFoodInputTipAndOkButton();
		hideYesNoButtons();
		showStartButtonAndStartText();
	}

	public Answer getAnswer() {

		Answer answer = new Answer(safeGetButtonYes(), safeGetButtonNo());

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

	private String getNewFoodTipName() {

		String newFoodTipName = inputNewFoodTip.getText();
		if (newFoodTipName != null && newFoodTipName.isEmpty()) {
			newFoodTipName = null;
		}
		return newFoodTipName;
	}

	public boolean hasAnswer() {
		return safeGetButtonYes() || safeGetButtonNo();
	}

	private void resetAnswer() {
		safeChangeButtonYes(false);
		safeChangeButtonNo(false);
		inputNewFood.setText("");
		inputNewFoodTip.setText("");
	}

	public void showInputForNewFood() {
		hideYesNoButtons();
		showNewFoodInputAndOkButton();
	}

	@Override public void mouseReleased(MouseEvent e) {

		final Object button = e.getSource();

		if (button == null) {
			return;
		}

		if (button.equals(buttonStart)) {
			startGameManager.startAgain();
			hideStartButton();
			showYesNoButtons();
		} else if (button.equals(buttonYes)) {
			safeChangeButtonYes(true);
		} else if (button.equals(buttonNo)) {
			safeChangeButtonNo(true);
		} else if (button.equals(buttonAgain)) {
			hidePlayAgainButton();
			showStartButtonAndStartText();
		} else if (button.equals(buttonInputOk)) {
			hideNewFoodInputAndOkButton();
			showNewFoodInputTipAndOkButton();
		} else if (button.equals(buttonInputTipOk)) {
			startGameManager.saveNewFoodBeforeStart(getNewFoodName(), getNewFoodTipName());
			hideNewFoodInputTipAndOkButton();
			showStartButtonAndStartText();
		}
	}

	@Override public void mousePressed(MouseEvent e) {
	}

	@Override public void mouseClicked(MouseEvent e) {
	}

	@Override public void mouseEntered(MouseEvent e) {
	}

	@Override public void mouseExited(MouseEvent e) {
	}

	private void safeChangeButtonYes(boolean value) {
		synchronized (buttonYes) {
			this.buttonYesPressed = value;
		}
	}

	private void safeChangeButtonNo(boolean value) {
		synchronized (buttonNo) {
			this.buttonNoPressed = value;
		}
	}

	private boolean safeGetButtonYes() {
		synchronized (buttonYes) {
			return this.buttonYesPressed;
		}
	}

	private boolean safeGetButtonNo() {
		synchronized (buttonNo) {
			return this.buttonNoPressed;
		}
	}

	public void showScreen() {
		this.pack();
		this.setVisible(true);
	}

	public void showTipOrGuess(String tipOrGuess) {
		labelQuestion.setText("O prato que você pensou é " + tipOrGuess + "?");
	}

	private void showStartButtonAndStartText() {
		buttonStart.setVisible(true);
		labelQuestion.setText("Pense em um prato que gosta.");
		this.pack();
	}

	private void hideStartButton() {
		buttonStart.setVisible(false);
		this.pack();
	}

	private void showPlayAgainButton() {
		buttonAgain.setVisible(true);
		this.pack();
	}

	private void hidePlayAgainButton() {
		buttonAgain.setVisible(false);
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

	private void showNewFoodInputAndOkButton() {
		inputNewFood.setVisible(true);
		buttonInputOk.setVisible(true);
		labelQuestion.setText("Desisto. Qual prato você pensou?");
		this.pack();
	}

	private void hideNewFoodInputAndOkButton() {
		inputNewFood.setVisible(false);
		buttonInputOk.setVisible(false);
		this.pack();
	}

	private void showNewFoodInputTipAndOkButton() {

		inputNewFoodTip.setVisible(true);
		buttonInputTipOk.setVisible(true);

		String newFoodName = inputNewFood.getText();
		labelQuestion.setText(newFoodName + " é ________, mas " + parentFoodName + " não.");

		this.pack();
	}

	private void hideNewFoodInputTipAndOkButton() {
		inputNewFoodTip.setVisible(false);
		buttonInputTipOk.setVisible(false);
		this.pack();
	}

	public void showFinalAnswer(String finalAnswer) {
		this.labelQuestion.setText("Acertei de novo! A comida é " + finalAnswer);

		showPlayAgainButton();
		hideYesNoButtons();
	}

	public void setParentFoodName(String parentFoodName) {
		this.parentFoodName = parentFoodName;
	}
}