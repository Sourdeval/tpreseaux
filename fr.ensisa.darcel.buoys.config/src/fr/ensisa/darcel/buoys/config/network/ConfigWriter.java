package fr.ensisa.darcel.buoys.config.network;

import java.io.OutputStream;

import fr.ensisa.darcel.buoys.config.model.Buoy;
import fr.ensisa.darcel.buoys.config.model.Version;
import fr.ensisa.darcel.buoys.network.Protocol;
import fr.ensisa.darcel.network.BasicAbstractWriter;

public class ConfigWriter extends BasicAbstractWriter {

    public ConfigWriter(OutputStream outputStream) {
        super(outputStream);
    }

	public void createReceiveCurrentVersion() {
		writeInt(Protocol.REQUEST_DO_RECEIVE_CURRENT);
		System.out.println("Bonjour");
	}

	public void createNewVersion(Version version) {
	}

	public void createGetBuoy(long id) {
	}

	public void createGetBuoyList(String who) {
	}

	public void createDeleteBuoy(long id) {
	}

	public void createCreateBuoy(Buoy buoy) {
	}

	public void createUpdateBuoy(Buoy buoy) {
	}

	public void createClearDataBuoy(long id) {
	}

}
