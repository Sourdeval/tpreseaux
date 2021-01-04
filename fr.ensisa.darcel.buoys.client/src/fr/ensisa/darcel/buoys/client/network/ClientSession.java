<<<<<<< HEAD
package fr.ensisa.darcel.buoys.client.network;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import fr.ensisa.darcel.buoys.client.model.Buoy;
import fr.ensisa.darcel.buoys.client.model.BuoyData;
import fr.ensisa.darcel.buoys.network.Protocol;

public class ClientSession implements ISession {

    private Socket tcp;
    private String host;
    private int port;

    public ClientSession(String host, int port) {
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
        	ClientWriter w = new ClientWriter(tcp.getOutputStream());
            w.createGetBuoyList(who);
            w.send();
            ClientReader r = new ClientReader(tcp.getInputStream());
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
	public Buoy doGetBuoy(long id) {
        try {
        	ClientWriter w = new ClientWriter(tcp.getOutputStream());
            w.createGetBuoy(id);
            w.send();
            ClientReader r = new ClientReader(tcp.getInputStream());
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
	public BuoyData doGetBuoyLastTick(long id) {
        try {
        	ClientWriter w = new ClientWriter(tcp.getOutputStream());
            w.createGetBuoyLastTick(id);
            w.send();
            ClientReader r = new ClientReader(tcp.getInputStream());
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
	public List<BuoyData> doGetBuoyData(long id, boolean tickIncluded, boolean measuresIncluded) {
        try {
        	ClientWriter w = new ClientWriter(tcp.getOutputStream());
            w.createGetBuoyData(id, tickIncluded, measuresIncluded);
            w.send();
            ClientReader r = new ClientReader(tcp.getInputStream());
            r.receive();
            if (r.getType() == Protocol.REPLY_KO) {
                return null;
            }
    		return null;
        } catch (IOException e) {
    		return null;
        }
	}

}
=======
package fr.ensisa.darcel.buoys.client.network;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import fr.ensisa.darcel.buoys.client.model.Buoy;
import fr.ensisa.darcel.buoys.client.model.BuoyData;
import fr.ensisa.darcel.buoys.network.Protocol;

public class ClientSession implements ISession {

    private Socket tcp;
    private String host;
    private int port;

    public ClientSession(String host, int port) {
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
        	ClientWriter w = new ClientWriter(tcp.getOutputStream());
            w.createGetBuoyList(who);
            w.send();
            ClientReader r = new ClientReader(tcp.getInputStream());
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
	public Buoy doGetBuoy(long id) {
        try {
        	ClientWriter w = new ClientWriter(tcp.getOutputStream());
            w.createGetBuoy(id);
            w.send();
            ClientReader r = new ClientReader(tcp.getInputStream());
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
	public BuoyData doGetBuoyLastTick(long id) {
        try {
        	ClientWriter w = new ClientWriter(tcp.getOutputStream());
            w.createGetBuoyLastTick(id);
            w.send();
            ClientReader r = new ClientReader(tcp.getInputStream());
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
	public List<BuoyData> doGetBuoyData(long id, boolean tickIncluded, boolean measuresIncluded) {
        try {
        	ClientWriter w = new ClientWriter(tcp.getOutputStream());
            w.createGetBuoyData(id, tickIncluded, measuresIncluded);
            w.send();
            ClientReader r = new ClientReader(tcp.getInputStream());
            r.receive();
            if (r.getType() == Protocol.REPLY_KO) {
                return null;
            }
    		return null;
        } catch (IOException e) {
    		return null;
        }
	}

}
>>>>>>> branch 'master' of https://github.com/Sourdeval/tpreseaux
