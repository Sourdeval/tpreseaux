package fr.ensisa.darcel.buoys.server;

import fr.ensisa.darcel.buoys.server.model.Model;

public class Application {

	private UDPServer udp = null;
	private TCPServer tcp = null;
	private Model model = null;

	public void start () {
		model = new Model ();
		model.populate();
		udp = new UDPServer(model);
		udp.start();
		tcp = new TCPServer(model);
		tcp.start();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Application m = new Application ();
		m.start();
	}

}
