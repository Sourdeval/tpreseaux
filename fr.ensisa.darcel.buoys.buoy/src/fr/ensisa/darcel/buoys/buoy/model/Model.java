package fr.ensisa.darcel.buoys.buoy.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {

	private Buoy buoy;
	private ObservableList<BuoyData> buoyData;

	public Buoy getBuoy() {
		if (buoy == null) buoy = new Buoy();
		return buoy;
	}

	public ObservableList<BuoyData> getBuoyData() {
		if (buoyData == null) buoyData = FXCollections.observableArrayList();
		return buoyData;
	}

}
