<<<<<<< HEAD
package fr.ensisa.darcel.buoys.client.model;

public class Battery {

	private int level;

	public Battery(int level) {
		super();
		this.level = level;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Battery ");
		builder.append(level);
		builder.append("%");
		return builder.toString();
	}

}
=======
package fr.ensisa.darcel.buoys.client.model;

public class Battery {

	private int level;

	public Battery(int level) {
		super();
		this.level = level;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Battery ");
		builder.append(level);
		builder.append("%");
		return builder.toString();
	}

}
>>>>>>> branch 'master' of https://github.com/Sourdeval/tpreseaux
