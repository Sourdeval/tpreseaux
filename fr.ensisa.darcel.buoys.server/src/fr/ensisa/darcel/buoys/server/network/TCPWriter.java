package fr.ensisa.darcel.buoys.server.network;

import java.io.OutputStream;
import java.util.Collection;

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
/*
    public void createSendNew(Version v)
    {
    	writeInt(Protocol.REPLY_DO_RECEIVE_CURRENT);
    	writeString(v.getNumber());
    	int size=v.getContent().length;
    	writeInt(size);

    	for(int i=0;i<size;i++)
    	{
    		writeByte(v.getContent()[i]);
    	}
    }
*/

	public void createReplyDoSendNew() {
		writeInt(Protocol.REPLY_DO_SEND_NEW);
	}
}
