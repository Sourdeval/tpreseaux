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
    	System.out.println("tcpwriterOK");
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
		System.out.println("replyGetBuoy");
		writeInt(Protocol.REPLY_DO_GET_BUOY);
		System.out.println(buoy.getVersion());
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
		System.out.println("tcpwriter");
		writeInt(Protocol.REPLY_DO_CREATE_BUOY);
		writeLong(id);
	}
}
