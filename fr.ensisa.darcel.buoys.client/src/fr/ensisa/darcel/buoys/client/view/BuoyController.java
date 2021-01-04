<<<<<<< HEAD
package fr.ensisa.darcel.buoys.client.view;

import java.util.List;

import fr.ensisa.darcel.buoys.client.model.Buoy;
import fr.ensisa.darcel.buoys.client.model.BuoyData;
import fr.ensisa.darcel.buoys.client.model.Location;
import fr.ensisa.darcel.buoys.client.model.Model;
import fr.ensisa.darcel.buoys.client.model.Usage;
import fr.ensisa.darcel.buoys.client.network.ISession;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class BuoyController {

	private Model model;
	private ISession session;

	private Buoy current = new Buoy();;
	private StringProperty status;
	private BooleanProperty ticks;
	private BooleanProperty measures;

    @FXML
    private Parent pane;
	@FXML
	private Label statusLabel;
	@FXML
	private TextField filterWho;
    @FXML
    private TableView<Buoy> buoyTable;
    @FXML
    private TableColumn<Buoy, Long> tableIdColumn;
    @FXML
    private TableColumn<Buoy, String>  tableWhoColumn;
    @FXML
    private TableColumn<Buoy, Usage>  tableUsageColumn;

    @FXML
    private CheckBox ticksCB;
    @FXML
    private CheckBox measuresCB;

    @FXML
    private Label lastDate;
    @FXML
    private Label lastLocation;
    @FXML
    private Label lastState;
    @FXML
    private Label lastBattery;

    @FXML
    private TableView<BuoyData> dataTable;
    @FXML
    private TableColumn<BuoyData, String> dataDateColumn;
    @FXML
    private TableColumn<BuoyData, Location>  dataLocationColumn;
    @FXML
    private TableColumn<BuoyData, String>  dataTypeColumn;
    @FXML
    private TableColumn<BuoyData, String>  dataMeasuresColumn;

    private void updateStatus (String text) {
    	status.set(text);
    }

    @FXML
    private void handleFilter() {
    	String who = filterWho.getText();
    	updateStatus("Fetching "+who+" buoys");
    	List<Buoy> related = session.doGetBuoyList(who);
    	model.getBuoys().getBuoys().clear();
    	if (related != null) {
    		model.getBuoys().getBuoys().addAll(related);
    		updateStatus("Buoys fetch done");
    	} else {
    		updateStatus("Buoys fetch failed");
    	}
    }

    private void loadBuoy(long id) {
    	updateStatus("Fetching buoy "+id+" description");
    	Buoy buoy = session.doGetBuoy (id);
    	if (buoy != null) {
        	current.setWith(buoy);
        	updateStatus("Buoy description got");
    	} else {
        	current.setWith(null);
    		updateStatus("Buoy description tick");
    	}
    }

    private void loadBuoyLastTick(long id) {
    	updateStatus("Fetching buoy last tick");
    	BuoyData data = session.doGetBuoyLastTick (id);
    	if (data != null) {
        	model.getLast().setWith(data);
        	updateStatus("Buoys last tick got");
    	} else {
        	model.getLast().setWith(null);
    		updateStatus("Buoys no last tick");
    	}
    }

    private void loadBuoyMeasures(long id) {
    	updateStatus("Fetching buoy data");
    	List<BuoyData> data = session.doGetBuoyData (id, ticks.get(), measures.get());
    	model.getData().clear();
    	if (data != null) {
        	model.getData().addAll(data);
        	updateStatus("Buoys data got");
    	} else {
    		updateStatus("Buoys no data ");
    	}
    }

    private void loadBuoy (Buoy buoy) {
    	if (buoy == null) {
        	current.setWith(null);
        	model.getLast().setWith(null);
        	model.getData().clear();
    		updateStatus("No buoy");
    	} else {
	    	long id = buoy.getId().get();
	    	loadBuoy(id);
	    	loadBuoyLastTick (id);
	    	loadBuoyMeasures (id);
	    }
    }

    @FXML
    private void handleDataTypeChanged() {
    	if (current == null) return;
    	long id = current.getId().get();
    	if (id == 0) return;
    	loadBuoyMeasures(id);
    }

    @FXML
    private void initialize() {
    	status = new SimpleStringProperty();
    	ticks = new SimpleBooleanProperty();
    	measures = new SimpleBooleanProperty(true);
    	statusLabel.textProperty().bind(status);
    	ticksCB.selectedProperty().bindBidirectional(ticks);
    	measuresCB.selectedProperty().bindBidirectional(measures);
        tableIdColumn.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
        tableWhoColumn.setCellValueFactory(cellData -> cellData.getValue().getWho());
        tableUsageColumn.setCellValueFactory(cellData -> cellData.getValue().getUsage());
        buoyTable.getSelectionModel().selectedItemProperty().addListener(
        		(observable, oldValue, newValue) -> loadBuoy (newValue)
    		);
        dataDateColumn.setCellValueFactory(cellData -> cellData.getValue().getDate().asString("%tT"));
        dataLocationColumn.setCellValueFactory(cellData -> cellData.getValue().getLocation());
        dataTypeColumn.setCellValueFactory(cellData -> cellData.getValue().getType());
        dataMeasuresColumn.setCellValueFactory(cellData -> cellData.getValue().getData());
    }

	public void setModel(Model model) {
		this.model = model;
		buoyTable.setItems(model.getBuoys().getBuoys());
    	lastDate.textProperty().bind(model.getLast().getDate().asString());
    	lastLocation.textProperty().bind(model.getLast().getLocation().asString());
    	lastState.textProperty().bind(model.getLast().getState());
    	lastBattery.textProperty().bind(model.getLast().getBattery().asString());
		dataTable.setItems(model.getData());
    	status.set("Ready");
	}

	public void setSession(ISession session) {
		this.session = session;
	}

}
=======
package fr.ensisa.darcel.buoys.client.view;

