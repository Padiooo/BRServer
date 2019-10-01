package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerConnection extends Thread {

	private Model model;
	private Controller controller;

	private ArrayList<PlayerConnection> connectionList = new ArrayList<PlayerConnection>();

	int[] ids_player = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

	private ServerSocket listener = null;

	public ServerConnection() {
		model = new Model();
		controller = new Controller(model);
		controller.start();
		ManagerConnection manager = new ManagerConnection(model, connectionList, ids_player);
		manager.start();
	}

	@Override
	public void run() {

			try {
				listener = new ServerSocket(9999);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			do {
				int id_player = isConnectionAvailable();
				if (id_player != -1) {
					Socket socket = null;
					try {
						socket = listener.accept();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (socket != null) {
						PlayerConnection playerConnection = new PlayerConnection(socket, id_player, model, controller);
						connectionList.add(playerConnection);
						ids_player[id_player] = -1;
						playerConnection.start();
						System.out.println("Player " + id_player + " added");
					}
				}
				
				try {
					Thread.sleep(Info.TICK_RATE);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			} while (true);

	}

	public int isConnectionAvailable() {
		for (int i = 0; i < ids_player.length; i++) {
			if (ids_player[i] > 0) {
				return i;
			}
		}
		return -1;
	}

}
