package fr.ensisa.darcel.buoys.client.network;

import java.io.OutputStream;

import fr.ensisa.darcel.buoys.network.Protocol;
import fr.ensisa.darcel.network.BasicAbstractWriter;

public class ClientWriter extends BasicAbstractWriter {

    public ClientWriter(OutputStream outputStream) {
        super(outputStream);
    }

	private void writeNullableString(String text) {
        if (text == null) {
        	writeInt(0);
        } else {
        	writeInt(1);
            writeString(text);
        }
	}

	public void createGetBuoyList(String who) {
	}

	public void createGetBuoy(long id) {
	}

	public void createGetBuoyLastTick(long id) {
	}

	public void createGetBuoyData(long id, boolean tick, boolean measures) {
	}

}
