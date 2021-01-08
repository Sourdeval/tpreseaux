package fr.ensisa.darcel.buoys.buoy.network;

import java.io.InputStream;

import fr.ensisa.darcel.buoys.buoy.model.Buoy;
import fr.ensisa.darcel.buoys.buoy.model.Usage;
import fr.ensisa.darcel.buoys.buoy.model.Version;
import fr.ensisa.darcel.buoys.network.Protocol;
import fr.ensisa.darcel.network.BasicAbstractReader;

public class BuoyReader extends BasicAbstractReader {

    public BuoyReader(InputStream inputStream) {
        super(inputStream);
    }

    private void eraseFields() {
    }

	public void receive() {
        type = readInt();
        eraseFields();
        switch (type) {
        case Protocol.REPLY_KO:
        	break;
        }
    }

}
