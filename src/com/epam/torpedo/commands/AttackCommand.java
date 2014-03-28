package com.epam.torpedo.commands;


public class AttackCommand implements Command {

	private int x;
	private int y;

	public AttackCommand(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String getResult() {
		return String.format("FIRE %d,%d", x, y);
	}
}
