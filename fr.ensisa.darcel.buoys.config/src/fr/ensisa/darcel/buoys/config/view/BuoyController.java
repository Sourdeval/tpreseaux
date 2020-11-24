package fr.ensisa.darcel.buoys.config.view;

import java.util.List;

import fr.ensisa.darcel.buoys.config.model.Buoy;
import fr.ensisa.darcel.buoys.config.model.Model;
import fr.ensisa.darcel.buoys.config.model.Usage;
import fr.ensisa.darcel.buoys.config.model.Version;
import fr.ensisa.darcel.buoys.config.model.VersionFactory;
import fr.ensisa.darcel.buoys.config.network.ISession;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
	private Label versionNumber;
	@FXML
	private Label versionContent;
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
    private TextField buoyWho;
    @FXML
    private Label buoyVersion;
    @FXML
    private ComboBox<Usage> buoyUsage;
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
    private Label dataCount;

    static NumberStringConverter longStringConverter = new NumberStringConverter ();

    private void updateStatus (String text) {
    	status.set(text);
    }

    @FXML
    private void handleCurrent() {
    	updateStatus("Get current version");
    	Version nextVersion = session.doReceiveCurrentVersion();
    	if (nextVersion == null) {
    		updateStatus("Protocol error");
    		return;
    	} else {
        	model.getVersion().setWith(nextVersion);
        	updateStatus("Current version got");
    	}
    }

    @FXML
    private void handleUpdate() {
    	Version nextVersion = VersionFactory.getNextVersion(model.getVersion());
    	updateStatus("Sending new version");
    	Boolean done = session.doSendNewVersion(nextVersion);
    	if (done == null) {
    		updateStatus("Protocol error");
    		return;
    	}
    	if (done == Boolean.TRUE) {
        	model.getVersion().setWith(nextVersion);
        	updateStatus("New version accepted");
    	} else {
    		updateStatus("New version refused");
    	}
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

    @FXML
    private void handleNew() {
    	current.setWith(new Buoy());
    }

    @FXML
    private void handleDelete() {
    	long id = current.getId().get();
    	if (id < 0) {
	    	updateStatus("Illegal to delete buoy "+id);
	    	return;
	    }
		updateStatus("Deleting buoy "+id);
    	Boolean done = session.doDeleteBuoy(id);
    	if (done == null) {
    		updateStatus("Protocol error");
    		return;
    	}
    	if (done == Boolean.TRUE) {
        	model.getBuoys().remove(current);
        	updateStatus("Buoy "+id+" deleted");
    	} else {
    		updateStatus("Not able to delete buoy "+id);
    	}
    }

    @FXML
    private void handleValid() {
    	long id = current.getId().get();
    	if (id > 0) {
    		updateStatus("Updating buoy "+id);
	    	Boolean done = session.doUpdateBuoy(current);
	    	if (done == null) {
	    		updateStatus("Protocol error");
	    		return;
	    	}
	    	if (done == Boolean.TRUE) {
	    		model.getBuoys().update(current);
	        	Buoy buoy = model.getBuoys().getById(id);
	        	current.setWith(buoy);
	        	updateStatus("Buoy "+id+" updated");
	    	} else {
	    		updateStatus("Not able to update buoy "+id);
	    	}
    	} else {
    		updateStatus("Inserting new buoy");
	    	Long newId = session.doCreateBuoy(current);
	    	if (newId == null) {
	    		updateStatus("Protocol error");
	    		return;
	    	}
	    	if (newId != -1) {
	    		current.setId(newId);
	        	model.getBuoys().add(current);
	        	Buoy buoy = model.getBuoys().getById(newId);
	        	current.setWith(buoy);
	        	updateStatus("Buoy "+id+" inserted");
	    	} else {
	    		updateStatus("Not able to insert a new buoy");
	    	}
    	}
    }

    @FXML
    private void handleClear() {
    	long id = current.getId().get();
    	if (id < 0) {
	    	updateStatus("Illegal to clear data buoy "+id);
	    	return;
	    }
		updateStatus("Clearing data of buoy "+id);
    	Boolean done = session.doClearDataBuoy(id);
    	if (done == null) {
    		updateStatus("Protocol error");
    		return;
    	}
    	if (done == Boolean.TRUE) {
        	updateStatus("Buoy "+id+" data cleared");
    	} else {
    		updateStatus("Not able to clear data of buoy "+id);
    	}
    }

    private void loadBuoy(Buoy buoy) {
    	if (buoy == null) {
        	current.setWith(null);
    		updateStatus("No buoy");
    	} else {
	    	long id = buoy.getId().get();
	    	updateStatus("Fetching buoy "+id+" description");
	    	Buoy description = session.doGetBuoy (id);
	    	if (description != null) {
	        	current.setWith(description);
	        	updateStatus("Buoy description got");
	    	} else {
	        	current.setWith(null);
	    		updateStatus("Buoy description tick");
	    	}
	    }
    }

    @FXML
    private void initialize() {
    	status = new SimpleStringProperty();
    	statusLabel.textProperty().bind(status);
        tableIdColumn.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
        tableWhoColumn.setCellValueFactory(cellData -> cellData.getValue().getWho());
        tableUsageColumn.setCellValueFactory(cellData -> cellData.getValue().getUsage());
        buoyUsage.setItems(FXCollections.observableArrayList(Usage.values()));
        buoyTable.getSelectionModel().selectedItemProperty().addListener(
        		(observable, oldValue, newValue) -> loadBuoy(newValue)
    		);
    	buoyId.textProperty().bindBidirectional(current.getId(), longStringConverter);
    	buoyVersion.textProperty().bindBidirectional(current.getVersion());
    	buoyWho.textProperty().bindBidirectional(current.getWho());
    	buoyUsage.valueProperty().bindBidirectional(current.getUsage());
    	buoySensorsAcceleration.selectedProperty().bindBidirectional(current.getSensors().getSensor3DAcceleration());
    	buoySensorsRotation.selectedProperty().bindBidirectional(current.getSensors().getSensor3DRotation());
    	buoySensorsNorth.selectedProperty().bindBidirectional(current.getSensors().getSensorNorth());
    	buoySensorsTop.selectedProperty().bindBidirectional(current.getSensors().getSensorTop());
    	buoySensorsBottom.selectedProperty().bindBidirectional(current.getSensors().getSensorBottom());
    	buoySensorsTelemetry.selectedProperty().bindBidirectional(current.getSensors().getSensorTelemetry());
    	dataCount.textProperty().bindBidirectional(current.getDataCount(), longStringConverter);
    }

	public void setModel(Model model) {
		this.model = model;
		buoyTable.setItems(model.getBuoys().getBuoys());
		versionNumber.textProperty().bind(model.getVersion().getNumber());
		versionContent.textProperty().bind(model.getVersion().getShortContent());
		updateStatus("Ready");
	}

	public void setSession(ISession session) {
		this.session = session;
	}

}
