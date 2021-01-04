package fr.ensisa.darcel.buoys.service.network;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import fr.ensisa.darcel.buoys.service.model.Buoy;
import fr.ensisa.darcel.buoys.service.model.BuoyData;
import fr.ensisa.darcel.buoys.network.Protocol;

public class ServiceSession implements ISession {

    private Socket tcp;
    private String host;
    private int port;

    public ServiceSession(String host, int port) {
    	this.host = host;
    	this.port = port;
    }

    @Override
    synchronized public boolean close() {
        try {
            if (tcp != null) {
                tcp.close();
            }
            tcp = null;
        } catch (IOException e) {
        }
        return true;
    }

    @Override
    synchronized public boolean open() {
        this.close();
        try {
            tcp = new Socket(this.host, this.port);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

	@Override
	public List<Buoy> doGetBuoyList(String who) {
        try {
            ServiceWriter w = new ServiceWriter(tcp.getOutputStream());
            w.createGetBuoyList(who);
            w.send();
            ServiceReader r = new ServiceReader(tcp.getInputStream());
            r.receive();
            if(r.getType() == Protocol.REPLY_DO_GET_BUOY_LIST)
            {

            	return r.readBuoyList();
            }
            if (r.getType() == Protocol.REPLY_KO) {
                return null;
            }
    		return null;
        } catch (IOException e) {
    		return null;
        }
	}

	@Override
	public Buoy doGetBuoy (long id) {
        try {
        	ServiceWriter w = new ServiceWriter(tcp.getOutputStream());
            w.createGetBuoy(id);
            w.send();
            ServiceReader r = new ServiceReader(tcp.getInputStream());
            r.receive();
            if(r.getType() == Protocol.REPLY_DO_GET_BUOY)
            {
            	return r.readBuoy();

            }
            if (r.getType() == Protocol.REPLY_KO) {
                return null;
            }
    		return null;
        } catch (IOException e) {
    		return null;
        }
	}

	@Override
	public BuoyData doGetBuoyLastTick(long id) {
        try {
            ServiceWriter w = new ServiceWriter(tcp.getOutputStream());
            w.createGetBuoyLastTick(id);
            w.send();
            ServiceReader r = new ServiceReader(tcp.getInputStream());
            r.receive();
            if(r.getType() == Protocol.REPLY_DO_GET_BUOY_LAST_TICK)
            {
            	return r.readLastTick();

            }
            if (r.getType() == Protocol.REPLY_KO) {
                return null;
            }
    		return null;
        } catch (IOException e) {
    		return null;
        }
	}

}
