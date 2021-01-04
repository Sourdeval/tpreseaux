package fr.ensisa.darcel.buoys.service.model;

import java.util.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BuoyData {
	private ObjectProperty<Date> date;
	private ObjectProperty<Location> location;
	private StringProperty state;
	private ObjectProperty<Battery> battery;

	public BuoyData() {
		super();
		this.date = new SimpleObjectProperty<Date>();
		this.location = new SimpleObjectProperty<Location>();
		this.state = new SimpleStringProperty();
		this.battery = new SimpleObjectProperty<Battery>();
	}

	public BuoyData(long date, Location location, String state, Battery battery) {
		this();
		this.date.set(new Date(date));
		this.location.set(location);
		this.state.set(state);
		this.battery.set(battery);
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
	public ObjectProperty<Battery> getBattery() {
		return battery;
	}

	public void setWith (BuoyData data) {
		if (data != null) {
			this.date.set(data.getDate().get());
			this.location.set(data.getLocation().get());
			this.state.set(data.getState().get());
			this.battery.set(data.getBattery().get());
		} else {
			this.date.set(null);
			this.location.set(null);
			this.state.set(null);
			this.battery.set(null);
		}
	}

}
