package server;

import java.io.IOException;

public class main {

	public static void main(String[] args) throws IOException {
		ServerEcho serverEcho = new ServerEcho(65000, "127.0.0.1");
		serverEcho.connect();

	}

}
