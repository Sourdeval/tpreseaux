package fr.ensisa.darcel.buoys.buoy.model;

public class State {

	private int state;
	private int detail;

	public State(int state, int detail) {
		super();
		this.state = state;
		this.detail = detail;
	}

	public int getState() {
		return state;
	}

	public int getDetail() {
		return detail;
	}

	@Override
	public String toString() {
		if (state == 0) return "well";
		StringBuilder tmp = new StringBuilder();
		tmp.append("error ");
		tmp.append(state);
		tmp.append(".");
		tmp.append(detail);
		return tmp.toString();
	}


}
