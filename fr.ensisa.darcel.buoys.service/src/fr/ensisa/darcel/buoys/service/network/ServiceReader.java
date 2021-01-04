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
    public List<Buoy> readBuoyList() {
		List<Buoy> buoys = new ArrayList<Buoy>();
		int size = readInt();
		for (int i = 0; i<size;i++)
		{
			Buoy buoy = new Buoy();
			buoy.getId().set(readLong());
			buoy.getWho().set(readString());
			switch (readInt()) {
			case 1:
				buoy.getUsage().set(Usage.UNUSED);
				break;
			case 2:
				buoy.getUsage().set(Usage.READY);
				break;
			case 3:
				buoy.getUsage().set(Usage.WORKING);
				break;
			case 4:
				buoy.getUsage().set(Usage.BACK);
				break;
			default:
				break;
			}
			buoys.add(buoy);
		}

		return buoys;


	}
    public Buoy readBuoy() {

		Buoy buoy = new Buoy();
		String version = readString();
		String who = readString();
		long id = readLong();
		buoy.getVersion().set(version);
		buoy.getWho().set(who);
		buoy.getId().set(id);

		switch (readInt()) {
		case 1:
			buoy.getUsage().set(Usage.UNUSED);
			break;
		case 2:
			buoy.getUsage().set(Usage.READY);
			break;
		case 3:
			buoy.getUsage().set(Usage.WORKING);
			break;
		case 4:
			buoy.getUsage().set(Usage.BACK);
			break;
		default:
			break;
		}
		buoy.getSensors().getSensor3DAcceleration().set(readBoolean());
		buoy.getSensors().getSensor3DRotation().set(readBoolean());
		buoy.getSensors().getSensorBottom().set(readBoolean());
		buoy.getSensors().getSensorNorth().set(readBoolean());
		buoy.getSensors().getSensorTop().set(readBoolean());
		buoy.getSensors().getSensorTelemetry().set(readBoolean());
		readInt();

		//buoy.getDataCount().set(readInt());
		return buoy;
	}

	public BuoyData readLastTick() {
		BuoyData data = new BuoyData();
		data.getDate().set(new Date(readLong()));
		Location loc = new Location(readFloat(),readFloat(),readFloat());
		data.getLocation().set(loc);
		String state = Integer.toString(readInt());
		String details = Integer.toString(readInt());

		data.getState().set(state + " - " + details);
		Plug plug = null;
		switch (readInt()) {
		case 1:
			plug = Plug.DISCONNECTED;
			break;
		case 2:
			plug = Plug.CHARGING_SLOW;
			break;
		case 3:
			plug = Plug.CHARGING_FAST;
			break;
		default:
			break;
		}
		Battery battery = new Battery(readInt(),readInt(),readInt(),plug,readInt(),readInt());
		data.getBattery().set(battery);
		return data;
	}

}
