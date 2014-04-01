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

public class TorpedoServerWorker implements Runnable {

	private boolean isStopped = false;
	private Socket socket;
	private int boardSize;
	private BufferedReader in;
	private OutputStream out;
	private ArtificalIntelligence ai;

	public TorpedoServerWorker(Socket clientSocket, int boardSize) {
		this.socket = clientSocket;
		this.boardSize = boardSize;

		Board board = new Board(boardSize, boardSize);
		try {
			ai = new ArtificalIntelligence(board, "server");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void printWelcomeMessage() throws IOException {
		out.write(String.format("INIT %d,%d\n", boardSize, boardSize).getBytes());
	}

	public void initStreams() throws IOException {
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = socket.getOutputStream();

	}

	public void returnResponse(Command data) throws IOException {
		String result = data.getResult();
		System.out.println("[server] " + Thread.currentThread().getId() + " wrote: " + result);
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
		System.out.println("A client connected" + Thread.currentThread().getId());

		try {
			initStreams();
			printWelcomeMessage();

			while (!isStopped) {
				Command command = null;
				String line = in.readLine();
				if (!ai.isGameOver()) {
					command = ai.handleCommand(line);
				} else {
					returnResponse(new GameoverCommand());
					isStopped = true;
				}

				if (command != null && command instanceof GameoverCommand == false) {
					returnResponse(command);
					attackEnemy();
				}
				// command = new GameoverCommand();

				// if (canAttack) {
				// command = ai.getAttackingCommand();
				// canAttack = false;
				// } else {
				// String line = in.readLine();
				// if (!ai.isGameOver()) {
				// command = ai.handleCommand(line);
				// } else {
				// command = new GameoverCommand();
				// }
				// canAttack = true;
				// }

				// Thread.sleep(500);

				// System.out.println("Command received: " + line);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @throws IOException
	 */
	private void attackEnemy() throws IOException {
		Command command = ai.getAttackingCommand();
		returnResponse(command);
	}

	// TODO Auto-generated method stub

}
