package fr.ensisa.darcel.buoys.server.network;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import fr.ensisa.darcel.buoys.network.Protocol;
import fr.ensisa.darcel.buoys.server.model.Battery;
import fr.ensisa.darcel.buoys.server.model.BuoyData;
import fr.ensisa.darcel.buoys.server.model.Location;
import fr.ensisa.darcel.buoys.server.model.State;
import fr.ensisa.darcel.buoys.server.model.Battery.Plug;
import fr.ensisa.darcel.network.BasicAbstractReader;

public class UDPReader extends BasicAbstractReader {

	private BuoyData data;

	public UDPReader(byte [] data) {
		super (new ByteArrayInputStream(data));
	}

	private int readAsByte() {
        return (int) (readByte() & 0xFF);
	}

	private BuoyData readUDPStd1() {
		return null;
	}

	private BuoyData readUDPStd2() {
		return null;
	}

	private BuoyData readUDPService() {
		return null;
	}

	public void receive() throws IOException {
		type = readInt ();
		switch (type) {
		case 0 : break;
		case Protocol.UDP_STD1: 	data = readUDPStd1(); break;
		case Protocol.UDP_STD2:		data = readUDPStd2(); break;
		case Protocol.UDP_SERVICE:	data = readUDPService (); break;
		}
	}

	public BuoyData getBuoyData() {
		return data;
	}

}
