package fr.ensisa.darcel.buoys.buoy.network;

import java.util.List;

import fr.ensisa.darcel.buoys.buoy.model.Buoy;
import fr.ensisa.darcel.buoys.buoy.model.BuoyData;
import fr.ensisa.darcel.buoys.buoy.model.Version;

/**
 *
 * @author hassenforder
 */
public interface ISession {

    boolean open ();
    boolean close ();

	Buoy doGetBuoy(long id);
	Version doUpdateVersion(Version previousVersion);
	Boolean doSendAllData(List<BuoyData> data);
	Boolean doSendTick(BuoyData tick);

}
