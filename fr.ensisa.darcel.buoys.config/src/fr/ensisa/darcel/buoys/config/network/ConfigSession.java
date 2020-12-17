package fr.ensisa.darcel.buoys.config.network;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import fr.ensisa.darcel.buoys.config.model.Buoy;
import fr.ensisa.darcel.buoys.config.model.Version;
import fr.ensisa.darcel.buoys.network.Protocol;

public class ConfigSession implements ISession {

    private Socket tcp;
    private String host;
    private int port;

    public ConfigSession(String host, int port) {
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
	public Version doReceiveCurrentVersion() {
        try {
        	ConfigWriter w = new ConfigWriter(tcp.getOutputStream());
            w.createReceiveCurrentVersion();
            w.send();
            ConfigReader r = new ConfigReader(tcp.getInputStream());
            r.receive();
			if (r.getType() == Protocol.REPLY_DO_RECEIVE_CURRENT)
			{
				Version version= r.readCurrentVersion();
				return version;
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
	public Boolean doSendNewVersion(Version version) {
        try {
        	ConfigWriter w = new ConfigWriter(tcp.getOutputStream());
            w.createNewVersion(version);
            w.send();
            ConfigReader r = new ConfigReader(tcp.getInputStream());
            r.receive();
            if(r.getType() == Protocol.REPLY_DO_SEND_NEW)
            {
            	return true;
            }
            if (r.getType() == Protocol.REPLY_KO) {
                return false;
            }
    		return null;
        } catch (IOException e) {
    		return null;
        }
	}

	@Override
	public List<Buoy> doGetBuoyList(String who) {
        try {
        	ConfigWriter w = new ConfigWriter(tcp.getOutputStream());
            w.createGetBuoyList(who);
            w.send();
            ConfigReader r = new ConfigReader(tcp.getInputStream());
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
        	ConfigWriter w = new ConfigWriter(tcp.getOutputStream());
            w.createGetBuoy(id);
            w.send();
            ConfigReader r = new ConfigReader(tcp.getInputStream());
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
	public Long doCreateBuoy(Buoy buoy) {
        try {
        	ConfigWriter w = new ConfigWriter(tcp.getOutputStream());
            w.createCreateBuoy(buoy);
            w.send();
            ConfigReader r = new ConfigReader(tcp.getInputStream());
            r.receive();
            if(r.getType() == Protocol.REPLY_DO_CREATE_BUOY)
            {
            	return r.readCreated();

            }
            if (r.getType() == Protocol.REPLY_KO) {
                return new Long(-1);
            }
    		return null;
        } catch (IOException e) {
    		return null;
        }
	}

	@Override
	public Boolean doUpdateBuoy(Buoy buoy) {
        try {
        	ConfigWriter w = new ConfigWriter(tcp.getOutputStream());
            w.createUpdateBuoy(buoy);
            w.send();
            ConfigReader r = new ConfigReader(tcp.getInputStream());
            r.receive();
            if (r.getType() == Protocol.REPLY_OK) {
                return true;
            }
            if (r.getType() == Protocol.REPLY_KO) {
                return false;
            }
    		return null;
        } catch (IOException e) {
    		return null;
        }
	}

	@Override
	public Boolean doDeleteBuoy(long id) {
        try {
        	ConfigWriter w = new ConfigWriter(tcp.getOutputStream());
            w.createDeleteBuoy(id);
            w.send();
            ConfigReader r = new ConfigReader(tcp.getInputStream());
            r.receive();

            if(r.getType() == Protocol.REPLY_DO_DELETE)
            {
            	return r.readDeleted();
            }
            if (r.getType() == Protocol.REPLY_KO) {
                return false;
            }

    		return null;
        } catch (IOException e) {
    		return null;
        }
	}

	@Override
	public Boolean doClearDataBuoy(long id) {
        try {
        	ConfigWriter w = new ConfigWriter(tcp.getOutputStream());
            w.createClearDataBuoy(id);
            w.send();
            ConfigReader r = new ConfigReader(tcp.getInputStream());
            r.receive();
            if (r.getType() == Protocol.REPLY_OK) {
                return true;
            }
            if (r.getType() == Protocol.REPLY_KO) {
                return false;
            }
    		return null;
        } catch (IOException e) {
    		return null;
        }
	}

}
