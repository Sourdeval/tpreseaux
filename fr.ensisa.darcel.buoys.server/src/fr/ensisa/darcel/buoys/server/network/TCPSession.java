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
import fr.ensisa.darcel.buoys.server.model.Sensors;
import fr.ensisa.darcel.buoys.server.model.Usage;
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
			case Protocol.REQUEST_DO_CREATE_BUOY:
				processREQUEST_DO_CREATE_BUOY(reader,writer);
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


	private void processREQUEST_DO_CREATE_BUOY(TCPReader reader, TCPWriter writer) {
		System.out.println("tcp-session");
		Buoy buoy = new Buoy();
		String version = reader.receiveString();
		System.out.println("version : "+version);
		String who = reader.receiveString();
		System.out.println("who : "+who);
		long id = reader.receiveLong();
		System.out.println("id : "+id);
		buoy.setVersion(version);
		buoy.setWho(who);
		buoy.setId(id);

		switch (reader.receiveInt()) {
		case 1:
			buoy.setUsage(Usage.UNUSED);
			break;
		case 2:
			buoy.setUsage(Usage.READY);
			break;
		case 3:
			buoy.setUsage(Usage.WORKING);
			break;
		case 4:
			buoy.setUsage(Usage.BACK);
			break;
		default:
			break;
		}
		//System.out.println("usage : "+reader.receiveInt());
		System.out.println(buoy.toString());
		Sensors sensors = new Sensors();

		//System.out.println("3daccel : "+reader.receiveBoolean());
		sensors.setSensor3DAcceleration(reader.receiveBoolean());
		sensors.setSensor3DRotation(reader.receiveBoolean());
		sensors.setSensorBottom(reader.receiveBoolean());
		sensors.setSensorNorth(reader.receiveBoolean());
		sensors.setSensorTop(reader.receiveBoolean());
		sensors.setSensorTelemetry(reader.receiveBoolean());
		System.out.print(sensors.isSensor3DAcceleration());
		buoy.setSensors(sensors);
		System.out.println(buoy.getSensors().isSensor3DAcceleration());
		model.getBuoys().add(buoy);
		if (model.getBuoys().getById(buoy.getId())!=null) {
			writer.createOK();
		} else {
			writer.createKO();
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