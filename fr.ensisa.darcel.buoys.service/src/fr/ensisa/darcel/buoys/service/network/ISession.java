package fr.ensisa.darcel.buoys.service.network;

import java.util.List;

import fr.ensisa.darcel.buoys.service.model.Buoy;
import fr.ensisa.darcel.buoys.service.model.BuoyData;

/**
 *
 * @author hassenforder
 */
public interface ISession {

    boolean open ();
    boolean close ();

	List<Buoy> doGetBuoyList(String who);
	BuoyData doGetBuoyLastTick(long id);
	Buoy doGetBuoy(long id);

}
