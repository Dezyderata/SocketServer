package server;

import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class ServerEcho {
	private int portNumber;
	private String hostName;
	ServerSocket server;
	Socket clientSocket;
	BufferedReader in;
	BufferedReader stdIn;
	PrintWriter out;
	
	public ServerEcho(int portNumber, String hostName) {
		this.hostName = hostName;
		this.portNumber = portNumber;
	}
	public void connect() throws IOException {
		boolean flag = false;
		try {
			this.stdIn = new BufferedReader(new InputStreamReader(System.in));
			this.server = new ServerSocket(this.portNumber, 10, InetAddress.getByName(this.hostName));
			this.server.setSoTimeout(5000);
			
			do {
				try {
					if(this.stdIn.ready()) {
						flag = this.stdIn.readLine().equalsIgnoreCase("exit");
					}else if(!(this.clientSocket = server.accept()).isClosed()) {
						this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
						this.out = new PrintWriter(this.clientSocket.getOutputStream(), true);
						answer();
					}
				}catch(SocketTimeoutException e) {
				}
			}while(!flag);
		} catch (UnknownHostException e) {
			System.err.println("Something is wrong with host!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO problem during connecting!");
			e.printStackTrace();
		}finally{
			server.close();
		}
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
}
