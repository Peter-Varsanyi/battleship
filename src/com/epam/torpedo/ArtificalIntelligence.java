package com.epam.torpedo;

import java.awt.Point;
import java.io.IOException;
import java.util.Random;

import com.epam.torpedo.commands.AttackCommand;
import com.epam.torpedo.commands.Command;
import com.epam.torpedo.commands.FireCommand;
import com.epam.torpedo.commands.GameoverCommand;

public class ArtificalIntelligence {
	private static final String LOST = "LOST";

	private static final String FIRE = "FIRE";

	private Board board;

	private int boardX;
	private int boardY;

	String type;
	int[][] alreadyUsedPoints;

	public ArtificalIntelligence(Board board, String type) throws IOException {
		this.board = board;
		boardX = board.getMaxx();
		boardY = board.getMaxY();
		alreadyUsedPoints = new int[boardX][boardY];
		this.type = type;
	}

	public Command handleCommand(String command) {
		Command cmd = null;

		if (command.startsWith(FIRE)) {
			cmd = handleFire(command);
		} else if (command.startsWith(LOST)) {
			cmd = new GameoverCommand();
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
		} while (alreadyUsedPoints[x][y] != 0);

		alreadyUsedPoints[x][y] = 1;
		printShotTable();
		return new AttackCommand(x, y);
	}

	private void printShotTable() {
		for (int i = 0; i < boardX; i++) {
			for (int j = 0; j < boardY; j++) {
				System.out.print(alreadyUsedPoints[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

}
