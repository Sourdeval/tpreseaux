package fr.ensisa.darcel.buoys.client.model;

public class Measures {

	private String value;

	public Measures(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(value);
		return builder.toString();
	}

}
