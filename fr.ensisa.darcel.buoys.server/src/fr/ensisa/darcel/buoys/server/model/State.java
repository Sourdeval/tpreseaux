package fr.ensisa.darcel.buoys.server.model;

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


}
