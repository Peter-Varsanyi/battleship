package com.epam.torpedo.enums;

import java.util.Random;

public enum DirectionEnum {
	HORIZONTAL, VERTICAL;
	public static DirectionEnum getRandomDirection() {
		Random randomGenerator = new Random();
		int randomNumber = randomGenerator.nextInt(values().length);
		return values()[randomNumber];
	}
}
