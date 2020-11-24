package fr.ensisa.darcel.buoys.config.network;

import java.util.List;

import fr.ensisa.darcel.buoys.config.model.Buoy;
import fr.ensisa.darcel.buoys.config.model.Version;

/**
 *
 * @author hassenforder
 */
public interface ISession {

    boolean open ();
    boolean close ();

	Version doReceiveCurrentVersion();
    Boolean doSendNewVersion (Version version);

    List<Buoy> doGetBuoyList(String who);
	Buoy doGetBuoy(long id);
	Long doCreateBuoy (Buoy buoy);
	Boolean doUpdateBuoy (Buoy buoy);
	Boolean doDeleteBuoy (long id);
	Boolean doClearDataBuoy(long id);

}
