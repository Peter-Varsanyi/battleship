package com.epam.torpedo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.epam.torpedo.enums.HitEnum;
import com.epam.torpedo.shipreader.Point;
import com.epam.torpedo.shipreader.ShipDetail;
import com.epam.torpedo.shipreader.ShipReader;

public class Board {
	private int maxX;
	private int maxY;

	private int[][] board;

	private Map<Integer, Ship> ships = new HashMap<>();
	private int nextShipId = 0;

	public Board(Integer maxX, Integer maxY) {
		this.maxX = maxX;
		this.maxY = maxY;

		board = new int[maxX][maxY];

		List<ShipDetail> details = new ShipReader().getAllShips();
		for (ShipDetail sd : details) {
			addBatchShip(sd);
		}
	}

	private void addBatchShip(ShipDetail sd) {
		for (int i = 0; i < sd.getCount(); i++) {
			Ship ship = createShipFromDetail(sd);
			addShip(ship);
		}
	}

	private Ship createShipOnRandomPoint(ShipDetail shipDetail) {
		boolean validPoint = false;
		List<Point> coordinates;
		coordinates = createNewShipCoordinatesFromDetail(shipDetail);
		Random randomGenerator = new Random();
		do {

			int x = randomGenerator.nextInt(maxX);
			int y = randomGenerator.nextInt(maxY);

			coordinates = createNewShipCoordinatesFromDetail(shipDetail);

			for (Point p : coordinates) {
				p.setX(p.getX() + x);
				p.setY(p.getY() + y);
			}

			validPoint = isShipPlaceable(coordinates);
		} while (!validPoint);
		nextShipId++;
		return new Ship(coordinates, nextShipId);
	}

	private List<Point> createNewShipCoordinatesFromDetail(ShipDetail shipDetail) {
		List<Point> coordinates = new ArrayList<>();

		int[][] points = shipDetail.getRelativePoints();
		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < points[i].length; j++) {
				if (points[i][j] > 0) {
					coordinates.add(new Point(j, i));
				}
			}
		}

		return coordinates;
	}

	private boolean isShipPlaceable(List<Point> coordinates) {
		boolean validPosition = true;
		for (Point p : coordinates) {
			if (p.getX() >= maxX || p.getY() >= maxY)
				return false;
			if (board[p.getX()][p.getY()] > 0) {
				validPosition = false;
			}
		}
		return validPosition;
	}

	private void addShip(Ship ship) {
		ships.put(nextShipId, ship);
		putShipInTheMatrix(ship);
	}

	private Ship createShipFromDetail(ShipDetail shipDetail) {
		return createShipOnRandomPoint(shipDetail);
	}

	private void putShipInTheMatrix(Ship ship) {
		List<Point> coordinates = ship.getCoordinates();
		for (Point p : coordinates) {
			board[p.getX()][p.getY()] = ship.getId();
		}
	}

	public void printBoard() {
		for (int i = 0; i < maxX; i++) {
			for (int j = 0; j < maxY; j++) {
				if (board[i][j] > 0) {
					System.out.print(String.format("%2d", board[i][j]));
				} else if (board[i][j] == -1) {
					System.out.print(" x");
				} else {
					System.out.print(" .");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	public HitEnum isHit(java.awt.Point point) {
		if (board[point.x][point.y] > 0) {
			int shipId = board[point.x][point.y];
			Ship ship = ships.get(shipId);
			ship.damage++;
			board[point.x][point.y] = -1;
			return ship.isSunk() ? HitEnum.SUNK : HitEnum.HIT;
		}
		return HitEnum.MISS;
	}

	public int getMaxx() {
		return maxX;
	}

	public int getMaxY() {
		return maxY;
	}

	public boolean isGameOver() {
		for (int i = 0; i < maxX; i++) {
			for (int j = 0; j < maxY; j++) {
				if (board[i][j] > 0)
					return false;
			}
		}
		return true;
	}
}
