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
		writeInt(Protocol.REQUEST_DO_DELETE);
		writeLong(id);
	}

	public void createCreateBuoy(Buoy buoy) {
		System.out.println("createBuoy");

		writeInt(Protocol.REQUEST_DO_CREATE_BUOY);
		if (buoy.getVersion().getValue() != null)
			writeString(buoy.getVersion().getValue());
		else
			writeString("null");
		writeString(buoy.getWho().getValue());
		writeLong(buoy.getId().getValue());
		switch (buoy.getUsage().getValue()) {
		case UNUSED:
			writeInt(1);
			break;
		case READY:
			writeInt(2);
			break;
		case WORKING:
			writeInt(3);
			break;
		case BACK:
			writeInt(4);
			break;
		default:
			writeInt(0);
			break;
		}

		writeBoolean(buoy.getSensors().getSensor3DAcceleration().getValue());
		writeBoolean(buoy.getSensors().getSensor3DRotation().getValue());
		writeBoolean(buoy.getSensors().getSensorBottom().getValue());
		writeBoolean(buoy.getSensors().getSensorNorth().getValue());
		writeBoolean(buoy.getSensors().getSensorTop().getValue());
		writeBoolean(buoy.getSensors().getSensorTelemetry().getValue());

	}

	public void createUpdateBuoy(Buoy buoy) {
		writeInt(Protocol.REQUEST_DO_UPDATE_BUOY);
		if (buoy.getVersion().getValue() != null)
			writeString(buoy.getVersion().getValue());
		else
			writeString("null");
		writeString(buoy.getWho().getValue());
		writeLong(buoy.getId().getValue());
		switch (buoy.getUsage().getValue()) {
		case UNUSED:
			writeInt(1);
			break;
		case READY:
			writeInt(2);
			break;
		case WORKING:
			writeInt(3);
			break;
		case BACK:
			writeInt(4);
			break;
		default:
			writeInt(0);
			break;
		}

		writeBoolean(buoy.getSensors().getSensor3DAcceleration().getValue());
		writeBoolean(buoy.getSensors().getSensor3DRotation().getValue());
		writeBoolean(buoy.getSensors().getSensorBottom().getValue());
		writeBoolean(buoy.getSensors().getSensorNorth().getValue());
		writeBoolean(buoy.getSensors().getSensorTop().getValue());
		writeBoolean(buoy.getSensors().getSensorTelemetry().getValue());

	}

	public void createClearDataBuoy(long id) {
		writeInt(Protocol.REQUEST_DO_CLEAR_DATA);
		writeLong(id);
	}

}
