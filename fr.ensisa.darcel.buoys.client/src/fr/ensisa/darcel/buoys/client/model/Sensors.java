package fr.ensisa.darcel.buoys.client.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Sensors {

	private final BooleanProperty sensor3DAcceleration;
	private final BooleanProperty sensor3DRotation;
	private final BooleanProperty sensorNorth;
	private final BooleanProperty sensorTop;
	private final BooleanProperty sensorBottom;
	private final BooleanProperty sensorTelemetry;

	public Sensors() {
		super();
		sensor3DAcceleration = new SimpleBooleanProperty(true);
		sensor3DRotation = new SimpleBooleanProperty(false);
		sensorNorth = new SimpleBooleanProperty(false);
		sensorTop = new SimpleBooleanProperty(false);
		sensorBottom = new SimpleBooleanProperty(true);
		sensorTelemetry = new SimpleBooleanProperty(false);
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
		if (sensors == null) {
			this.getSensor3DAcceleration().set(true);
			this.getSensor3DRotation().set(false);
			this.getSensorNorth().set(false);
			this.getSensorTop().set(false);
			this.getSensorBottom().set(true);
			this.getSensorTelemetry().set(false);
		} else {
			this.getSensor3DAcceleration().set(sensors.getSensor3DAcceleration().get());
			this.getSensor3DRotation().set(sensors.getSensor3DRotation().get());
			this.getSensorNorth().set(sensors.getSensorNorth().get());
			this.getSensorTop().set(sensors.getSensorTop().get());
			this.getSensorBottom().set(sensors.getSensorBottom().get());
			this.getSensorTelemetry().set(sensors.getSensorTelemetry().get());
		}
	}

}
