package fr.ensisa.darcel.buoys.server.network;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.ensisa.darcel.buoys.network.Protocol;
import fr.ensisa.darcel.buoys.server.model.Battery;
import fr.ensisa.darcel.buoys.server.model.Buoy;
import fr.ensisa.darcel.buoys.server.model.BuoyData;
import fr.ensisa.darcel.buoys.server.model.BuoyDataTable;
import fr.ensisa.darcel.buoys.server.model.Buoys;
import fr.ensisa.darcel.buoys.server.model.Location;
import fr.ensisa.darcel.buoys.server.model.Measures;
import fr.ensisa.darcel.buoys.server.model.Model;
import fr.ensisa.darcel.buoys.server.model.Sensors;
import fr.ensisa.darcel.buoys.server.model.Usage;
import fr.ensisa.darcel.buoys.server.model.Version;
import fr.ensisa.darcel.buoys.server.model.Battery.Plug;

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
			case Protocol.REQUEST_DO_DELETE:
				processREQUEST_DO_DELETE(reader,writer);
				break;
			case Protocol.REQUEST_DO_CREATE_BUOY:
				processREQUEST_DO_CREATE_BUOY(reader,writer);
				break;
			case Protocol.REQUEST_DO_UPDATE_BUOY:
				processREQUEST_DO_UPDATE_BUOY(reader,writer);
				break;
			case Protocol.REQUEST_DO_CLEAR_DATA:
				processREQUEST_DO_CLEAR_DATA(reader,writer);
				break;
			case Protocol.REQUEST_DO_GET_BUOY_LAST_TICK:
				processREQUEST_DO_GET_BUOY_LAST_TICK(reader,writer);
				break;
			case Protocol.REQUEST_DO_GET_BUOY_DATA:
				processREQUEST_DO_GET_BUOY_DATA(reader,writer);
				break;
			case Protocol.REQUEST_DO_GET_BUOY_BUOY:
				processREQUEST_DO_GET_BUOY_BUOY(reader,writer);
				break;
			case Protocol.REQUEST_DO_UPDATE_VERSION:
				processREQUEST_DO_UPDATE_VERSION(reader,writer);
				break;
			case Protocol.REQUEST_DO_SEND_DATA:
				processREQUEST_DO_SEND_DATA(reader,writer);
				break;
			default:
				return false;
			}
			writer.send();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	private void processREQUEST_DO_SEND_DATA(TCPReader reader, TCPWriter writer) {
		List<BuoyData> list = new ArrayList<BuoyData>();
		int size = reader.receiveInt();
		for (int i = 0; i<size; i++){
			boolean isMeasure;
			long date = reader.receiveLong();
			long id = reader.receiveLong();
			Location loc = new Location(reader.receiveFloat(),reader.receiveFloat(),reader.receiveFloat());
			isMeasure = reader.receiveBoolean();
			Measures m = new Measures();
			if (isMeasure){
				m.setAcceleration_X(reader.receiveFloat());
				m.setAcceleration_Y(reader.receiveFloat());
				m.setAcceleration_Z(reader.receiveFloat());
				m.setRotation_X(reader.receiveFloat());
				m.setRotation_Y(reader.receiveFloat());
				m.setRotation_Z(reader.receiveFloat());
				m.setNorth(reader.receiveFloat());
				m.setTop_temperature(reader.receiveFloat());
				m.setTop_humidity(reader.receiveFloat());
				m.setTop_light(reader.receiveFloat());
				m.setTop_ir(reader.receiveFloat());
				m.setTelemetry_left(reader.receiveFloat());
				m.setBottom_temperature(reader.receiveFloat());
				m.setBottom_humidity(reader.receiveFloat());
				m.setBottom_light(reader.receiveFloat());
				m.setBottom_ir(reader.receiveFloat());
				m.setTelemetry_front(reader.receiveFloat());
				m.setTelemetry_back(reader.receiveFloat());
				m.setTelemetry_left(reader.receiveFloat());
				m.setTelemetry_right(reader.receiveFloat());
			}
			Battery b = new Battery();
			if (reader.receiveBoolean()){
				switch(reader.receiveInt()){
				case 1:
					b.setPlug(Plug.DISCONNECTED);
					break;
				case 2:
					b.setPlug(Plug.CHARGING_SLOW);
					break;
				case 3:
					b.setPlug(Plug.CHARGING_FAST);
					break;
				default:
					break;
				}
				b.setLevel(reader.receiveInt());
				b.setTemperature(reader.receiveInt());
				b.setLoad(reader.receiveInt());
				b.setDischarge(reader.receiveInt());
				b.setCycleCount(reader.receiveInt());
			}
			fr.ensisa.darcel.buoys.server.model.State s = null;

			if (reader.receiveBoolean()){
				s = new fr.ensisa.darcel.buoys.server.model.State(reader.receiveInt(),reader.receiveInt());
			}
			if (isMeasure){
				list.add(new BuoyData(date,id,loc,m));
			}
			else {
				list.add(new BuoyData(date,id,loc,s,b));
			}
		}
		if (list.isEmpty()){
			writer.createKO();
		}
		else {
			writer.createOK();
		}
	}

	private void processREQUEST_DO_UPDATE_VERSION(TCPReader reader, TCPWriter writer) {
		String version = reader.receiveString();
		Version versionserver = model.getLastVersion();
		if (versionserver != null) {
			if (!versionserver.getNumber().equals(version)){
				writer.createUpdateVersion(versionserver);
			}
			else {
				writer.createOK();
			}
		} else
			writer.createKO();
	}

	private void processREQUEST_DO_GET_BUOY_BUOY(TCPReader reader, TCPWriter writer) {
		Buoy buoy = model.getBuoys().getById(reader.receiveLong());
		if (buoy != null) {
			writer.createReplyGetBuoyBuoy(buoy);
		} else {
			writer.createKO();
		}
	}

	private void processREQUEST_DO_GET_BUOY_DATA(TCPReader reader, TCPWriter writer) {
		long id = reader.receiveLong();
		boolean tick = reader.receiveBoolean();
		boolean measures = reader.receiveBoolean();
		System.out.println(tick);
		System.out.println(measures);

		Map<Long, BuoyData> buoyTable = model.getBuoyDataTable().getByCriterion(id, tick, measures);
		Map<Long,BuoyData> newBuoysTable = new HashMap<Long,BuoyData>();
		if (buoyTable != null){
			for (Map.Entry<Long, BuoyData> entry : buoyTable.entrySet())
			{
				if(tick){
					if(entry.getValue().isTick()){
						newBuoysTable.put(entry.getKey(), entry.getValue());
					}
				}
				if(measures){
					if(!(entry.getValue().isTick())){
						newBuoysTable.put(entry.getKey(), entry.getValue());
					}
				}
			}
			writer.createGetBuoyData(newBuoysTable);
		}
		else writer.createKO();
	}

	private void processREQUEST_DO_GET_BUOY_LAST_TICK(TCPReader reader, TCPWriter writer) {
		long id = reader.receiveLong();
		BuoyData tick = model.getBuoyDataTable().getLastTick(id);
		if(tick!=null)
		{
			writer.createGetLastTick(tick);
		}
		else{
			writer.createKO();
		}
	}

	private void processREQUEST_DO_CLEAR_DATA(TCPReader reader, TCPWriter writer) {
		 long id = reader.receiveLong();
		 if(model.getBuoyDataTable().clear(id))
		 {
			 writer.createOK();
		 }
		 else
		 {
			writer.createKO();
		 }
	}

	private void processREQUEST_DO_UPDATE_BUOY(TCPReader reader, TCPWriter writer) {
		String version = reader.receiveString();
		String who = reader.receiveString();
		long id = reader.receiveLong();
		Buoy buoy = model.getBuoys().getById(id);
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
		Sensors sensors = new Sensors();
		sensors.setSensor3DAcceleration(reader.receiveBoolean());
		sensors.setSensor3DRotation(reader.receiveBoolean());
		sensors.setSensorBottom(reader.receiveBoolean());
		sensors.setSensorNorth(reader.receiveBoolean());
		sensors.setSensorTop(reader.receiveBoolean());
		sensors.setSensorTelemetry(reader.receiveBoolean());
		buoy.setSensors(sensors);

		if (model.getBuoys().getById(buoy.getId())!=null) {
			writer.createOK();
		} else {
			writer.createKO();
		}

	}

	private void processREQUEST_DO_CREATE_BUOY(TCPReader reader, TCPWriter writer) {
		Buoy buoy = new Buoy();
		String version = reader.receiveString();
		String who = reader.receiveString();
		long id = reader.receiveLong();
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
		Sensors sensors = new Sensors();
		sensors.setSensor3DAcceleration(reader.receiveBoolean());
		sensors.setSensor3DRotation(reader.receiveBoolean());
		sensors.setSensorBottom(reader.receiveBoolean());
		sensors.setSensorNorth(reader.receiveBoolean());
		sensors.setSensorTop(reader.receiveBoolean());
		sensors.setSensorTelemetry(reader.receiveBoolean());
		buoy.setSensors(sensors);
		model.getBuoys().add(buoy);
		if (model.getBuoys().getById(buoy.getId())!=null) {
			writer.createReplyCreateBuoy(buoy.getId());
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
		int datacount;
		if (model.getBuoyDataTable().getById(buoy.getId())==null)
			datacount=0;
		else
			datacount = model.getBuoyDataTable().getById(buoy.getId()).size();
		if (buoy != null) {
			writer.createReplyGetBuoy(buoy,datacount);
		} else {
			writer.createKO();
		}

	}

	private void processREQUEST_DO_GET_BUOY_LIST(TCPReader reader, TCPWriter writer) {
		Map<Long, Buoy> buoys = model.getBuoys().getBuoys();
		String who = reader.receiveString();
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