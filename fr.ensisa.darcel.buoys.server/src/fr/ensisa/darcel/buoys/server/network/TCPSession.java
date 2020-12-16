package fr.ensisa.darcel.buoys.server.network;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
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
		System.out.println("tcp session created");
	}

	public void close() {
		this.interrupt();
		try {
			if (connection != null)
				connection.close();
			System.out.println("tcp session closed");
		} catch (IOException e) {
		}
		connection = null;
	}

	public boolean operate() {
		try {
			System.out.println("operate...");
			TCPWriter writer = new TCPWriter(connection.getOutputStream());
			TCPReader reader = new TCPReader(connection.getInputStream());
			System.out.println("Pré receive");
			reader.receive();
			System.out.println("Post receive");
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
			case Protocol.REQUEST_DO_DELETE:
				processREQUEST_DO_DELETE(reader,writer);
				break;
			default:
				return false; // connection jammed
			// to remove before adding anything
			// entry added to remove annoying error reported by compiler
			//case 1:
			}
			writer.send();
			return true;
		} catch (IOException e) {
			return false;
		}
	}


	private void processREQUEST_DO_DELETE(TCPReader reader, TCPWriter writer) {
		Buoy buoy = model.getBuoys().getById(reader.receiveLong());
		if (model.getBuoys().remove(buoy.getId())) {
			writer.createReplyDeleteBuoy();
		} else {
			writer.createKO();
		}
	}

	private void processREQUEST_DO_GET_BUOY(TCPReader reader, TCPWriter writer) {
		Buoy buoy = model.getBuoys().getById(reader.receiveLong());
		if (buoy != null) {
			writer.createReplyGetBuoy(buoy);
		} else {
			writer.createKO();
		}
	}

	private void processREQUEST_DO_GET_BUOY_LIST(TCPReader reader, TCPWriter writer) {
		Map<Long, Buoy> buoys = model.getBuoys().getBuoys();
		String who = reader.receiveString();
		System.out.println(who);
		Map<Long,Buoy> newBuoysList = new HashMap<Long,Buoy>();
		if (who.isEmpty())
		{
			for (Map.Entry<Long, Buoy> entry : buoys.entrySet())
			{
				newBuoysList.put(entry.getKey(), entry.getValue());
			}
		}
		else{
			for (Map.Entry<Long, Buoy> entry : buoys.entrySet())
			{
				if(entry.getValue().getWho().equals(who) )
				{
					newBuoysList.put(entry.getKey(), entry.getValue());
				}
			}
		}

		if (!(newBuoysList.isEmpty())) {
			writer.createReplyGetBuoyList(newBuoysList);
		} else {
			writer.createKO();
		}
	}

	private void processREQUEST_DO_SEND_NEW(TCPReader reader, TCPWriter writer) {
		model.setLastVersion(reader.readDoSendNew());
		if (model.getLastVersion() != null) {
			writer.createReplyDoSendNew();
		} else {
			writer.createKO();
		}
	}

	private void processREQUEST_DO_RECEIVE_CURRENT(TCPReader reader, TCPWriter writer) {
		Version version = model.getLastVersion();
		if (version != null) {
			writer.createCurrentVersion(version);
		} else
			writer.createKO();
	}

	public void run() {
		while (true) {
			if (! operate())
				break;
		}
		try {
			if (connection != null) connection.close();
		} catch (IOException e) {
		}
	}

}