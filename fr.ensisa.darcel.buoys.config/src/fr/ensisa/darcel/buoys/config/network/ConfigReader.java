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
		switch (type) {
		case Protocol.REPLY_DO_RECEIVE_CURRENT:
			this.readCurrentVersion();
			break;
		case Protocol.REPLY_KO:
			break;
		}
	}

	public Version readCurrentVersion() {
		String number = readString();
		int size = readInt();
		byte[] content = new byte[size];

		for (int i = 0; i < size; i++) {
			content[i] = readByte();
		}

		return new Version(number, content);
	}
}
