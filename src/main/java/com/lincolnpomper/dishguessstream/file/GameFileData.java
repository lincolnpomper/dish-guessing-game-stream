package com.lincolnpomper.dishguessstream.file;

import java.util.Arrays;

public class GameFileData {

	public String name;
	public String[] values;

	@Override
	public String toString() {
		return "GameFileData [name=" + name + ", values=" + Arrays.toString(values) + "]";
	}
}