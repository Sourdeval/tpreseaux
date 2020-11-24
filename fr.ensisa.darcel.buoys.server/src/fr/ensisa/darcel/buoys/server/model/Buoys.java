package fr.ensisa.darcel.buoys.server.model;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Buoys {

	static private long ID = 0;
	static private Random rnd = new Random();

	private Map<Long, Buoy> buoys;

	static private boolean isTrue () {
		int v = rnd.nextInt(100);
		return v > 50;
	}

	static private Buoy createBuoy (String who, Usage usage) {
		Buoy buoy = new Buoy();
		buoy.setWho(who);
		buoy.setUsage(usage);
		Sensors s = new Sensors ();
		buoy.setSensors(s);
		s.setSensor3DAcceleration(isTrue());
		s.setSensor3DRotation(isTrue());
		s.setSensorNorth(isTrue());
		s.setSensorTop(isTrue());
		s.setSensorBottom(isTrue());
		s.setSensorTelemetry(isTrue());
		return buoy;
	}

	private void feed () {
		add(createBuoy ("Ensisa", Usage.UNUSED));
		add(createBuoy ("Ensisa", Usage.READY));
		add(createBuoy ("Ensisa", Usage.WORKING));
		add(createBuoy ("Enscmu", Usage.BACK));
		add(createBuoy ("UHA", Usage.READY));
		add(createBuoy ("UHA", Usage.WORKING));
	}

	public Map<Long, Buoy> getBuoys() {
		if (buoys == null) {
			buoys = new TreeMap<>();
			feed();
		}
		return buoys;
	}

	public Buoy getById (long id) {
		if (! getBuoys().containsKey(id)) return null;
		return getBuoys().get(id);
	}

	public long add(Buoy buoy) {
		long id = ++ID;
		buoy.setId(id);
		getBuoys().put(id, buoy);
		return id;
	}

	public long update(Buoy buoy) {
		long id = buoy.getId();
		if (! getBuoys().containsKey(id)) return -1;
		getBuoys().put(id, buoy);
		return id;
	}

	public boolean remove(long id) {
		getBuoys().remove(id);
		return true;
	}

}
