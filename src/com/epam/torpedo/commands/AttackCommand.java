package com.epam.torpedo.commands;


public class AttackCommand implements Command {

	private static final String FIRE_FORMAT = "FIRE %d,%d";
	private int x;
	private int y;

	public AttackCommand(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String getResult() {
		return String.format(FIRE_FORMAT, x, y);
	}
}
