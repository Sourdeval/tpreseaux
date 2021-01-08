package fr.ensisa.darcel.buoys.buoy.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Battery {

	public enum Plug {
		DISCONNECTED,
		CHARGING_SLOW,
		CHARGING_FAST,
	}
	private final IntegerProperty level;
	private final IntegerProperty temperature;
	private final IntegerProperty load;
	private final ObjectProperty<Plug> plug;
	private final IntegerProperty discharge;
	private final IntegerProperty cycleCount;

	public Battery() {
		super();
		this.level = new SimpleIntegerProperty();
		this.temperature = new SimpleIntegerProperty();
		this.load = new SimpleIntegerProperty();
		this.plug = new SimpleObjectProperty<Plug>();
		this.discharge = new SimpleIntegerProperty();
		this.cycleCount = new SimpleIntegerProperty();
	}

	public Battery(int level, int temperature, int load, Plug plug, int discharge, int cycleCount) {
		this();
		this.level.set(level);
		this.temperature.set(temperature);
		this.load.set(load);
		this.plug.set(plug);
		this.discharge.set(discharge);
		this.cycleCount.set(cycleCount);
	}

	public IntegerProperty getLevel() {
		return level;
	}

	public IntegerProperty getTemperature() {
		return temperature;
	}

	public IntegerProperty getLoad() {
		return load;
	}

	public ObjectProperty<Plug> getPlug() {
		return plug;
	}

	public IntegerProperty getDischarge() {
		return discharge;
	}

	public IntegerProperty getCycleCount() {
		return cycleCount;
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
		if (plug.get() != null) {
			switch (plug.get()) {
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
