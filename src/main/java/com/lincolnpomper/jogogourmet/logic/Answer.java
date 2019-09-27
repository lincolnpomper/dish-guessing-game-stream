package com.lincolnpomper.jogogourmet.logic;

public class Answer {

	private boolean yes;
	private boolean no;

	public Answer(boolean yes, boolean no) {

		this.yes = yes;
		this.no = no;
	}

	public boolean isYes() {
		return yes;
	}

	public boolean isNo() {
		return no;
	}
}
