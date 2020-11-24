package fr.ensisa.darcel.buoys.server.model;

public class Battery {

	public enum Plug {
		DISCONNECTED,
		CHARGING_SLOW,
		CHARGING_FAST,
	}
	private int level;
	private int temperature;
	private int load;
	private Plug plug;
	private int discharge;
	private int cycleCount;

	public Battery() {
		super();
	}

	public Battery(int level, int temperature, int load, Plug plug, int discharge, int cycleCount) {
		this();
		this.level = level;
		this.temperature = temperature;
		this.load = load;
		this.plug = plug;
		this.discharge = discharge;
		this.cycleCount = cycleCount;
	}


	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public int getLoad() {
		return load;
	}

	public void setLoad(int load) {
		this.load = load;
	}

	public Plug getPlug() {
		return plug;
	}

	public void setPlug(Plug plug) {
		this.plug = plug;
	}

	public int getDischarge() {
		return discharge;
	}

	public void setDischarge(int discharge) {
		this.discharge = discharge;
	}

	public int getCycleCount() {
		return cycleCount;
	}

	public void setCycleCount(int cycleCount) {
		this.cycleCount = cycleCount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("L=");
		builder.append(level);
		builder.append("T=");
		builder.append(temperature);
		builder.append("°, C=");
		builder.append(load);
		builder.append("mA, P=");
		if (plug != null) {
			switch (plug) {
			case CHARGING_FAST: builder.append("F"); break;
			case CHARGING_SLOW:	builder.append("S"); break;
			case DISCONNECTED:	builder.append("D"); break;
			default:			builder.append("X"); break;
			}
		} else {
			builder.append("?");
		}
		builder.append(", D=");
		builder.append(discharge);
		builder.append("mA, #=");
		builder.append(cycleCount);
		return builder.toString();
	}

	public void setWith(Battery battery) {
		throw new Error ("Not yet defined");
	}

}
