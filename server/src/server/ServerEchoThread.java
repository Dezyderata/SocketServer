package server;

import java.io.*;
import java.net.*;


public class ServerEchoThread implements Runnable {
	Socket clientSocket;
	BufferedReader in;
	PrintWriter out;
	public ServerEchoThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	public void answer() {
		String newLine;
		try {
			while((newLine = in.readLine()) != null){
				this.out.println("Server echo: " + newLine);
			}
		} catch (IOException e) {
			System.err.println("Something went wrong during readLine!");
			e.printStackTrace();
		}		
	}

	@Override
	public void run() {
		try {
			this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			this.out = new PrintWriter(this.clientSocket.getOutputStream(), true);
		} catch (IOException e) {
			System.err.println("Something went wrong during creating streams for: " + clientSocket.getLocalPort());
			e.printStackTrace();
		}
		answer();
		
	}
}
