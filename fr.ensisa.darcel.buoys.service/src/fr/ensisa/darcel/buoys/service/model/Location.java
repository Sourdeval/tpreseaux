package fr.ensisa.darcel.buoys.service.model;

public class Location {
	private float longitude;
	private float latitude;
	private float altitude;

	public Location(float longitude, float latitude, float altitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.altitude = altitude;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("@ ");
		builder.append(longitude);
		builder.append(", ");
		builder.append(latitude);
		builder.append(", ");
		builder.append(altitude);
		return builder.toString();
	}


}
