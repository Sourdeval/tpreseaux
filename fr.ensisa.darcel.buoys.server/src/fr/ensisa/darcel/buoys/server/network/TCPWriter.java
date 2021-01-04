package fr.ensisa.darcel.buoys.server.network;

import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;

import fr.ensisa.darcel.buoys.network.Protocol;
import fr.ensisa.darcel.buoys.server.model.Battery;
import fr.ensisa.darcel.buoys.server.model.Buoy;
import fr.ensisa.darcel.buoys.server.model.BuoyData;
import fr.ensisa.darcel.buoys.server.model.Location;
import fr.ensisa.darcel.buoys.server.model.Measures;
import fr.ensisa.darcel.buoys.server.model.State;
import fr.ensisa.darcel.buoys.server.model.Version;
import fr.ensisa.darcel.network.BasicAbstractWriter;

public class TCPWriter extends BasicAbstractWriter {

    public TCPWriter(OutputStream outputStream) {
        super (outputStream);
    }

    public void createKO() {
        writeInt(Protocol.REPLY_KO);
    }
    public void createOK(){
    	writeInt(Protocol.REPLY_OK);
    }

    public void createCurrentVersion(Version v)
    {
    	writeInt(Protocol.REPLY_DO_RECEIVE_CURRENT);
    	writeString(v.getNumber());
    	byte [] a = v.getContent();
    	if (a!=null){
        	int size=a.length;
        	writeInt(size);
        	for(int i=0;i<size;i++)
        	{
        		writeByte(v.getContent()[i]);
        	}
    	}
    	else {
    		writeInt(0);
    	}

    }


	public void createReplyDoSendNew() {
		writeInt(Protocol.REPLY_DO_SEND_NEW);
	}

	public void createReplyGetBuoy(Buoy buoy,int datacount) {
		writeInt(Protocol.REPLY_DO_GET_BUOY);
		if (buoy.getVersion() != null)
			writeString(buoy.getVersion());
		else
			writeString("null");
		writeString(buoy.getWho());
		writeLong(buoy.getId());
		switch (buoy.getUsage()) {
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
		writeBoolean(buoy.getSensors().isSensor3DAcceleration());
		writeBoolean(buoy.getSensors().isSensor3DRotation());
		writeBoolean(buoy.getSensors().isSensorBottom());
		writeBoolean(buoy.getSensors().isSensorNorth());
		writeBoolean(buoy.getSensors().isSensorTop());
		writeBoolean(buoy.getSensors().isSensorTelemetry());
		writeInt(datacount);
	}

	public void createReplyGetBuoyList(Map<Long, Buoy> buoys) {
		writeInt(Protocol.REPLY_DO_GET_BUOY_LIST);
		writeInt(buoys.size());
		for (Map.Entry<Long, Buoy> entry : buoys.entrySet())
		{
			writeLong(entry.getValue().getId());
			writeString(entry.getValue().getWho());
			switch (entry.getValue().getUsage()) {
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
		}
	}



	public void createReplyDeleteBuoy() {

		writeInt(Protocol.REPLY_DO_DELETE);
		writeBoolean(true);

	}


	public void createReplyCreateBuoy(long id) {
		writeInt(Protocol.REPLY_DO_CREATE_BUOY);
		writeLong(id);
	}

	public void createGetLastTick(BuoyData tick) {
		writeInt(Protocol.REPLY_DO_GET_BUOY_LAST_TICK);
		writeLong(tick.getDate().getTime());


		writeFloat(tick.getLocation().getLongitude());
		writeFloat(tick.getLocation().getLatitude());
		writeFloat(tick.getLocation().getAltitude());
		writeInt(tick.getState().getState());
		writeInt(tick.getState().getDetail());
		switch (tick.getBattery().getPlug()) {
		case DISCONNECTED:
			writeInt(1);
			break;
		case CHARGING_SLOW:
			writeInt(2);
			break;
		case CHARGING_FAST:
			writeInt(3);
			break;
		default:
			writeInt(0);
			break;
		}
		writeInt(tick.getBattery().getLevel());
		writeInt(tick.getBattery().getTemperature());
		writeInt(tick.getBattery().getLoad());

		writeInt(tick.getBattery().getDischarge());
		writeInt(tick.getBattery().getCycleCount());
	}

	public void createGetBuoyData(Map<Long, BuoyData> buoyTable) {
		writeInt(Protocol.REPLY_DO_GET_BUOY_DATA);
		writeInt(buoyTable.size());
		for (Map.Entry<Long, BuoyData> entry : buoyTable.entrySet())
		{

			writeLong(entry.getValue().getDate().getTime());
			System.out.println(entry.getValue().getType().toString());
			switch (entry.getValue().getType().toString()) {
			case "TICK":
				writeInt(1);
				writeTick(entry);
				break;
			case "MEASURES":
				writeInt(2);
				writeMeasures(entry);
				break;

			default:
				writeInt(0);
				break;
			}

		}

	}
	public void writeTick(Map.Entry<Long, BuoyData> entry){
		writeFloat(entry.getValue().getLocation().getLongitude());
		writeFloat(entry.getValue().getLocation().getLatitude());
		writeFloat(entry.getValue().getLocation().getAltitude());
		writeInt(entry.getValue().getState().getState());
		writeInt(entry.getValue().getState().getDetail());
		switch (entry.getValue().getBattery().getPlug()) {
		case DISCONNECTED:
			writeInt(1);
			break;
		case CHARGING_SLOW:
			writeInt(2);
			break;
		case CHARGING_FAST:
			writeInt(3);
			break;
		default:
			writeInt(0);
			break;
		}
		writeInt(entry.getValue().getBattery().getLevel());
		writeInt(entry.getValue().getBattery().getTemperature());
		writeInt(entry.getValue().getBattery().getLoad());

		writeInt(entry.getValue().getBattery().getDischarge());
		writeInt(entry.getValue().getBattery().getCycleCount());

	}
	public void writeMeasures(Map.Entry<Long, BuoyData> entry){
		writeFloat(entry.getValue().getLocation().getLongitude());
		writeFloat(entry.getValue().getLocation().getLatitude());
		writeFloat(entry.getValue().getLocation().getAltitude());
		writeFloat(entry.getValue().getMeasures().getAcceleration_X());
		writeFloat(entry.getValue().getMeasures().getAcceleration_Y());
		writeFloat(entry.getValue().getMeasures().getAcceleration_Z());
		writeFloat(entry.getValue().getMeasures().getRotation_X());
		writeFloat(entry.getValue().getMeasures().getRotation_Y());
		writeFloat(entry.getValue().getMeasures().getRotation_Z());
		writeFloat(entry.getValue().getMeasures().getNorth());
		writeFloat(entry.getValue().getMeasures().getTop_temperature());
		writeFloat(entry.getValue().getMeasures().getTop_humidity());
		writeFloat(entry.getValue().getMeasures().getTop_light());
		writeFloat(entry.getValue().getMeasures().getTop_ir());
		writeFloat(entry.getValue().getMeasures().getBottom_temperature());
		writeFloat(entry.getValue().getMeasures().getBottom_humidity());
		writeFloat(entry.getValue().getMeasures().getBottom_light());
		writeFloat(entry.getValue().getMeasures().getBottom_ir());
		writeFloat(entry.getValue().getMeasures().getTelemetry_front());
		writeFloat(entry.getValue().getMeasures().getTelemetry_back());
		writeFloat(entry.getValue().getMeasures().getTelemetry_left());
		writeFloat(entry.getValue().getMeasures().getTelemetry_right());
	}
}
