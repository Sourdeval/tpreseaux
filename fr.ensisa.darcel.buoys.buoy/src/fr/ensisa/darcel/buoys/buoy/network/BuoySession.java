package fr.ensisa.darcel.buoys.buoy.network;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import fr.ensisa.darcel.buoys.buoy.model.Buoy;
import fr.ensisa.darcel.buoys.buoy.model.BuoyData;
import fr.ensisa.darcel.buoys.buoy.model.Version;
import fr.ensisa.darcel.buoys.buoy.model.Battery.Plug;
import fr.ensisa.darcel.buoys.network.Protocol;

public class BuoySession implements ISession {

    private Socket tcp;
    private String host;
    private int portTCP;
    private int portUDP;

    public BuoySession(String host, int portTCP, int portUDP) {
    	this.host = host;
    	this.portTCP = portTCP;
    	this.portUDP = portUDP;
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
            tcp = new Socket(this.host, this.portTCP);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

	@Override
	public Buoy doGetBuoy (long id) {
        try {
        	BuoyWriter w = new BuoyWriter(tcp.getOutputStream());
            w.createGetBuoy(id);
            w.send();
            BuoyReader r = new BuoyReader(tcp.getInputStream());
            r.receive();
            if (r.getType() == Protocol.REPLY_KO) {
                return null;
            }
    		return null;
        } catch (IOException e) {
    		return null;
        }
	}

	@Override
	public Version doUpdateVersion(Version previousVersion) {
        try {
        	BuoyWriter w = new BuoyWriter(tcp.getOutputStream());
            w.createUpdateVersion(previousVersion.getNumber().get());
            w.send();
            BuoyReader r = new BuoyReader(tcp.getInputStream());
            r.receive();
            if (r.getType() == Protocol.REPLY_KO) {
                return null;
            }
    		return null;
        } catch (IOException e) {
    		return null;
        }
	}

	@Override
	public Boolean doSendAllData(List<BuoyData> data) {
        try {
        	BuoyWriter w = new BuoyWriter(tcp.getOutputStream());
            w.createSendData(data);
            w.send();
            BuoyReader r = new BuoyReader(tcp.getInputStream());
            r.receive();
            if (r.getType() == Protocol.REPLY_KO) {
                return false;
            }
    		return null;
        } catch (IOException e) {
    		return null;
        }
	}

	private enum Decision {
		UDP_STD1,
		UDP_STD2,
		UDP_SERVICE,
	}

	private Decision lastDecision = null;

	private Decision decisionTaker (BuoyData tick) {
		return Decision.UDP_SERVICE;
	}

	@Override
	public Boolean doSendTick(BuoyData tick) {
        try {
            UDPWriter w = new UDPWriter(host, portUDP);
            switch (decisionTaker(tick)) {
            case UDP_STD1		: w.createUDPStd1 (tick); break;
            case UDP_STD2		: w.createUDPStd2 (tick); break;
            case UDP_SERVICE	: w.createUDPService (tick); break;
            }
            w.send();
            return true;
        } catch (IOException e) {
        	return null;
        }
	}

}
