package fr.ensisa.darcel.buoys.service.view;

import java.util.List;

import fr.ensisa.darcel.buoys.service.model.Buoy;
import fr.ensisa.darcel.buoys.service.model.BuoyData;
import fr.ensisa.darcel.buoys.service.model.Model;
import fr.ensisa.darcel.buoys.service.model.Usage;
import fr.ensisa.darcel.buoys.service.network.ISession;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

public class BuoyController {

	private Model model;
	private ISession session;

	private Buoy current = new Buoy();;
	private StringProperty status;

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
    private Label buoyId;
    @FXML
    private Label buoyWho;
    @FXML
    private Label buoyVersion;
    @FXML
    private Label buoyUsage;
    @FXML
    private CheckBox buoySensorsAcceleration;
    @FXML
    private CheckBox buoySensorsRotation;
    @FXML
    private CheckBox buoySensorsNorth;
    @FXML
    private CheckBox buoySensorsTop;
    @FXML
    private CheckBox buoySensorsBottom;
    @FXML
    private CheckBox buoySensorsTelemetry;
    @FXML
    private Label lastDate;
    @FXML
    private Label lastLocation;
    @FXML
    private Label lastState;
    @FXML
    private Label lastBattery;

    static NumberStringConverter longStringConverter = new NumberStringConverter ();

    static StringConverter<Usage> enumStringConverter = new StringConverter<Usage> () {

		@Override
		public String toString(Usage value) {
	        if (value == null) {
	            return "";
	        }
	        return value.name();
		}

		@Override
		public Usage fromString(String value) {
	        try {
	            if (value == null) {
	                return null;
	            }
	            value = value.trim().toUpperCase();
	            if (value.length() < 1) {
	                return null;
	            }
	            return Usage.valueOf(value);
	        } catch (Exception ex) {
                return null;
	        }
		}

    };

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

    private void loadBuoy (Buoy buoy) {
    	if (buoy == null) {
        	current.setWith(null);
        	model.getLast().setWith(null);
    		updateStatus("No buoy");
    	} else {
	    	long id = buoy.getId().get();
	    	loadBuoy(id);
	    	loadBuoyLastTick (id);
	    }
    }

    @FXML
    private void initialize() {
    	status = new SimpleStringProperty();
    	statusLabel.textProperty().bind(status);
        tableIdColumn.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
        tableWhoColumn.setCellValueFactory(cellData -> cellData.getValue().getWho());
        tableUsageColumn.setCellValueFactory(cellData -> cellData.getValue().getUsage());
        buoyTable.getSelectionModel().selectedItemProperty().addListener(
        		(observable, oldValue, newValue) -> loadBuoy(newValue)
    		);
    	buoyId.textProperty().bind(current.getId().asString());
    	buoyVersion.textProperty().bind(current.getVersion());
    	buoyWho.textProperty().bind(current.getWho());
    	buoyUsage.textProperty().bind(current.getUsage().asString());
    	buoySensorsAcceleration.selectedProperty().bind(current.getSensors().getSensor3DAcceleration());
    	buoySensorsRotation.selectedProperty().bind(current.getSensors().getSensor3DRotation());
    	buoySensorsNorth.selectedProperty().bind(current.getSensors().getSensorNorth());
    	buoySensorsTop.selectedProperty().bind(current.getSensors().getSensorTop());
    	buoySensorsBottom.selectedProperty().bind(current.getSensors().getSensorBottom());
    	buoySensorsTelemetry.selectedProperty().bind(current.getSensors().getSensorTelemetry());
    }

	public void setModel(Model model) {
		this.model = model;
		buoyTable.setItems(model.getBuoys().getBuoys());
		lastDate.textProperty().bind(model.getLast().getDate().asString());
		lastLocation.textProperty().bind(model.getLast().getLocation().asString());
		lastState.textProperty().bind(model.getLast().getState());
		lastBattery.textProperty().bind(model.getLast().getBattery().asString());
		status.set("Ready");
	}

	public void setSession(ISession session) {
		this.session = session;
	}

}
