package com.epam.torpedo.shipreader;

public class ShipDetail {

	private int count;
	private int[][] relativePoints;

	public ShipDetail(int count, String... lines) {
		this.count = count;
		relativePoints = createPointsFromLines(lines);
	}

	private int[][] createPointsFromLines(String[] lines) {
		int[][] points = new int[4][4];
		int j = 0;
		for (String s : lines) {
			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i) == 'x') {
					points[i][j] = 1;
				} else {
					points[i][j] = 0;
				}
			}
			j++;
		}
		return points;
	}

	public int[][] getRelativePoints() {
		return relativePoints;
	}

	public int getCount() {
		return count;
	}
}
