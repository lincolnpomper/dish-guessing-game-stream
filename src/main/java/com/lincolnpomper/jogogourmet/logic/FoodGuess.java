package com.lincolnpomper.jogogourmet.logic;

class FoodGuess extends Food {

	private boolean tip;
	private boolean current;
	private boolean visited;

	FoodGuess(Food food) {
		super(food.getName(), food.getTip(), food.getParent());

		this.visited = false;
		this.current = false;
	}

	String getGuess() {
		return isTip() ? this.getTip() : this.getName();
	}

	boolean isTip() {
		return tip;
	}

	void setTip(boolean tip) {
		this.tip = tip;
	}

	boolean isCurrent() {
		return current;
	}

	void setCurrent(boolean current) {
		this.current = current;
	}

	boolean isVisited() {
		return visited;
	}

	void setVisited(boolean visited) {
		this.visited = visited;
	}
}
