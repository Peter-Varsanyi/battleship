package com.epam.torpedo;

import com.epam.torpedo.network.TorpedoClient;
import com.epam.torpedo.network.TorpedoServer;

public class Program {

	public static void main(String[] args) {

		 new Thread(new TorpedoServer(6666, 10, 10)).start();
		 new Thread(new TorpedoClient("localhost", 6666)).start();

//		ShipPossibilityMatrix matrix = new ShipPossibilityMatrix();
//		int[][] matrix2 = matrix.getMatrix();
	}

}
