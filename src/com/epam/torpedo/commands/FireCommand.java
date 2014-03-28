package com.epam.torpedo.commands;

import java.awt.Point;

import com.epam.torpedo.Board;
import com.epam.torpedo.enums.HitEnum;

public class FireCommand implements Command {

	private Board board;
	private Point point;

	public FireCommand(Board board, Point point) {
		this.board = board;
		this.point = point;

	}

	@Override
	public String getResult() {
		HitEnum hit = board.isHit(point);
		return hit.toString();
	}

}
