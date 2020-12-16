package fr.ensisa.darcel.buoys.server;

import java.net.ServerSocket;
import java.net.Socket;

import fr.ensisa.darcel.buoys.network.Protocol;
import fr.ensisa.darcel.buoys.server.model.Model;
import fr.ensisa.darcel.buoys.server.network.TCPSession;

public class TCPServer extends Thread {

	private ServerSocket server = null;
	private Model model = null;

	public TCPServer(Model model) {
		super();
		this.model = model;
	}

	public void run () {
		try {
			server = new ServerSocket (Protocol.BUOYS_TCP_PORT);
			while (true) {
				Socket connection = server.accept();
				TCPSession session = new TCPSession (connection, model);
				session.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
