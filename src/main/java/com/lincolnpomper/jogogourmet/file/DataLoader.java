package com.lincolnpomper.jogogourmet.file;

import com.lincolnpomper.jogogourmet.data.GameDataRepository;
import com.lincolnpomper.jogogourmet.logic.Food;

import java.util.List;
import java.util.Optional;

public class DataLoader {

	private GameDataRepository gameDataRepository;

	public DataLoader(GameDataRepository gameDataRepository) {
		this.gameDataRepository = gameDataRepository;
	}

	public void setupData() {

		List<GameFileData> foodsFileData = GameFileReader.getInstance().getList(GameFileReader.ResourceType.Food);

		foodsFileData.forEach(data -> gameDataRepository.save(new Food(data)));

		foodsFileData.forEach(data -> {
			if (data.values[1] != null) {
				final Optional<Food> food = gameDataRepository.findByName(data.name);
				final Optional<Food> parent = gameDataRepository.findByName(data.values[1]);
				if (food.isPresent() && parent.isPresent()) {
					food.get().setParent(parent.get());
				}
			}
		});
	}

}