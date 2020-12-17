package fr.ensisa.darcel.buoys.config.network;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import fr.ensisa.darcel.buoys.config.model.Buoy;
import fr.ensisa.darcel.buoys.config.model.Usage;
import fr.ensisa.darcel.buoys.config.model.Version;
import fr.ensisa.darcel.buoys.network.Protocol;
import fr.ensisa.darcel.network.BasicAbstractReader;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ConfigReader extends BasicAbstractReader {

	public ConfigReader(InputStream inputStream) {
		super(inputStream);
	}

	private void eraseFields() {
	}

	public void receive() {
		type = readInt();
		eraseFields();
		switch (type) { //Ne rien mettre dans ce switch svp
		case Protocol.REPLY_KO:
			break;
		}
	}

	//Méthodes pour lire les Arguments des REPLY

	public Version readCurrentVersion() {
		String number = readString();
		int size = readInt();
		byte[] content;
		if (size>0){
			content = new byte[size];
			for (int i = 0; i < size; i++) {
				content[i] = readByte();
			}
		}
		else {
			content = null;
		}
		return (new Version(number, content));
	}

	public Buoy readBuoy() {
		Buoy buoy = new Buoy();
		String version = readString();
		String who = readString();
		long id = readLong();
		buoy.getVersion().set(version);
		buoy.getWho().set(who);
		buoy.setId(id);

		switch (readInt()) {
		case 1:
			buoy.getUsage().set(Usage.UNUSED);
			break;
		case 2:
			buoy.getUsage().set(Usage.READY);
			break;
		case 3:
			buoy.getUsage().set(Usage.WORKING);
			break;
		case 4:
			buoy.getUsage().set(Usage.BACK);
			break;
		default:
			break;
		}
		buoy.getSensors().getSensor3DAcceleration().set(readBoolean());
		buoy.getSensors().getSensor3DRotation().set(readBoolean());
		buoy.getSensors().getSensorBottom().set(readBoolean());
		buoy.getSensors().getSensorNorth().set(readBoolean());
		buoy.getSensors().getSensorTop().set(readBoolean());
		buoy.getSensors().getSensorTelemetry().set(readBoolean());
		buoy.getDataCount().set(readInt());
		return buoy;
	}

	public List<Buoy> readBuoyList() {
		List<Buoy> buoys = new ArrayList<Buoy>();
		int size = readInt();
		for (int i = 0; i<size;i++)
		{
			Buoy buoy = new Buoy();
			buoy.getId().set(readLong());
			buoy.getWho().set(readString());
			switch (readInt()) {
			case 1:
				buoy.getUsage().set(Usage.UNUSED);
				break;
			case 2:
				buoy.getUsage().set(Usage.READY);
				break;
			case 3:
				buoy.getUsage().set(Usage.WORKING);
				break;
			case 4:
				buoy.getUsage().set(Usage.BACK);
				break;
			default:
				break;
			}
			buoys.add(buoy);
		}

		return buoys;


	}

	public Boolean readDeleted() {
		return readBoolean();
	}
	public long readCreated(){
		System.out.println("configreader");
		return readLong();
	}
}
