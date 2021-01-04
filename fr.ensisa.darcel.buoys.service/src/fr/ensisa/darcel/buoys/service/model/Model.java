package fr.ensisa.darcel.buoys.service.model;

public class Model {

	private Buoys buoys;
	private BuoyData last;

	public Buoys getBuoys() {
		if (buoys == null) buoys = new Buoys();
		return buoys;
	}

	public BuoyData getLast() {
		if (last == null) last = new BuoyData();
		return last;
	}

}
