package fr.ensisa.darcel.buoys.buoy.model;

public class Measures {

	private final float acceleration_X;
	private final float acceleration_Y;
	private final float acceleration_Z;
	private final float rotation_X;
	private final float rotation_Y;
	private final float rotation_Z;
	private final float north;
	private final float top_temperature;
	private final float top_humidity;
	private final float top_light;
	private final float top_ir;
	private final float bottom_temperature;
	private final float bottom_humidity;
	private final float bottom_light;
	private final float bottom_ir;
	private final float telemetry_front;
	private final float telemetry_back;
	private final float telemetry_left;
	private final float telemetry_right;


	public Measures(float acceleration_X, float acceleration_Y, float acceleration_Z, float rotation_X,
			float rotation_Y, float rotation_Z, float north, float top_temperature, float top_humidity, float top_light,
			float top_ir, float bottom_temperature, float bottom_humidity, float bottom_light, float bottom_ir,
			float telemetry_front, float telemetry_back, float telemetry_left, float telemetry_right) {
		super();
		this.acceleration_X = acceleration_X;
		this.acceleration_Y = acceleration_Y;
		this.acceleration_Z = acceleration_Z;
		this.rotation_X = rotation_X;
		this.rotation_Y = rotation_Y;
		this.rotation_Z = rotation_Z;
		this.north = north;
		this.top_temperature = top_temperature;
		this.top_humidity = top_humidity;
		this.top_light = top_light;
		this.top_ir = top_ir;
		this.bottom_temperature = bottom_temperature;
		this.bottom_humidity = bottom_humidity;
		this.bottom_light = bottom_light;
		this.bottom_ir = bottom_ir;
		this.telemetry_front = telemetry_front;
		this.telemetry_back = telemetry_back;
		this.telemetry_left = telemetry_left;
		this.telemetry_right = telemetry_right;
	}

	public float getAcceleration_X() {
		return acceleration_X;
	}

	public float getAcceleration_Y() {
		return acceleration_Y;
	}

	public float getAcceleration_Z() {
		return acceleration_Z;
	}

	public float getRotation_X() {
		return rotation_X;
	}

	public float getRotation_Y() {
		return rotation_Y;
	}

	public float getRotation_Z() {
		return rotation_Z;
	}

	public float getNorth() {
		return north;
	}

	public float getTop_temperature() {
		return top_temperature;
	}

	public float getTop_humidity() {
		return top_humidity;
	}

	public float getTop_light() {
		return top_light;
	}

	public float getTop_ir() {
		return top_ir;
	}

	public float getBottom_temperature() {
		return bottom_temperature;
	}

	public float getBottom_humidity() {
		return bottom_humidity;
	}

	public float getBottom_light() {
		return bottom_light;
	}

	public float getBottom_ir() {
		return bottom_ir;
	}

	public float getTelemetry_front() {
		return telemetry_front;
	}

	public float getTelemetry_back() {
		return telemetry_back;
	}

	public float getTelemetry_left() {
		return telemetry_left;
	}

	public float getTelemetry_right() {
		return telemetry_right;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Acc[");
		builder.append(String.format("%4.0f",acceleration_X));
		builder.append(", ");
		builder.append(String.format("%4.0f",acceleration_Y));
		builder.append(", ");
		builder.append(String.format("%4.0f",acceleration_Z));
		builder.append("]");
		builder.append(" ");
		builder.append("Rot[");
		builder.append(String.format("%4.0f",rotation_X));
		builder.append(", ");
		builder.append(String.format("%4.0f",rotation_Y));
		builder.append(", ");
		builder.append(String.format("%4.0f",rotation_Z));
		builder.append("]");
		builder.append(" ");
		builder.append("North=");
		builder.append(String.format("%3.1f",north));
		builder.append(" ");
		builder.append("Top[");
		builder.append(String.format("%2.0f",top_temperature));
		builder.append(", ");
		builder.append(String.format("%2.0f",top_humidity));
		builder.append(", ");
		builder.append(String.format("%3.0f",top_light));
		builder.append(", ");
		builder.append(String.format("%3.0f",top_ir));
		builder.append("]");
		builder.append(" ");
		builder.append("Bot[");
		builder.append(String.format("%2.0f",bottom_temperature));
		builder.append(", ");
		builder.append(String.format("%2.0f",bottom_humidity));
		builder.append(", ");
		builder.append(String.format("%3.0f",bottom_light));
		builder.append(", ");
		builder.append(String.format("%3.0f",bottom_ir));
		builder.append("]");
		builder.append(" ");
		builder.append("Tel[");
		builder.append(", ");
		builder.append(String.format("%3.0f",telemetry_front));
		builder.append(", ");
		builder.append(String.format("%3.0f",telemetry_back));
		builder.append(", ");
		builder.append(String.format("%3.0f",telemetry_left));
		builder.append(", ");
		builder.append(String.format("%3.0f",telemetry_right));
		builder.append("]");
		return builder.toString();
	}

}