import java.util.List;

import fr.ensisa.darcel.buoys.client.model.Buoy;
import fr.ensisa.darcel.buoys.client.model.BuoyData;
import fr.ensisa.darcel.buoys.client.model.Location;
import fr.ensisa.darcel.buoys.client.model.Model;
import fr.ensisa.darcel.buoys.client.model.Usage;
import fr.ensisa.darcel.buoys.client.network.ISession;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class BuoyController {

	private Model model;
	private ISession session;

	private Buoy current = new Buoy();;
	private StringProperty status;
	private BooleanProperty ticks;
	private BooleanProperty measures;

    @FXML
    private Parent pane;
	@FXML
	private Label statusLabel;
	@FXML
	private TextField filterWho;
    @FXML
    private TableView<Buoy> buoyTable;
    @FXML
    private TableColumn<Buoy, Long> tableIdColumn;
    @FXML
    private TableColumn<Buoy, String>  tableWhoColumn;
    @FXML
    private TableColumn<Buoy, Usage>  tableUsageColumn;

    @FXML
    private CheckBox ticksCB;
    @FXML
    private CheckBox measuresCB;

    @FXML
    private Label lastDate;
    @FXML
    private Label lastLocation;
    @FXML
    private Label lastState;
    @FXML
    private Label lastBattery;

    @FXML
    private TableView<BuoyData> dataTable;
    @FXML
    private TableColumn<BuoyData, String> dataDateColumn;
    @FXML
    private TableColumn<BuoyData, Location>  dataLocationColumn;
    @FXML
    private TableColumn<BuoyData, String>  dataTypeColumn;
    @FXML
    private TableColumn<BuoyData, String>  dataMeasuresColumn;

    private void updateStatus (String text) {
    	status.set(text);
    }

    @FXML
    private void handleFilter() {
    	String who = filterWho.getText();
    	updateStatus("Fetching "+who+" buoys");
    	List<Buoy> related = session.doGetBuoyList(who);
    	model.getBuoys().getBuoys().clear();
    	if (related != null) {
    		model.getBuoys().getBuoys().addAll(related);
    		updateStatus("Buoys fetch done");
    	} else {
    		updateStatus("Buoys fetch failed");
    	}
    }

    private void loadBuoy(long id) {
    	updateStatus("Fetching buoy "+id+" description");
    	Buoy buoy = session.doGetBuoy (id);
    	if (buoy != null) {
        	current.setWith(buoy);
        	updateStatus("Buoy description got");
    	} else {
        	current.setWith(null);
    		updateStatus("Buoy description tick");
    	}
    }

    private void loadBuoyLastTick(long id) {
    	updateStatus("Fetching buoy last tick");
    	BuoyData data = session.doGetBuoyLastTick (id);
    	if (data != null) {
        	model.getLast().setWith(data);
        	updateStatus("Buoys last tick got");
    	} else {
        	model.getLast().setWith(null);
    		updateStatus("Buoys no last tick");
    	}
    }

    private void loadBuoyMeasures(long id) {
    	updateStatus("Fetching buoy data");
    	List<BuoyData> data = session.doGetBuoyData (id, ticks.get(), measures.get());
    	model.getData().clear();
    	if (data != null) {
        	model.getData().addAll(data);
        	updateStatus("Buoys data got");
    	} else {
    		updateStatus("Buoys no data ");
    	}
    }

    private void loadBuoy (Buoy buoy) {
    	if (buoy == null) {
        	current.setWith(null);
        	model.getLast().setWith(null);
        	model.getData().clear();
    		updateStatus("No buoy");
    	} else {
	    	long id = buoy.getId().get();
	    	loadBuoy(id);
	    	loadBuoyLastTick (id);
	    	loadBuoyMeasures (id);
	    }
    }

    @FXML
    private void handleDataTypeChanged() {
    	if (current == null) return;
    	long id = current.getId().get();
    	if (id == 0) return;
    	loadBuoyMeasures(id);
    }

    @FXML
    private void initialize() {
    	status = new SimpleStringProperty();
    	ticks = new SimpleBooleanProperty();
    	measures = new SimpleBooleanProperty(true);
    	statusLabel.textProperty().bind(status);
    	ticksCB.selectedProperty().bindBidirectional(ticks);
    	measuresCB.selectedProperty().bindBidirectional(measures);
        tableIdColumn.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
        tableWhoColumn.setCellValueFactory(cellData -> cellData.getValue().getWho());
        tableUsageColumn.setCellValueFactory(cellData -> cellData.getValue().getUsage());
        buoyTable.getSelectionModel().selectedItemProperty().addListener(
        		(observable, oldValue, newValue) -> loadBuoy (newValue)
    		);
        dataDateColumn.setCellValueFactory(cellData -> cellData.getValue().getDate().asString("%tT"));
        dataLocationColumn.setCellValueFactory(cellData -> cellData.getValue().getLocation());
        dataTypeColumn.setCellValueFactory(cellData -> cellData.getValue().getType());
        dataMeasuresColumn.setCellValueFactory(cellData -> cellData.getValue().getData());
    }

	public void setModel(Model model) {
		this.model = model;
		buoyTable.setItems(model.getBuoys().getBuoys());
    	lastDate.textProperty().bind(model.getLast().getDate().asString());
    	lastLocation.textProperty().bind(model.getLast().getLocation().asString());
    	lastState.textProperty().bind(model.getLast().getState());
    	lastBattery.textProperty().bind(model.getLast().getBattery().asString());
		dataTable.setItems(model.getData());
    	status.set("Ready");
	}

	public void setSession(ISession session) {
		this.session = session;
	}

}
>>>>>>> branch 'master' of https://github.com/Sourdeval/tpreseaux
