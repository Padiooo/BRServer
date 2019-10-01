package Server;

public class BallShoot extends Thread {

	private int id;
	private double angle;

	private int x;
	private int y;

	public BallShoot(int x, int y, int id_ball, double angle) {
		this.id = id_ball;
		this.angle = angle;
		this.x = x;
		this.y = y;
	}

	@Override
	public void run() {

		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			x += Info.BALL_SPEED * Math.cos(angle);
			y += Info.BALL_SPEED * Math.sin(angle);

		}
	}

	@Override
	public String toString() {
		String s = x + " " + y;
		return s;

	}

	// ------------------------------------------------------

	public int getBallId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
