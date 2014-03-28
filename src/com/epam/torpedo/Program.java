package com.epam.torpedo;

import com.epam.torpedo.network.TorpedoClient;
import com.epam.torpedo.network.TorpedoServer;

public class Program {

	public static void main(String[] args) throws InterruptedException {

//		ShipGuesser shipguesser = new ShipGuesser(30, 30);
//		shipguesser.guessShip();
		
		new Thread(new TorpedoServer(6666,5)).start();
//		Thread.sleep(10);
		new Thread(new TorpedoClient("localhost", 6666)).start();
	}

}
