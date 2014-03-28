package com.epam.torpedo;

import java.awt.Point;
import java.util.Random;

import com.epam.torpedo.enums.HitEnum;
import com.epam.torpedo.shipreader.ShipReader;

public class ShipGuesser {

	private int[][] randomNumbers;

	private Integer maxX;
	private Integer maxY;

	public ShipGuesser(Integer maxX, Integer maxY) {
		this.maxX = maxX;
		this.maxY = maxY;
		randomNumbers = new int[maxX][maxY];
	}

	public Point getRandomUniquePointOnTable() {
		int x, y;
		Random rng = new Random();
		do {
			x = rng.nextInt(maxX);
			y = rng.nextInt(maxY);
		} while (randomNumbers[x][y] != 0);
		randomNumbers[x][y] = 1;
		return new Point(x, y);
	}

	public void guessShip() {
		int sinkCount = 0;
		int guessCount = 0;

		ShipReader shipReader = new ShipReader();
		shipReader.getAllShips();

		Board board = new Board(maxX, maxY);
		do {
			Point point = getRandomUniquePointOnTable();
			HitEnum hit = board.isHit(point);
			if (hit == HitEnum.SUNK) {
				sinkCount++;
				System.out.println("sink");
				board.printBoard();

			}
			guessCount++;
		} while (sinkCount < 15);
		System.out.println("Guesscount: " + guessCount + " Hitcount: " + sinkCount);
		board.printBoard();
	}
}
