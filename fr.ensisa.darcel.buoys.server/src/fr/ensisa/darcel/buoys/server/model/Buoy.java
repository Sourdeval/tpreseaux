package fr.ensisa.darcel.buoys.server.model;

public class Buoy {

	private long id;
	private String version;
	private String who;
	private Usage usage;
	private Sensors sensors;

	public Buoy() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getWho() {
		return who;
	}

	public void setWho(String who) {
		this.who = who;
	}

	public Usage getUsage() {
		return usage;
	}

	public void setUsage(Usage usage) {
		this.usage = usage;
	}

	public Sensors getSensors() {
		return sensors;
	}

	public void setSensors(Sensors sensors) {
		this.sensors = sensors;
	}


}
