package fr.ensisa.darcel.buoys.buoy.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Version {

	private final StringProperty number;
	private final StringProperty content;

	public Version() {
		super();
		this.number= new SimpleStringProperty("-");
		this.content = new SimpleStringProperty();
	}

	public Version(String number, byte [] content) {
		this();
		this.number.set(number);
		if (content != null) this.content.set(new String (content));
		else this.content.set(null);
	}

	public StringProperty getNumber() {
		return number;
	}

	public StringProperty getContent() {
		return content;
	}

	public void setWith(Version next) {
		this.number.set(next.getNumber().get());
		this.content.set(next.getContent().get());
	}

}
