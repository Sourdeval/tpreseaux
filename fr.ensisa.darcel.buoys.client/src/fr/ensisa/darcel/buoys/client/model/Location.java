<<<<<<< HEAD
package fr.ensisa.darcel.buoys.client.model;

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
=======
package fr.ensisa.darcel.buoys.client.model;

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
>>>>>>> branch 'master' of https://github.com/Sourdeval/tpreseaux