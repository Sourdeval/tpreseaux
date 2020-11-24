package fr.ensisa.darcel.buoys.server.model;

public class Model {

	private Version lastVersion;
	private Buoys buoys;
	private BuoyDataTable buoyDataTable;

	public Version getLastVersion() {
		if (lastVersion == null) lastVersion = new Version();
		return lastVersion;
	}

	public void setLastVersion(Version newVersion) {
		lastVersion = newVersion;
	}

	public Buoys getBuoys() {
		if (buoys == null) buoys = new Buoys();
		return buoys;
	}

	public BuoyDataTable getBuoyDataTable() {
		if (buoyDataTable == null) buoyDataTable = new BuoyDataTable();
		return buoyDataTable;
	}

	public void populate() {
	}


}
