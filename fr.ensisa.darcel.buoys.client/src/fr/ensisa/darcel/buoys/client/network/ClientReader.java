package fr.ensisa.darcel.buoys.client.network;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import fr.ensisa.darcel.buoys.client.model.Battery;
import fr.ensisa.darcel.buoys.client.model.Buoy;
import fr.ensisa.darcel.buoys.client.model.BuoyData;
import fr.ensisa.darcel.buoys.client.model.Location;
import fr.ensisa.darcel.buoys.client.model.Measures;
import fr.ensisa.darcel.buoys.client.model.Usage;
import fr.ensisa.darcel.buoys.network.Protocol;
import fr.ensisa.darcel.network.BasicAbstractReader;

public class ClientReader extends BasicAbstractReader {

    public ClientReader(InputStream inputStream) {
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
