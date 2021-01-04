package fr.ensisa.darcel.buoys.service.model;

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

	public Battery(int level, int temperature, int load, Plug plug, int discharge, int cycleCount) {
		super();
		this.level = level;
		this.temperature = temperature;
		this.load = load;
		this.plug = plug;
		this.discharge = discharge;
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

}
