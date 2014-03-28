package com.epam.torpedo;

import java.awt.Point;
import java.util.Random;

import com.epam.torpedo.commands.AttackCommand;
import com.epam.torpedo.commands.Command;
import com.epam.torpedo.commands.FireCommand;
import com.epam.torpedo.commands.GameoverCommand;

public class ArtificalIntelligence {
	private Board board;

	private int boardX;
	private int boardY;

	String type;
	Integer[][] alreadyUsedPoints;

	public ArtificalIntelligence(Board board, String type) {
		this.board = board;
		boardX = board.getMaxx();
		boardY = board.getMaxY();
		alreadyUsedPoints = new Integer[boardX][boardY];
		this.type = type;
	}

	public Command handleCommand(String command) {
		Command cmd = null;
		
		if (type.equals("server")) {
			System.out.println("["+type +"] Received command: " + command);	
		} else {
			System.err.println("["+type +"] Received command: " + command);
		}
		if (command.startsWith("FIRE")) {
			cmd = handleFire(command);
		} else if (command.startsWith("LOST")) {
			cmd = new GameoverCommand();
		} else if (command.startsWith("HIT")) {
//			System.out.println("a");
//			cmd = new  
		}
		return cmd;
	}

	private Command handleFire(String command) {
		String[] data = command.split(" |,");
		int x = Integer.parseInt(data[1]);
		int y = Integer.parseInt(data[2]);

		Point point = new Point(x, y);
		return new FireCommand(board, point);
	}

	public boolean isGameOver() {
		return board.isGameOver();
	}

	public Command getAttackingCommand() {
		int x, y;
		Random rng = new Random();
		do {
			x = rng.nextInt(boardX);
			y = rng.nextInt(boardY);
		} while (alreadyUsedPoints[x][y] != null);
		alreadyUsedPoints[x][y] = 1;
		return new AttackCommand(x, y);
	}
}
