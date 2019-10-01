package Server;

import java.util.Observable;
import java.util.Observer;

public class Controller extends Thread implements Observer {

	private Model model;

	public Controller(Model model) {
		this.model = model;
	}

	@Override
	public void run() {
		while (model.arrayIsNull()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		while (true) {
			int[][] players_coord = getPlayersCoord();
			int[][][] balls_coord = getBallsCoord();

			checkHitBox(players_coord, balls_coord);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Player) {
			if (isPlayerOutBound((Player) arg)) {
				((Player) arg).rollBack();
			}
		}
	}

	public void checkHitBox(int[][] players_coord, int balls_coord[][][]) {
		for (int id_player = 0; id_player < model.getAllPlayer().size(); id_player++) {
			for (int id_ball = 0; id_ball < balls_coord.length; id_ball++) {
				for (int k = 0; k < balls_coord[0].length; k++) {
					if (id_player != id_ball && balls_coord[id_ball][k][0] != -99) {
						//@formatter:off
						if (checkDistance(players_coord[id_player][0], players_coord[id_player][1], balls_coord[id_ball][k][0], balls_coord[id_ball][k][1])) {
							playerDie(id_player, id_ball);
						}
						//@formatter:on
					} else {
						break;
					}
				}
			}
		}
	}

	public void playerDie(int id_player_killed, int id_player_killer) {
		System.out.println(id_player_killed + " " + id_player_killer);
		model.getPlayer(id_player_killed).die(id_player_killer);
		System.out.println("Player id " + id_player_killed + " died from player id " + id_player_killer + " ball");
	}

	// if distance > pb_distanc => player touched (true)
	public boolean checkDistance(int px, int py, int bx, int by) {

		if (bx == -99 || by == -99) {
			return false;
		}

		int distance = Info.BALL_SIZE / 2 + Info.PLAYER_SIZE / 2;
		int pb_distance = (int) Math.sqrt(Math.pow(px - bx, 2) + Math.pow(py - by, 2));

		return (distance > pb_distance) ? true : false;
	}

	public int[][][] getBallsCoord() {
		int row = model.getAllPlayer().size();
		int[][][] ballsCoord = new int[row][3][2];

		for (int i = 0; i < model.getAllPlayer().size(); i++) {
			for (int j = 0; j < 3; j++) {
				BallShoot ball = model.getPlayer(i).getBalls()[j];
				if (ball == null) {
					ballsCoord[i][j][0] = -99;
					ballsCoord[i][j][1] = -99;
				} else {
					ballsCoord[i][j][0] = ball.getX();
					ballsCoord[i][j][1] = ball.getY();
				}
			}
		}
		return ballsCoord;
	}

	public int[][] getPlayersCoord() {

		int row = model.getAllPlayer().size();
		int[][] playersCoord = new int[row][2];

		for (int i = 0; i < row; i++) {
			playersCoord[i][0] = model.getPlayer(i).getX();
			playersCoord[i][1] = model.getPlayer(i).getY();
		}
		return playersCoord;
	}

	public boolean isPlayerOutBound(Player player) {
		int x = player.getX();
		int y = player.getY();

		boolean rollback = false;

		if (x < 0) {
			player.setDx(1);
			rollback = true;
		} else if (x > Info.WINDOW_X) {
			player.setDx(-1);
			rollback = true;
		}

		if (y < 0) {
			player.setDy(1);
			rollback = true;
		} else if (y + Info.PLAYER_SIZE > Info.WINDOW_Y) {
			player.setDy(-1);
			rollback = true;
		}
		return rollback;
	}
}
