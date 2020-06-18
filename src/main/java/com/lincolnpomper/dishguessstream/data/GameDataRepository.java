package com.lincolnpomper.dishguessstream.data;

import com.lincolnpomper.dishguessstream.logic.Food;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class GameDataRepository {

	private static GameDataRepository me;
	private Set<Food> foodSet;

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
		System.out.println("Log: saved " + food);
	}

	public Set<Food> findAll() {
		return foodSet;
	}

	public Optional<Food> findByName(String name) {
		return foodSet.stream().filter(f -> f.getName().equals(name)).findFirst();
	}
}
