package fr.ensisa.darcel.buoys.client.network;

import java.util.List;

import fr.ensisa.darcel.buoys.client.model.Buoy;
import fr.ensisa.darcel.buoys.client.model.BuoyData;

/**
 *
 * @author hassenforder
 */
public interface ISession {

    boolean open ();
    boolean close ();

	List<Buoy> doGetBuoyList(String who);
	Buoy doGetBuoy(long id);
	BuoyData doGetBuoyLastTick(long id);
	List<BuoyData> doGetBuoyData(long id, boolean tickIncluded, boolean measuresIncluded);

}
