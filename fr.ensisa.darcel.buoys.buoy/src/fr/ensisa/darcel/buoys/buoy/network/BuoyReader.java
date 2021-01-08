package fr.ensisa.darcel.buoys.buoy.network;

import java.io.InputStream;

import fr.ensisa.darcel.buoys.buoy.model.Buoy;
import fr.ensisa.darcel.buoys.buoy.model.Usage;
import fr.ensisa.darcel.buoys.buoy.model.Version;
import fr.ensisa.darcel.buoys.network.Protocol;
import fr.ensisa.darcel.network.BasicAbstractReader;

public class BuoyReader extends BasicAbstractReader {

    public BuoyReader(InputStream inputStream) {
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

	public Buoy readBuoy() {
		Buoy buoy = new Buoy();
		String number = readString();
		String who = readString();
		long id = readLong();
		buoy.getVersion().setWith(new Version(number,null));
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
		return buoy;
	}

}
