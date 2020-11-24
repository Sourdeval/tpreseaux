package fr.ensisa.darcel.buoys.server.model;

public class Sensors {

	private boolean sensor3DAcceleration;
	private boolean sensor3DRotation;
	private boolean sensorNorth;
	private boolean sensorTop;
	private boolean sensorBottom;
	private boolean sensorTelemetry;

	public Sensors() {
		super();
	}

	public boolean isSensor3DAcceleration() {
		return sensor3DAcceleration;
	}

	public void setSensor3DAcceleration(boolean sensor3dAcceleration) {
		sensor3DAcceleration = sensor3dAcceleration;
	}

	public boolean isSensor3DRotation() {
		return sensor3DRotation;
	}

	public void setSensor3DRotation(boolean sensor3dRotation) {
		sensor3DRotation = sensor3dRotation;
	}

	public boolean isSensorNorth() {
		return sensorNorth;
	}

	public void setSensorNorth(boolean sensorNorth) {
		this.sensorNorth = sensorNorth;
	}

	public void setSensorTop(boolean sensorTop) {
		this.sensorTop = sensorTop;
	}

	public void setSensorBottom(boolean sensorBottom) {
		this.sensorBottom = sensorBottom;
	}

	public void setSensorTelemetry(boolean sensorTelemetry) {
		this.sensorTelemetry = sensorTelemetry;
	}

	public boolean isSensorTop() {
		return sensorTop;
	}

	public boolean isSensorBottom() {
		return sensorBottom;
	}

	public boolean isSensorTelemetry() {
		return sensorTelemetry;
	}

}
