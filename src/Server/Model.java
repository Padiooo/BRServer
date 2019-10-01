package Server;

import java.util.ArrayList;
import java.util.Observable;

public class Model extends Observable {

	public int size_ball = 10;
	public int size_player = 20;

	private ArrayList<Player> playerList = new ArrayList<Player>();

	public String getUpdate() {
		StringBuilder sb = new StringBuilder();

		for (Player player : playerList) {
			sb.append(player.toString());
		}
		return sb.toString();
	}

	public void removePlayer(int id_player) {
		int id = -1;
		for(int i = 0; i < playerList.size(); i++) {
			if(playerList.get(i).getId() == id_player) id = i;
		}
		if(id != -1) {
			playerList.remove(id);
		}
	}

	public void addPlayer(Player player) {
		playerList.add(player);
	}

	public Player getPlayer(int id_player) {
		return playerList.get(id_player);
	}

	public ArrayList<Player> getAllPlayer() {
		return playerList;
	}

	public boolean arrayIsNull() {
		boolean a = (playerList.size() < 2) ? true : false;
		if(a) {
			//System.out.println(a);
		}
		return a;
	}

}
