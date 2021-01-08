package fr.ensisa.darcel.buoys.buoy.network;

import java.io.OutputStream;
import java.util.List;

import fr.ensisa.darcel.buoys.buoy.model.Battery;
import fr.ensisa.darcel.buoys.buoy.model.BuoyData;
import fr.ensisa.darcel.buoys.buoy.model.Location;
import fr.ensisa.darcel.buoys.buoy.model.Measures;
import fr.ensisa.darcel.buoys.buoy.model.State;
import fr.ensisa.darcel.buoys.network.Protocol;
import fr.ensisa.darcel.network.BasicAbstractWriter;

public class BuoyWriter extends BasicAbstractWriter {

    public BuoyWriter(OutputStream outputStream) {
        super(outputStream);
    }

	public void createGetBuoy(long id) {
		writeInt(Protocol.REQUEST_DO_GET_BUOY_BUOY);
		writeLong(id);
	}

	public void createUpdateVersion(String versionNumber) {
	}

	public void createSendData(List<BuoyData> data) {
	}

}
