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
		return buoy;
	}
}
