package com.lincolnpomper.jogogourmet.data;

import com.lincolnpomper.jogogourmet.logic.Food;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class GameDataRepository {

	private static GameDataRepository me;
	Set<Food> foodSet;

	private GameDataRepository() {
		foodSet = new HashSet<>();
	}

	public static GameDataRepository getInstance() {
		if (me == null) {
			me = new GameDataRepository();
		}
		return me;
	}

	public void save(Food food) {
		foodSet.add(food);
	}

	public Set<Food> findAll() {
		return foodSet;
	}

	public Optional<Food> findByName(String name) {
		return foodSet.stream().filter(f -> f.getName().equals(name)).findFirst();
	}
}
