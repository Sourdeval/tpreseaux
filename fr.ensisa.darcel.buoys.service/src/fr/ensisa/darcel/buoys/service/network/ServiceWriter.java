package fr.ensisa.darcel.buoys.service.network;

import java.io.OutputStream;

import fr.ensisa.darcel.buoys.network.Protocol;
import fr.ensisa.darcel.network.BasicAbstractWriter;

public class ServiceWriter extends BasicAbstractWriter {

    public ServiceWriter(OutputStream outputStream) {
        super(outputStream);
    }

	public void createGetBuoyList(String who) {
		writeInt(Protocol.REQUEST_DO_GET_BUOY_LIST);
		writeString(who);
	}

	public void createGetBuoy(long id) {
		writeInt(Protocol.REQUEST_DO_GET_BUOY);
		writeLong(id);
	}

	public void createGetBuoyLastTick(long id) {
		writeInt(Protocol.REQUEST_DO_GET_BUOY_LAST_TICK);
		writeLong(id);
	}

}
