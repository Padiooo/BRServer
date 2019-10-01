package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class PlayerConnection extends Thread {

	private Socket socket;
	private BufferedReader is;
	private BufferedWriter os;

	private String delims = "[ ]+";

	private Player player;
	private Model model;

	private int id_player;

	private boolean isConnected = true;
	public boolean exit = false;

	public PlayerConnection() {
	}

	public PlayerConnection(Socket socketOfServer, int id, Model model, Controller controller) {

		this.id_player = id;
		this.player = new Player(id);
		this.model = model;

		try {
			socket = socketOfServer;
			is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
			os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addPlayer(player);
		player.addObserver(controller);

		sendMessage("SETUP " + id + " " + Info.TICK_RATE + " " + Info.WINDOW_X + " " + Info.WINDOW_Y + " "
				+ Info.PLAYER_SIZE + " " + Info.BALL_SIZE);

	}

	@Override
	public void run() {
		while (player.isAlive() && isConnected) {
			try {
				update(is.readLine());
			} catch (IOException e) {
				e.printStackTrace();
				endConnection();
			}
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		sendMessage(player.getDeathRecap());
		model.removePlayer(id_player);
		player = null;

		while (!exit) {

		}
	}

	private void update(String s) {
		String[] data = s.split(delims);
		switch (data[0]) {
		case "move":
			move(Integer.valueOf(data[1]), Integer.valueOf(data[2]));
			break;
		case "shoot":
			shoot(Integer.valueOf(data[1]), Integer.valueOf(data[2]), Integer.valueOf(data[3]));
			break;
		case "reload":
			reload(Integer.valueOf(data[1]));
			break;
		default:
			System.out.println(s);
			break;
		}
	}

	private void shoot(int id_ball, int x, int y) {
		player.shoot(id_ball, x, y);
	}

	private void reload(int id_ball) {
		player.reload(id_ball);
	}

	private void move(int dx, int dy) {
		player.move(dx, dy);
	}

	public void sendMessage(String msg) {
		if (!exit) {
			try {
				os.write(msg);
				os.newLine();
				os.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void endConnection() {
		try {
			os.close();
			is.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.removePlayer(id_player);
		isConnected = false;
		exit = true;
	}

	// ------------------------------------------------------

	public int getIdPlayer() {
		return this.id_player;
	}
}
