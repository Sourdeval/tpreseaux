package fr.ensisa.darcel.buoys.server.network;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.ensisa.darcel.buoys.network.Protocol;
import fr.ensisa.darcel.buoys.server.model.Buoy;
import fr.ensisa.darcel.buoys.server.model.BuoyData;
import fr.ensisa.darcel.buoys.server.model.Buoys;
import fr.ensisa.darcel.buoys.server.model.Model;
import fr.ensisa.darcel.buoys.server.model.Version;

public class TCPSession extends Thread {

	private Socket connection;
	private Model model;

	public TCPSession(Socket connection, Model model) {
		this.connection = connection;
		this.model = model;
	}

	public void close() {
		this.interrupt();
		try {
			if (connection != null)
				connection.close();
		} catch (IOException e) {
		}
		connection = null;
	}

	public boolean operate() {
		try {
			TCPWriter writer = new TCPWriter(connection.getOutputStream());
			TCPReader reader = new TCPReader(connection.getInputStream());
			reader.receive();
			switch (reader.getType()) {
			case 0:
				return false; // socket closed
			case Protocol.REQUEST_DO_RECEIVE_CURRENT:
				processREQUEST_DO_RECEIVE_CURRENT(reader, writer);
				break;
			case Protocol.REQUEST_DO_SEND_NEW:
				processREQUEST_DO_SEND_NEW(reader, writer);
				break;
			case Protocol.REQUEST_DO_GET_BUOY_LIST:
				processREQUEST_DO_GET_BUOY_LIST(reader, writer);
				break;
			case Protocol.REQUEST_DO_GET_BUOY:
				processREQUEST_DO_GET_BUOY(reader, writer);
				break;
			default:
				return false; // connection jammed
			// to remove before adding anything
			// entry added to remove annoying error reported by compiler
			case 1:
			}
			writer.send();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	private void processREQUEST_DO_GET_BUOY(TCPReader reader, TCPWriter writer) {

	}

	private void processREQUEST_DO_GET_BUOY_LIST(TCPReader reader, TCPWriter writer) {
		// TODO Auto-generated method stub

	}

	private void processREQUEST_DO_SEND_NEW(TCPReader reader, TCPWriter writer) {
		/*Version version

		if (version != null) {
			writer.createNewVersion(version);
		} else
			writer.createKO();
*/
	}

	private void processREQUEST_DO_RECEIVE_CURRENT(TCPReader reader, TCPWriter writer) {
		Version version = model.getLastVersion();

		if (version != null) {
			writer.createCurrentVersion(version);
		} else
			writer.createKO();

	}
}