package com.lincolnpomper.jogogourmet.logic;

public class Answer {

	private String newFood;
	private boolean yes;
	private boolean no;

	public Answer(boolean yes, boolean no, String newFood) {

		this.yes = yes;
		this.no = no;
		this.newFood = newFood;
	}

	public String getNewFood() {
		return newFood;
	}

	public boolean isYes() {
		return yes;
	}

	public boolean isNo() {
		return no;
	}

	@Override public String toString() {
		return "Answer{" + "newFood='" + newFood + '\'' + ", yes=" + yes + ", no=" + no + '}';
	}
}
