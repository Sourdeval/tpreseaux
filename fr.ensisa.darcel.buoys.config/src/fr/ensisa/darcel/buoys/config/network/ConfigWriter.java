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
	}

	public void createNewVersion(Version version) {
		writeInt(Protocol.REQUEST_DO_SEND_NEW);
		writeString(version.getNumber().getValue());
		byte [] a = version.getContent().toString().getBytes();
    	if (a!=null){
        	int size=a.length;
        	writeInt(size);
        	for(int i=0;i<size;i++)
        	{
        		writeByte(a[i]);
        	}
    	}
	}

	public void createGetBuoy(long id) {
		writeInt(Protocol.REQUEST_DO_GET_BUOY);
		writeLong(id);
	}

	public void createGetBuoyList(String who) {
		writeInt(Protocol.REQUEST_DO_GET_BUOY_LIST);
		writeString(who);
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
