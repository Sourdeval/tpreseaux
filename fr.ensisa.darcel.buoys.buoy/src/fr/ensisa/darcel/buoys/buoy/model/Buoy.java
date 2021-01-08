package fr.ensisa.darcel.buoys.buoy.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Buoy {

	private final LongProperty id;
	private final Version version;
	private final StringProperty who;
	private final ObjectProperty<Usage> usage;
	private final Sensors sensors;

	public Buoy() {
		super();
		this.id = new SimpleLongProperty(0);
		this.version = new Version();
		this.who= new SimpleStringProperty();
		this.usage = new SimpleObjectProperty<Usage>(Usage.UNUSED);
		this.sensors = new Sensors();
	}

	public LongProperty getId() {
		return id;
	}

	public Version getVersion() {
		return version;
	}

	public StringProperty getWho() {
		return who;
	}

	public ObjectProperty<Usage> getUsage() {
		return usage;
	}

	public Sensors getSensors() {
		return sensors;
	}

	public void setWith (Buoy buoy) {
		this.getId().set(buoy.getId().get());
		this.getVersion().setWith(buoy.getVersion());
		this.getUsage().set(buoy.getUsage().get());
		this.getWho().set(buoy.getWho().get());
		this.getSensors().setWith(buoy.getSensors());
	}

}
