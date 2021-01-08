package fr.ensisa.darcel.buoys.buoy.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BuoyData {
	private Date date;
	private long id;
	private Location location;
	private State state;
	private Battery battery;
	private Measures measures;

	private final StringProperty asString;

	public BuoyData() {
		this.asString = new SimpleStringProperty();
	}

	//Tick ctor
	public BuoyData(long date, long id, Location location, State state, Battery battery) {
		this();
		this.date = new Date(date);
		this.id = id;
		this.location = location;
		this.state = state;
		this.battery = battery;
	}

	//Measures ctor
	public BuoyData(long date, long id, Location location, Measures measures) {
		this();
		this.date = new Date(date);
		this.id = id;
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

	public StringProperty asString() {
		if (asString.get() == null) {
			asString.set(toString());
		}
		return asString;
	}

	static SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("#");
		builder.append(id);
		builder.append(" ");
		builder.append(sdf.format(date));
		if (location != null) {
			builder.append(" ");
			builder.append(location);
		}
		if (state != null) {
			builder.append(" ");
			builder.append(state);
		}
		if (battery != null) {
			builder.append(" ");
			builder.append(battery);
		}
		if (measures != null) {
			builder.append(" ");
			builder.append(measures);
		}
		return builder.toString();
	}

}
