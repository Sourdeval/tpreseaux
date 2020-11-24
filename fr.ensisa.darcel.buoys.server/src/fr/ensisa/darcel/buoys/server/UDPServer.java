package fr.ensisa.darcel.buoys.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import fr.ensisa.darcel.buoys.network.Protocol;
import fr.ensisa.darcel.buoys.server.model.Model;
import fr.ensisa.darcel.buoys.server.network.UDPSession;

public class UDPServer extends Thread {

	private DatagramSocket server = null;
	private Model model = null;

	public UDPServer(Model model) {
		super();
		this.model = model;
	}

	public void run () {
		try {
			server = new DatagramSocket(Protocol.BUOYS_UDP_PORT);
			while (true) {
				byte [] buffer = new byte [1500];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				server.receive(packet);
				UDPSession session = new UDPSession (packet, model);
				session.start ();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
