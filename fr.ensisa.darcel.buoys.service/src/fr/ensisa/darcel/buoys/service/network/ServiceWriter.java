<<<<<<< HEAD
package fr.ensisa.darcel.buoys.service.network;

import java.io.OutputStream;

import fr.ensisa.darcel.buoys.network.Protocol;
import fr.ensisa.darcel.network.BasicAbstractWriter;

public class ServiceWriter extends BasicAbstractWriter {

    public ServiceWriter(OutputStream outputStream) {
        super(outputStream);
    }

	public void createGetBuoyList(String who) {
	}

	public void createGetBuoy(long id) {
	}

	public void createGetBuoyLastTick(long id) {
	}

}
=======
package fr.ensisa.darcel.buoys.service.network;

import java.io.OutputStream;

import fr.ensisa.darcel.buoys.network.Protocol;
import fr.ensisa.darcel.network.BasicAbstractWriter;

public class ServiceWriter extends BasicAbstractWriter {

    public ServiceWriter(OutputStream outputStream) {
        super(outputStream);
    }

	public void createGetBuoyList(String who) {
	}

	public void createGetBuoy(long id) {
	}

	public void createGetBuoyLastTick(long id) {
	}

}
>>>>>>> branch 'master' of https://github.com/Sourdeval/tpreseaux
