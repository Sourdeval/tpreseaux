package fr.ensisa.darcel.buoys.buoy.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Sensors {

	private final BooleanProperty sensor3DAcceleration;
	private final BooleanProperty sensor3DRotation;
	private final BooleanProperty sensorNorth;
	private final BooleanProperty sensorTop;
	private final BooleanProperty sensorBottom;
	private final BooleanProperty sensorTelemetry;
	private final StringProperty asString;

	public Sensors() {
		super();
		sensor3DAcceleration = new SimpleBooleanProperty(true);
		sensor3DRotation = new SimpleBooleanProperty(false);
		sensorNorth = new SimpleBooleanProperty(false);
		sensorTop = new SimpleBooleanProperty(false);
		sensorBottom = new SimpleBooleanProperty(true);
		sensorTelemetry = new SimpleBooleanProperty(false);
		asString = new SimpleStringProperty();
	}

	public BooleanProperty getSensor3DAcceleration() {
		return sensor3DAcceleration;
	}

	public BooleanProperty getSensor3DRotation() {
		return sensor3DRotation;
	}

	public BooleanProperty getSensorNorth() {
		return sensorNorth;
	}

	public BooleanProperty getSensorTop() {
		return sensorTop;
	}

	public BooleanProperty getSensorBottom() {
		return sensorBottom;
	}

	public BooleanProperty getSensorTelemetry() {
		return sensorTelemetry;
	}

	public void setWith (Sensors sensors) {
		this.getSensor3DAcceleration().set(sensors.getSensor3DAcceleration().get());
		this.getSensor3DRotation().set(sensors.getSensor3DRotation().get());
		this.getSensorNorth().set(sensors.getSensorNorth().get());
		this.getSensorTop().set(sensors.getSensorTop().get());
		this.getSensorBottom().set(sensors.getSensorBottom().get());
		this.getSensorTelemetry().set(sensors.getSensorTelemetry().get());
	}

	public StringProperty asString() {
		if (asString.get() == null) {
			asString.set(toString());
		}
		return asString;
	}

	@Override
	public String toString() {
		StringBuffer tmp = new StringBuffer();
		if (sensor3DAcceleration.get()) {
			tmp.append("A");
		}
		if (sensor3DRotation.get()) {
			tmp.append("R");
		}
		if (sensorNorth.get()) {
			tmp.append("N");
		}
		if (sensorTop.get()) {
			tmp.append("T");
		}
		if (sensorBottom.get()) {
			tmp.append("B");
		}
		if (sensorTelemetry.get()) {
			tmp.append("M");
		}
		String result = tmp.toString();
		return result.isEmpty() ? "-" : result;
	}


}
