package fr.ensisa.darcel.buoys.server.network;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import fr.ensisa.darcel.buoys.network.Protocol;
import fr.ensisa.darcel.buoys.server.model.Battery;
import fr.ensisa.darcel.buoys.server.model.Buoy;
import fr.ensisa.darcel.buoys.server.model.BuoyData;
import fr.ensisa.darcel.buoys.server.model.Location;
import fr.ensisa.darcel.buoys.server.model.Measures;
import fr.ensisa.darcel.buoys.server.model.Sensors;
import fr.ensisa.darcel.buoys.server.model.State;
import fr.ensisa.darcel.buoys.server.model.Usage;
import fr.ensisa.darcel.buoys.server.model.Version;
import fr.ensisa.darcel.buoys.server.model.Battery.Plug;
import fr.ensisa.darcel.network.BasicAbstractReader;

public class TCPReader extends BasicAbstractReader {

	public TCPReader(InputStream inputStream) {
		super (inputStream);
	}

	private void eraseFields() {
	}

	public void receive() {
		type = readInt ();
		eraseFields ();
		switch (type) {
		case Protocol.REQUEST_DO_RECEIVE_CURRENT:
			readCurrentVersion();
		case 0 : break;
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
