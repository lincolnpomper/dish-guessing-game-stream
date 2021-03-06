package com.lincolnpomper.dishguessstream.logic;

import com.lincolnpomper.dishguessstream.file.GameFileData;

import java.util.Objects;

public class Food {

	private String name;
	private String tip;
	private Food parent;

	public Food(GameFileData data) {

		this.name = data.name;
		this.tip = data.values[0];
	}

	public Food(String name, String tip, Food parent) {
		this.name = name;
		this.tip = tip;
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public boolean hasParent() {
		return parent != null;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTip() {
		return tip;
	}

	public boolean hasTip() {
		return tip != null;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public Food getParent() {
		return parent;
	}

	boolean noParent() {
		return parent == null;
	}

	public void setParent(Food parent) {
		this.parent = parent;
	}

	@Override public boolean equals(Object o) {

		if (this == o)
			return true;

		Food food = (Food) o;

		return Objects.equals(name, food.name);
	}

	@Override public int hashCode() {
		return name != null ? name.hashCode() : 0;
	}

	@Override public String toString() {
		return "Food{" + "name='" + name + '\'' + ", tip='" + tip + '\'' + ", parent=" + parent + '}';
	}
}
