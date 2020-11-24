package fr.ensisa.darcel.buoys.server.model;

import java.util.Date;

public class BuoyData {

	private enum Type {
		TICK,
		MEASURES,
	}

	private Date date;
	private long id;
	private Type type;
	private Location location;
	private State state;
	private Battery battery;
	private Measures measures;

	public BuoyData() {
	}

	//Tick ctor
	public BuoyData(long date, long id, Location location, State state, Battery battery) {
		this();
		this.date = new Date(date);
		this.id = id;
		this.type = Type.TICK;
		this.location = location;
		this.state = state;
		this.battery = battery;
	}

	//Measures ctor
	public BuoyData(long date, long id, Location location, Measures measures) {
		this();
		this.date = new Date(date);
		this.id = id;
		this.type = Type.MEASURES;
		this.location = location;
		this.measures = measures;
	}

	public Date getDate() {
		return date;
	}

	public long getId() {
		return id;
	}

	public Location getLocation() {
		return location;
	}

	public State getState() {
		return state;
	}

	public Battery getBattery() {
		return battery;
	}

	public Measures getMeasures() {
		return measures;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BuoyData [date=");
		builder.append(date);
		builder.append(", id=");
		builder.append(id);
		builder.append(", location=");
		builder.append(location);
		builder.append(", state=");
		builder.append(state);
		builder.append(", battery=");
		builder.append(battery);
		builder.append(", measures=");
		builder.append(measures);
		builder.append("]");
		return builder.toString();
	}

	public boolean isTick() {
		return type == Type.TICK;
	}

	public Enum<Type> getType() {
		return type;
	}

}
