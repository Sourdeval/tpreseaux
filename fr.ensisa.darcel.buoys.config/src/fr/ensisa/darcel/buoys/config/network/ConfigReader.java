package fr.ensisa.darcel.buoys.config.network;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import fr.ensisa.darcel.buoys.config.model.Buoy;
import fr.ensisa.darcel.buoys.config.model.Usage;
import fr.ensisa.darcel.buoys.config.model.Version;
import fr.ensisa.darcel.buoys.network.Protocol;
import fr.ensisa.darcel.network.BasicAbstractReader;

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
        case Protocol.REPLY_KO:
        	break;
        }
    }

}
