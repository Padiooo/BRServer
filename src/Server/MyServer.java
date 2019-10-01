package Server;

public class MyServer {

	public static void main(String args[]) {

		new ServerConnection().start();
		System.out.println("Server running . . .");
	}
}