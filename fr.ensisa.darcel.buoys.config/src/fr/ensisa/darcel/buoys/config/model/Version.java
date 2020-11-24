package fr.ensisa.darcel.buoys.config.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Version {

	private StringProperty number;
	private StringProperty content;
	private StringProperty shortContent;

	public Version() {
		super();
		this.number= new SimpleStringProperty("-");
		this.content = new SimpleStringProperty();
		this.shortContent = new SimpleStringProperty();
	}

	public Version(String number, byte [] content) {
		this();
		this.number.set(number);
		if (content != null) this.content.set(new String(content));
		else this.content.set(null);
		updateShortContent ();
	}

	public StringProperty getNumber() {
		return number;
	}

	public StringProperty getContent() {
		return content;
	}

	public StringProperty getShortContent() {
		return shortContent;
	}

	public void setWith(Version nextVersion) {
		this.number.set(nextVersion.getNumber().get());
		this.content.set(nextVersion.getContent().get());
		updateShortContent ();
	}

	private void updateShortContent() {
		String c = this.content.get();
		if (c != null) {
			Pattern pattern = Pattern.compile("<version (.*?)>");
			Matcher matcher = pattern.matcher(c);
			if (matcher.find()) {
				this.shortContent.set(matcher.group(1));
			} else {
				this.shortContent.set("---");
			}
		} else {
			this.shortContent.set("null");
		}
	}

}
