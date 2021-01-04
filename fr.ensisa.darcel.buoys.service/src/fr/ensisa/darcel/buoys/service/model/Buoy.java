package fr.ensisa.darcel.buoys.service.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Buoy {

	private final LongProperty id;
	private final StringProperty version;
	private final StringProperty who;
	private final ObjectProperty<Usage> usage;
	private final Sensors sensors;

	public Buoy() {
		super();
		this.id = new SimpleLongProperty(0);
		this.version = new SimpleStringProperty();
		this.who= new SimpleStringProperty();
		this.usage = new SimpleObjectProperty<Usage>(Usage.UNUSED);
		this.sensors = new Sensors();
	}

	public LongProperty getId() {
		return id;
	}

	public StringProperty getVersion() {
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
		if (buoy == null) {
			this.getId().set(0);
			this.getVersion().set(null);
			this.getUsage().set(Usage.UNUSED);
			this.getWho().set(null);
			this.getSensors().setWith(null);
		} else {
			this.getId().set(buoy.getId().get());
			this.getVersion().set(buoy.getVersion().get());
			this.getUsage().set(buoy.getUsage().get());
			this.getWho().set(buoy.getWho().get());
			this.getSensors().setWith(buoy.getSensors());
		}
	}

}
