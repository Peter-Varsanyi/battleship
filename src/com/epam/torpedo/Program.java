package com.epam.torpedo;

import java.io.IOException;

import com.epam.torpedo.network.TorpedoClient;
import com.epam.torpedo.network.TorpedoServer;

public class Program {

	public static void main(String[] args) throws InterruptedException, IOException {

		// ShipGuesser shipguesser = new ShipGuesser(30, 30);
		// shipguesser.guessShip();

		 new Thread(new TorpedoServer(6666, 20, 20)).start();
		// // Thread.sleep(10);
		 new Thread(new TorpedoClient("localhost", 6666)).start();

		ShipPossibilityMatrix matrix = new ShipPossibilityMatrix();
		int[][] matrix2 = matrix.getMatrix();
	}

}
