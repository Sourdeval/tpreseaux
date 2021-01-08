package fr.ensisa.darcel.buoys.buoy.network;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

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
		writeInt(Protocol.REQUEST_DO_UPDATE_VERSION);
		writeString(versionNumber);
	}

	public void createSendData(List<BuoyData> data) {
		writeInt(Protocol.REQUEST_DO_SEND_DATA);
		writeInt(data.size());
		for (int i = 0; i<data.size(); i++){
			BuoyData temp = data.get(i);
			writeLong(temp.getDate().getTime());
			writeLong(temp.getId());
			writeFloat(temp.getLocation().getLongitude());
			writeFloat(temp.getLocation().getLatitude());
			writeFloat(temp.getLocation().getAltitude());
			if (temp.getMeasures() != null){
				writeBoolean(true);
				writeMeasures(temp);
			}
			else {
				writeBoolean(false);
			}
			if (temp.getBattery() != null){
				writeBoolean(true);
				switch (temp.getBattery().getPlug().get()) {
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
				writeInt(temp.getBattery().getLevel().get());
				writeInt(temp.getBattery().getTemperature().get());
				writeInt(temp.getBattery().getLoad().get());

				writeInt(temp.getBattery().getDischarge().get());
				writeInt(temp.getBattery().getCycleCount().get());
			}
			else {
				writeBoolean(false);
			}
			if (temp.getState() != null){
				writeBoolean(true);
				writeInt(temp.getState().getState());
				writeInt(temp.getState().getDetail());
			}
			else {
				writeBoolean(false);
			}
		}
	}
	public void writeMeasures(BuoyData b){
		writeFloat(b.getMeasures().getAcceleration_X());
		writeFloat(b.getMeasures().getAcceleration_Y());
		writeFloat(b.getMeasures().getAcceleration_Z());
		writeFloat(b.getMeasures().getRotation_X());
		writeFloat(b.getMeasures().getRotation_Y());
		writeFloat(b.getMeasures().getRotation_Z());
		writeFloat(b.getMeasures().getNorth());
		writeFloat(b.getMeasures().getTop_temperature());
		writeFloat(b.getMeasures().getTop_humidity());
		writeFloat(b.getMeasures().getTop_light());
		writeFloat(b.getMeasures().getTop_ir());
		writeFloat(b.getMeasures().getBottom_temperature());
		writeFloat(b.getMeasures().getBottom_humidity());
		writeFloat(b.getMeasures().getBottom_light());
		writeFloat(b.getMeasures().getBottom_ir());
		writeFloat(b.getMeasures().getTelemetry_front());
		writeFloat(b.getMeasures().getTelemetry_back());
		writeFloat(b.getMeasures().getTelemetry_left());
		writeFloat(b.getMeasures().getTelemetry_right());
	}

}
