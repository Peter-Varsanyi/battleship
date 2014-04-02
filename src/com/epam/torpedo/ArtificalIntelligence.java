package com.epam.torpedo;

import java.awt.Point;
import java.io.IOException;
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
	int[][] alreadyUsedPoints;

	private int[][] matrix;

	public ArtificalIntelligence(Board board, String type) throws IOException {
		this.board = board;
		boardX = board.getMaxx();
		boardY = board.getMaxY();
		alreadyUsedPoints = new int[boardX][boardY];
		this.type = type;

		ShipPossibilityMatrix matrixGenerator;
		matrixGenerator = new ShipPossibilityMatrix();
		matrix = matrixGenerator.getMatrix();

	}

	public Command handleCommand(String command) {
		Command cmd = null;

		if (type.equals("server")) {
			System.out.println("[" + type + "] Received command: " + command);
		} else {
			System.err.println("[" + type + "] Received command: " + command);
		}
		if (command.startsWith("FIRE")) {
			cmd = handleFire(command);
		} else if (command.startsWith("LOST")) {
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
//
//	private Point getBestHitBasedOnMatrix(Point lastHit) {
//		if (lastHit == null)
//			return new Point(0, 0);
//		int topleftx = lastHit.x - 1;
//		int toplefty = lastHit.y - 1;
//
//		int maxValue = 0;
//		int bestX = 0;
//		int bestY = 0;
//
//		System.out.println(lastHit);
//
//		for (int i = 0; i < 3; i++) {
//			for (int j = 0; j < 3; j++) {
//				// System.out.println("Coords: " + (lastx - i - 1) + " y: " + (lasty - j - 1));
//				int x = topleftx + i;
//				int y = toplefty + i;
//				// System.out.println("aa " + x + " y" + y);
//				if (isValidSpotOnTable(x, y) && alreadyUsedPoints[x][y] == 0 && matrix[i][j] > maxValue) {
//					maxValue = matrix[i][j];
//					bestX = x;
//					bestY = y;
//				}
//			}
//		}
//
//		return new Point(bestX, bestY);
//	}

//	/**
//	 * @param x
//	 * @param y
//	 * @return
//	 */
//	private boolean isValidSpotOnTable(int x, int y) {
//		return x > 0 && y > 0 && x < this.boardX && y < this.boardY;
//	}

	public Command getAttackingCommand() {

		int x, y;

		Random rng = new Random();

		// Point nextHit = getBestHitBasedOnMatrix(lastHit);
		// if (nextHit.equals(new Point(0, 0))) {
		do {
			x = rng.nextInt(boardX);
			y = rng.nextInt(boardY);
		} while (alreadyUsedPoints[x][y] != 0);
		// nextHit = new Point(x, y);
		// }

		alreadyUsedPoints[x][y] = 1;
		// lastHit = nextHit;
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
