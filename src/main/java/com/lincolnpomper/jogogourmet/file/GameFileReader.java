package com.lincolnpomper.jogogourmet.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GameFileReader {

	private static String basePath;
	private static final String SEPARATOR = ",";
	private static GameFileReader me;

	private Map<ResourceType, List<GameFileData>> resources = new HashMap<>();
	private BufferedReader reader = null;

	private GameFileReader() {
	}

	static GameFileReader getInstance(String basePath) {

		GameFileReader.basePath = basePath;

		if (me == null) {
			me = new GameFileReader();
			me.parseCSV();
		}
		return me;
	}

	private void parseCSV() {

		for (ResourceType type : ResourceType.values()) {
			resources.put(type, parseCSVFromFile(type.getValue()));
		}
	}

	List<GameFileData> getList(ResourceType key) {
		return resources.get(key);
	}

	private List<GameFileData> parseCSVFromFile(String fileName) {

		List<GameFileData> list = new ArrayList<>();

		try {

			reader = new BufferedReader(new FileReader(fileName));
			String line;

			// skip header
			reader.readLine();

			while ((line = reader.readLine()) != null) {

				String[] array = line.split(SEPARATOR, -1);

				for (int i = 0; i < array.length; i++) {
					if ("".equals(array[i])) {
						array[i] = null;
					}
				}

				String[] values = new String[array.length - 1];

				String name = array[0];
				System.arraycopy(array, 1, values, 0, array.length - 1);

				GameFileData item = new GameFileData();
				item.name = name;
				item.values = values;

				list.add(item);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return list;
	}

	public enum ResourceType {

		Food("foods.csv");

		private String value;

		ResourceType(String value) {
			this.value = basePath + value;
		}

		String getValue() {
			return value;
		}
	}
}