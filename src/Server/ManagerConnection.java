package Server;

import java.util.ArrayList;

public class ManagerConnection extends Thread {

	private Model model;

	private ArrayList<PlayerConnection> removedConnection = new ArrayList<PlayerConnection>();
	private ArrayList<PlayerConnection> connectionList;

	int[] ids_player;
	
	public ManagerConnection(Model model, ArrayList<PlayerConnection> connectionList, int[] ids_players) {

		this.model = model;
		this.connectionList = connectionList;
		this.ids_player = ids_players;
	}

	@Override
	public void run() {
		while (true) {
			try {
				String update = model.getUpdate();
				Thread.sleep(Info.TICK_RATE);
				for (PlayerConnection playerConnection : connectionList) {
					if (!playerConnection.exit) {
						playerConnection.sendMessage(update);
					} else {
						removedConnection.add(playerConnection);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			removeConnections();
		}
	}

	private void removeConnections() {
		for (PlayerConnection removedConnection : removedConnection) {
			connectionList.remove(removedConnection);
			removedConnection.endConnection();
			ids_player[removedConnection.getIdPlayer()] = 1;
			System.out.println("Player " + removedConnection.getIdPlayer() + " removed");
		}
		removedConnection.clear();
	}

}
