package fr.ensisa.darcel.buoys.service.network;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.ensisa.darcel.buoys.service.model.Battery;
import fr.ensisa.darcel.buoys.service.model.Buoy;
import fr.ensisa.darcel.buoys.service.model.BuoyData;
import fr.ensisa.darcel.buoys.service.model.Location;
import fr.ensisa.darcel.buoys.service.model.Usage;
import fr.ensisa.darcel.buoys.service.model.Battery.Plug;
import fr.ensisa.darcel.buoys.network.Protocol;
import fr.ensisa.darcel.network.BasicAbstractReader;

public class ServiceReader extends BasicAbstractReader {

    public ServiceReader(InputStream inputStream) {
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
