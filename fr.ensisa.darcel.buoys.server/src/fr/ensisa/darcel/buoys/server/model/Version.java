package fr.ensisa.darcel.buoys.server.model;

public class Version {

	private String number;
	private byte [] content;

	public Version() {
		super();
		this.number= "-";
		this.content = null;
	}

	public Version(String number, byte [] content) {
		super();
		this.number = number;
		this.content = content;
	}

	public String getNumber() {
		return number;
	}

	public byte [] getContent() {
		return content;
	}

}
