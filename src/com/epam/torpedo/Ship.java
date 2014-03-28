package com.epam.torpedo;

import java.util.List;

import com.epam.torpedo.shipreader.Point;

public class Ship {
	public List<Point> coordinates;
	public int id;
	public int damage;

	public Ship(List<Point> coordinates, int id) {
		this.coordinates = coordinates;
		this.id = id;

	}

	public List<Point> getCoordinates() {
		return coordinates;
	}

	public int getId() {
		return id;
	}

	public int getDamage() {
		return damage;
	}

}
