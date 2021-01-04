<<<<<<< HEAD
package fr.ensisa.darcel.buoys.client.model;

import java.util.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BuoyData {
	private ObjectProperty<Date> date;
	private ObjectProperty<Location> location;
	private StringProperty state;
	private StringProperty type;
	private ObjectProperty<Battery> battery;
	private ObjectProperty<Measures> measures;
	private StringProperty data;

	public BuoyData() {
		super();
		this.date = new SimpleObjectProperty<Date>();
		this.location = new SimpleObjectProperty<Location>();
		this.state = new SimpleStringProperty();
		this.type = new SimpleStringProperty();
		this.battery = new SimpleObjectProperty<Battery>();
		this.measures = new SimpleObjectProperty<Measures>();
		this.data = new SimpleStringProperty();
	}

	public BuoyData(long date, Location location, String state, Battery battery) {
		this();
		this.date.set(new Date(date));
		this.location.set(location);
		this.state.set(state);
		this.type.set("TICK");
		this.battery.set(battery);
		this.data.set(buildData());
	}
	public BuoyData(long date, Location location, Measures measures) {
		this();
		this.date.set(new Date(date));
		this.location.set(location);
		this.type.set("MEASURES");
		this.measures.set(measures);
		this.data.set(buildData());
	}
	public ObjectProperty<Date> getDate() {
		return date;
	}
	public ObjectProperty<Location> getLocation() {
		return location;
	}
	public StringProperty getState() {
		return state;
	}
	public StringProperty getType() {
		return type;
	}
	public ObjectProperty<Battery> getBattery() {
		return battery;
	}
	public ObjectProperty<Measures> getMeasures() {
		return measures;
	}
	public StringProperty getData() {
		return data;
	}

	public void setWith (BuoyData data) {
		if (data != null) {
			this.date.set(data.getDate().get());
			this.location.set(data.getLocation().get());
			this.state.set(data.getState().get());
			this.type.set(data.getType().get());
			this.battery.set(data.getBattery().get());
			this.measures.set(data.getMeasures().get());
		} else {
			this.date.set(null);
			this.location.set(null);
			this.state.set(null);
			this.type.set(null);
			this.battery.set(null);
			this.measures.set(null);
		}
		this.data.set(buildData());
	}

	private String buildData() {
		if (getMeasures().get() != null) return getMeasures().get().getValue();
		StringBuilder tmp = new StringBuilder();
		tmp.append(getState().get());
		if (getBattery().get() != null) {
			tmp.append(" ");
			tmp.append(getBattery().get().toString());
		}
		return tmp.toString();
	}

}
=======
package fr.ensisa.darcel.buoys.client.model;

import java.util.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BuoyData {
	private ObjectProperty<Date> date;
	private ObjectProperty<Location> location;
	private StringProperty state;
	private StringProperty type;
	private ObjectProperty<Battery> battery;
	private ObjectProperty<Measures> measures;
	private StringProperty data;

	public BuoyData() {
		super();
		this.date = new SimpleObjectProperty<Date>();
		this.location = new SimpleObjectProperty<Location>();
		this.state = new SimpleStringProperty();
		this.type = new SimpleStringProperty();
		this.battery = new SimpleObjectProperty<Battery>();
		this.measures = new SimpleObjectProperty<Measures>();
		this.data = new SimpleStringProperty();
	}

	public BuoyData(long date, Location location, String state, Battery battery) {
		this();
		this.date.set(new Date(date));
		this.location.set(location);
		this.state.set(state);
		this.type.set("TICK");
		this.battery.set(battery);
		this.data.set(buildData());
	}
	public BuoyData(long date, Location location, Measures measures) {
		this();
		this.date.set(new Date(date));
		this.location.set(location);
		this.type.set("MEASURES");
		this.measures.set(measures);
		this.data.set(buildData());
	}
	public ObjectProperty<Date> getDate() {
		return date;
	}
	public ObjectProperty<Location> getLocation() {
		return location;
	}
	public StringProperty getState() {
		return state;
	}
	public StringProperty getType() {
		return type;
	}
	public ObjectProperty<Battery> getBattery() {
		return battery;
	}
	public ObjectProperty<Measures> getMeasures() {
		return measures;
	}
	public StringProperty getData() {
		return data;
	}

	public void setWith (BuoyData data) {
		if (data != null) {
			this.date.set(data.getDate().get());
			this.location.set(data.getLocation().get());
			this.state.set(data.getState().get());
			this.type.set(data.getType().get());
			this.battery.set(data.getBattery().get());
			this.measures.set(data.getMeasures().get());
		} else {
			this.date.set(null);
			this.location.set(null);
			this.state.set(null);
			this.type.set(null);
			this.battery.set(null);
			this.measures.set(null);
		}
		this.data.set(buildData());
	}

	private String buildData() {
		if (getMeasures().get() != null) return getMeasures().get().getValue();
		StringBuilder tmp = new StringBuilder();
		tmp.append(getState().get());
		if (getBattery().get() != null) {
			tmp.append(" ");
			tmp.append(getBattery().get().toString());
		}
		return tmp.toString();
	}

}
>>>>>>> branch 'master' of https://github.com/Sourdeval/tpreseaux
