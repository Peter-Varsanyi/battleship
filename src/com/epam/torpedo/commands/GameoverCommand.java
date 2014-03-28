package com.epam.torpedo.commands;


public class GameoverCommand implements Command {

	@Override
	public String getResult() {
		return "LOST";
	}

}
