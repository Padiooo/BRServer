package Server;

import java.util.Observable;

public class Player extends Observable {

	private int x;
	private int y;
	private int dx;
	private int dy;

	private int id;
	private boolean isAlive = true;
	private String death_recap = "";

	private int speed = 10;

	private BallShoot[] balls = new BallShoot[3];

	public Player(int id) {
		this.id = id;
		getPosition(id);
	}

	public void move(int dx, int dy) {
		x += speed * dx;
		y += speed * dy;
		setChanged();
		notifyObservers(this);
	}

	public void rollBack() {
		x += speed * dx;
		y += speed * dy;
	}

	public void shoot(int id_ball, int x, int y) {
		double angle = Math.atan2(y - this.y, x - this.x);
		BallShoot ball = new BallShoot(this.x, this.y, id_ball, angle);
		balls[id_ball] = ball;
		ball.start();
	}

	public void reload(int id_ball) {
		balls[id_ball] = null;
	}

	public void die(int id_player_killer) {
		isAlive = false;
		death_recap = "Died from " + id_player_killer;
		x = -999;
		y = -999;
	}

	@Override
	public String toString() {

		StringBuilder s = new StringBuilder();

		if (isAlive) {
			s.append(id + " " + x + " " + y + " ");
			for (int i = 0; i < 3; i++) {
				if (balls[i] == null) {
					s.append(-99 + " " + -99 + " ");
				} else {
					s.append(balls[i].toString() + " ");
				}
			}
		} else {
			s.append(id + " " + -99 + " " + -99 + " ");
			for (int i = 0; i < 3; i++) {
				s.append(-99 + " " + -99 + " ");
			}
		}

		return s.toString();
	}

	public String getDeathRecap() {
		return death_recap;
	}

	private void getPosition(int id) {
		switch (id) {
		case 0:
			x = 25;
			y = 25;
			break;
		case 1:
			x = 475;
			y = 475;
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		case 6:
			break;
		case 7:
			break;
		case 8:
			break;
		case 9:
			break;
		case 10:
			break;
		case 11:
			break;
		case 12:
			break;
		}
	}

	// ------------------------------------------------------

	public boolean isAlive() {
		return isAlive;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getDx() {
		return dx;
	}

	public int getDy() {
		return dy;
	}

	public int getId() {
		return id;
	}

	public BallShoot[] getBalls() {
		return balls;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setBalls(BallShoot[] balls) {
		this.balls = balls;
	}

}
