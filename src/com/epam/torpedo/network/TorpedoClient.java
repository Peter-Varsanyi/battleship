package com.epam.torpedo.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import com.epam.torpedo.ArtificalIntelligence;
import com.epam.torpedo.Board;
import com.epam.torpedo.commands.Command;
import com.epam.torpedo.commands.GameoverCommand;

public class TorpedoClient implements Runnable {
	private int port;
	private String hostName;
	private Socket socket;
	private OutputStream out;

	private Board board;
	private BufferedReader in;
	private ArtificalIntelligence ai;

	public TorpedoClient(String hostName, int port) {
		this.hostName = hostName;
		this.port = port;
	}

	// private void commandReceived(String line) {
	// if (line.startsWith("INIT")) {
	// System.out.println("Init parameters received");
	// } else if (line.startsWith("FIRE")) {
	// System.out.println("SHOTS FIRED");
	// } else if (line.startsWith("MISS")) {
	// System.out.println("MISS");
	// } else if (line.startsWith("HIT")) {
	// System.out.println("hit");
	// } else if (line.startsWith("SUNK")) {
	// System.out.println("sunk");
	// } else if (line.startsWith("LOST")) {
	// System.out.println("IWIN");
	// }
	//
	// }

	// public void executeCommand(String command) {
	// try {
	// out.write(command.getBytes());
	// out.flush();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	boolean isStopped = false;

	public void initStreams() throws IOException {
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = socket.getOutputStream();

	}

	public void returnResponse(Command data) throws IOException {
		String result = data.getResult();
		System.out.println("[client] wrote: " + result);
		out.write((result + "\n").getBytes());
		out.flush();
		if (data instanceof GameoverCommand) {
			in.close();
			out.close();
			isStopped = true;
		}
	}

	@Override
	public void run() {
		try {
			socket = new Socket(hostName, port);
			initStreams();
			readBoardParametersAndInit();

			System.out.println("Client ready to serve");
			
			boolean firstRound = true;
			
			while (!isStopped) {
				if (firstRound) attackEnemy();
				
				Command command = null;
				String line = in.readLine();
				if (!ai.isGameOver()) {
					command = ai.handleCommand(line);
				} else {
					isStopped = true;
				}
				if (command != null) {
					returnResponse(command);
					attackEnemy();
				}
//				Thread.sleep(500);

//				Command command;
//
//				if (canAttack) {
//					command = ai.getAttackingCommand();
//					canAttack = false;
//				} else {
//					String line = in.readLine();
//					if (!ai.isGameOver()) {
//						command = ai.handleCommand(line);
//					} else {
//						command = new GameoverCommand();
//					}
//					canAttack = true;
//				}
//				if (command instanceof GameoverCommand) isStopped = true;
//				if (command != null)
//					returnResponse(command);

				// System.out.println("Command received: " + line);
			}

			// while (!isStopped) {
			// System.out.println("Connecting on port: " + port);
			//
			// executeCommand("FIRE 1,1\n");
			// String line = in.readLine();
			// System.out.println("Read from the server: " + line);
			// }
			// while(true) {
			// if (in.ready()) {
			// String line = in.readLine();
			// commandReceived(line);
			// }
			// }

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void readBoardParametersAndInit() throws IOException {
		String tableSize = in.readLine();
		String[] data = tableSize.split(" |,");
		int tableX = Integer.parseInt(data[1]);
		int tableY = Integer.parseInt(data[2]);

		board = new Board(tableX, tableY);
		ai = new ArtificalIntelligence(board,"client");
	}
	private void attackEnemy() throws IOException {
		Command command = ai.getAttackingCommand();
		returnResponse(command);
	}

}
