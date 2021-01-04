package fr.ensisa.darcel.buoys.service.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Buoys {

	private ObservableList<Buoy> buoys;

	public ObservableList<Buoy> getBuoys() {
		if (buoys == null) {
			buoys = FXCollections.observableArrayList();
		}
		return buoys;
	}

	public Buoy getById (long id) {
		for (Buoy b : getBuoys()) {
			if (b.getId().get() == id) {
				return b;
			}
		}
		return null;
	}

	public void add(Buoy buoy) {
		if (buoy == null) return;
		Buoy copy = new Buoy();
		copy.setWith(buoy);
		getBuoys().add(copy);
	}

	public void update(Buoy buoy) {
		if (buoy == null) return;
		Buoy old = getById(buoy.getId().get());
		if (old == null) return;
		old.setWith(buoy);
	}

	public void remove(Buoy current) {
		if (current == null) return;
		Buoy toremove = getById(current.getId().get());
		if (toremove == null) return;
		getBuoys().remove(toremove);
	}

	public void removeByIndex(int index) {
		getBuoys().remove(index);
	}

}
