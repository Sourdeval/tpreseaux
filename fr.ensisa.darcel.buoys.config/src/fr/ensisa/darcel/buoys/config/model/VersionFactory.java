package fr.ensisa.darcel.buoys.config.model;

import java.util.Random;

public class VersionFactory {

	static Random rnd = new Random();

	static private String buildNumber (int major, int minor) {
		StringBuilder tmp = new StringBuilder();
		tmp.append(major);
		tmp.append(".");
		tmp.append(minor);
		return tmp.toString();
	}

	static private byte [] buildContent (int major, int minor) {
		StringBuilder tmp = new StringBuilder();
		tmp.append("<?xml>");
		tmp.append("<content>");
		tmp.append("<version");
		tmp.append(" major='");
		tmp.append(major);
		tmp.append("'");
		tmp.append(" minor='");
		tmp.append(minor);
		tmp.append("'");
		tmp.append(">");
		tmp.append(major);
		tmp.append(".");
		tmp.append(minor);
		tmp.append("</version>");
		tmp.append("</content>");
		return tmp.toString().getBytes();
	}

	static private Version buildVersion (String number) {
		int major = 0;
		int minor = 0;
		if (number != null) {
			int position = number.indexOf(".");
			if (position != -1) {
				try { major = Integer.parseInt(number.substring(0, position)); } catch (Exception e) { }
				try { minor = Integer.parseInt(number.substring(position+1)); } catch (Exception e) { }
				++major;
				minor += 1+rnd.nextInt(9);
			}
		}
		String nextNumber = buildNumber(major, minor);
		byte [] nextContent = buildContent(major, minor);
		Version nextVersion = new Version (nextNumber, nextContent);
		return nextVersion;
	}

	static public Version getNextVersion (Version previous) {
		String number = null;
		if (previous != null) {
			number = previous.getNumber().get();
		}
		return buildVersion (number);
	}

}
