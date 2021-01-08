package fr.ensisa.darcel.buoys.buoy.model;

public class Location {

	private float longitude;
	private float latitude;
	private float altitude;

	public Location() {
		super();
	}

	public Location(float longitude, float latitude, float altitude) {
		this();
		this.longitude = longitude;
		this.latitude = latitude;
		this.altitude = altitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public float getAltitude() {
		return altitude;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("@ ");
		builder.append(String.format("%.3f", longitude));
		builder.append(", ");
		builder.append(String.format("%.3f", latitude));
		builder.append(", ");
		builder.append(String.format("%4.1f", altitude));
		return builder.toString();
	}

}
