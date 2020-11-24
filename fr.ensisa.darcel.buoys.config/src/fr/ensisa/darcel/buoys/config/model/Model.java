package fr.ensisa.darcel.buoys.config.model;

public class Model {

	private Buoys buoys;
	private Version version;

	public Buoys getBuoys() {
		if (buoys == null) buoys = new Buoys();
		return buoys;
	}

	public Version getVersion() {
		if (version == null) version = new Version();
		return version;
	}

}
