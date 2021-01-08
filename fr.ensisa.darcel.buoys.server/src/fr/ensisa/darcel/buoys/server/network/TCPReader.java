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
		switch (type) {//Ne rien écrire dans ce switch svp
		case 0 : break;
		}
	}
	public long receiveLong() {
		long l = readLong();
		return l;
	}
	public Version readDoSendNew() {
		String n = readString();
		int size = readInt();
		byte[] a = new byte[size];
		for (int i=0; i < size; i++){
			a[i] = readByte();
		}
		return new Version(n,a);
	}

	public String receiveString() {
		String s = readString();
		return s;
	}
	public int receiveInt(){
		int i = readInt();
		return i;
	}

	public boolean receiveBoolean() {
		boolean b = readBoolean();
		return b;
	}
	public float receiveFloat() {
		return readFloat();
	}

	//Méthodes pour lire les Arguments des REQUEST

}
