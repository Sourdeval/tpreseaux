package fr.ensisa.darcel.buoys.buoy.view;

import java.util.List;
import java.util.Random;

import fr.ensisa.darcel.buoys.buoy.model.Battery;
import fr.ensisa.darcel.buoys.buoy.model.Buoy;
import fr.ensisa.darcel.buoys.buoy.model.BuoyData;
import fr.ensisa.darcel.buoys.buoy.model.Location;
import fr.ensisa.darcel.buoys.buoy.model.Measures;
import fr.ensisa.darcel.buoys.buoy.model.Model;
import fr.ensisa.darcel.buoys.buoy.model.State;
import fr.ensisa.darcel.buoys.buoy.model.Version;
import fr.ensisa.darcel.buoys.buoy.model.Battery.Plug;
import fr.ensisa.darcel.buoys.buoy.network.ISession;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class BuoyController {

	private Model model;
	private ISession session;

	private StringProperty status;

    @FXML
    private Parent pane;
	@FXML
	private Label statusLabel;
	@FXML
	private TextField selectId;

    @FXML
    private Label buoyWho;
    @FXML
    private Label buoyVersionNumber;
    @FXML
    private Label buoyVersionContent;
    @FXML
    private Label buoyUsage;
    @FXML
    private Label buoySensors;

    @FXML
    private TableView<BuoyData> buoyDataTable;
    @FXML
    private TableColumn<BuoyData, String>  tableColumn;

    @FXML
    private TextField buoyLongitude;
    @FXML
    private TextField buoyLatitude;
    @FXML
    private TextField buoyAltitude;
    @FXML
    private TextField buoyBatteryLevel;
    @FXML
    private TextField buoyBatteryCount;
    @FXML
    private TextField buoyBatteryTemperature;
    @FXML
    private TextField buoyBatteryLoad;
    @FXML
    private TextField buoyBatteryDischarge;
    @FXML
    private ToggleGroup buoyBatteryPlug;
    @FXML
    private TextField buoyState;
    @FXML
    private TextField buoyDetail;

    @FXML
    private TextField buoySensorsGeneralAcceleration_X;
    @FXML
    private TextField buoySensorsGeneralAcceleration_Y;
    @FXML
    private TextField buoySensorsGeneralAcceleration_Z;
    @FXML
    private TextField buoySensorsGeneralRotation_X;
    @FXML
    private TextField buoySensorsGeneralRotation_Y;
    @FXML
    private TextField buoySensorsGeneralRotation_Z;
    @FXML
    private TextField buoySensorsGeneralNorth;
    @FXML
    private TextField buoySensorsTopTemperature;
    @FXML
    private TextField buoySensorsTopHumidity;
    @FXML
    private TextField buoySensorsTopLight;
    @FXML
    private TextField buoySensorsTopIR;
    @FXML
    private TextField buoySensorsBottomTemperature;
    @FXML
    private TextField buoySensorsBottomHumidity;
    @FXML
    private TextField buoySensorsBottomLight;
    @FXML
    private TextField buoySensorsBottomIR;
    @FXML
    private TextField buoySensorsTelemetryFront;
    @FXML
    private TextField buoySensorsTelemetryBack;
    @FXML
    private TextField buoySensorsTelemetryLeft;
    @FXML
    private TextField buoySensorsTelemetryRight;

    private void updateStatus (String text) {
    	status.set(text);
    }

    @FXML
    private void handleSelect() {
    	long id = convertLong (selectId.getText());
    	updateStatus("Fetching buoy #"+id);
    	Buoy found = session.doGetBuoy(id);
    	if (found != null) {
    		model.getBuoy().setWith(found);;
    		updateStatus("Buoy fetch done");
    	} else {
    		updateStatus("Buoy fetch failed");
    	}
    }

    @FXML
    private void handleUpdate() {
    	updateStatus("Version Update");
    	Version previousVersion = model.getBuoy().getVersion();
    	Version nextVersion = session.doUpdateVersion(previousVersion);
    	if (nextVersion != null && nextVersion.getNumber() != null) {
    		if (previousVersion.getNumber().get() != null) {
	    		if (previousVersion.getNumber().get().equals (nextVersion.getNumber().get())) {
	        		updateStatus("Same version, no update");
	    		} else {
	    			model.getBuoy().getVersion().setWith(nextVersion);
	    			updateStatus("New version, update applied");
	    		}
    		} else {
    			model.getBuoy().getVersion().setWith(nextVersion);
    			updateStatus("New version, update applied");
    		}
    	} else {
    		updateStatus("Version no update");
    	}
    }

    @FXML
    private void handleClear() {
    	model.getBuoyData().clear();
    }

    @FXML
    private void handleSendAllData() {
    	updateStatus("Sendind all data");
    	List<BuoyData> data = model.getBuoyData();
    	Boolean done = session.doSendAllData(data);
    	if (done == null) {
    		updateStatus("Protocol error");
    		return;
    	}
    	if (done == Boolean.TRUE) {
        	updateStatus("Data uploaded, now you can erase them");
    	} else {
        	updateStatus("Can't upload data");
    	}
    }

    private void doSendTick(BuoyData tick) {
    	updateStatus("Sendind tick");
    	Boolean done = session.doSendTick(tick);
    	if (done == null) {
    		updateStatus("Protocol error");
    		return;
    	}
    	if (done == Boolean.TRUE) {
        	updateStatus("Tick sent");
    	} else {
        	updateStatus("Can't send tick");
    	}
    }

    private long extractDate () {
    	return System.currentTimeMillis();
    }

    private long extractId () {
    	long id = model.getBuoy().getId().get();
    	return id;
    }

    private int convertInt (String text) {
    	if (text == null) return 0;
    	try {
        	int value = Integer.parseInt(text);
    		return value;
		}
    	catch (Exception e) {
    		return 0;
    	}
    }

    private long convertLong (String text) {
    	if (text == null) return 0;
    	try {
        	long value = Long.parseLong(text);
    		return value;
		}
    	catch (Exception e) {
    		return 0;
    	}
    }

    private float convertFloat (String text) {
    	if (text == null) return 0;
    	try {
        	float value = Float.parseFloat(text);
    		return value;
		}
    	catch (Exception e) {
    		return 0;
    	}
    }

    private State extractState () {
    	int code = convertInt (buoyState.getText());
    	int detail = convertInt (buoyDetail.getText());
    	return new State (code, detail);
    }

    private Location extractLocation () {
    	float longitude = convertFloat (buoyLongitude.getText());
    	float latitude = convertFloat (buoyLatitude.getText());
    	float altitude = convertFloat (buoyAltitude.getText());
    	return new Location(longitude, latitude, altitude);
    }

    private Battery extractBattery () {
    	int level = convertInt (buoyBatteryLevel.getText());
    	int count = convertInt (buoyBatteryCount.getText());
    	int temperature = convertInt (buoyBatteryTemperature.getText());
    	int load = convertInt (buoyBatteryLoad.getText());
    	int discharge = convertInt (buoyBatteryDischarge.getText());
    	RadioButton radio = (RadioButton) buoyBatteryPlug.getSelectedToggle();
    	Plug plug = null;
    	if (radio != null) {
	    	if (radio.getText().equals("Not")) plug = Plug.DISCONNECTED;
	    	if (radio.getText().equals("Slow")) plug = Plug.CHARGING_SLOW;
	    	if (radio.getText().equals("Fast")) plug = Plug.CHARGING_FAST;
    	}
    	return new Battery(level, temperature, load, plug, discharge, count);
    }

    private Measures extractMeasures () {
    	float acceleration_X = convertFloat (buoySensorsGeneralAcceleration_X.getText());
    	float acceleration_Y = convertFloat (buoySensorsGeneralAcceleration_Y.getText());
    	float acceleration_Z = convertFloat (buoySensorsGeneralAcceleration_Z.getText());
    	float rotation_X = convertFloat (buoySensorsGeneralRotation_X.getText());
    	float rotation_Y = convertFloat (buoySensorsGeneralRotation_Y.getText());
    	float rotation_Z = convertFloat (buoySensorsGeneralRotation_Z.getText());
    	float north = convertFloat (buoySensorsGeneralNorth.getText());
    	float top_temperature = convertFloat (buoySensorsTopTemperature.getText());
    	float top_humidity = convertFloat (buoySensorsTopHumidity.getText());
    	float top_light = convertFloat (buoySensorsTopLight.getText());
    	float top_ir = convertFloat (buoySensorsTopIR.getText());
    	float bottom_temperature = convertFloat (buoySensorsBottomTemperature.getText());
    	float bottom_humidity = convertFloat (buoySensorsBottomHumidity.getText());
    	float bottom_light = convertFloat (buoySensorsBottomLight.getText());
    	float bottom_ir = convertFloat (buoySensorsBottomIR.getText());
    	float telemetry_front = convertFloat (buoySensorsTelemetryFront.getText());
    	float telemetry_back = convertFloat (buoySensorsTelemetryBack.getText());
    	float telemetry_left = convertFloat (buoySensorsTelemetryLeft.getText());
    	float telemetry_right = convertFloat (buoySensorsTelemetryRight.getText());
    	return new Measures(
	    			acceleration_X, acceleration_Y, acceleration_Z,
	    			rotation_X, rotation_Y, rotation_Z,
	    			north,
	    			top_temperature, top_humidity, top_light, top_ir,
	    			bottom_temperature, bottom_humidity, bottom_light, bottom_ir,
	    			telemetry_front, telemetry_back, telemetry_left, telemetry_right
			);
    }

    private BuoyData gatherTickData () {
    	long date = extractDate();
    	long id = extractId();
    	Location location = extractLocation();
    	State state = extractState();
    	Battery battery = extractBattery();
    	BuoyData data = new BuoyData(date, id, location, state, battery);
    	return data;
    }

    @FXML
    private void handleTick() {
    	BuoyData data = gatherTickData();
    	doSendTick (data);
    	model.getBuoyData().add(data);
    }

    private BuoyData gatherMeasuresData () {
    	long date = extractDate();
    	long id = extractId();
    	Location location = extractLocation();
    	Measures measures = extractMeasures();
    	BuoyData data = new BuoyData(date, id, location, measures);
    	return data;
    }

    @FXML
    private void handleMeasures() {
    	BuoyData data = gatherMeasuresData();
    	model.getBuoyData().add(data);
    }

    private Random rnd = new Random();

    @FXML
    private void handleRandomLocation() {
    	buoyLatitude.setText(Float.toString(180.0F*rnd.nextFloat()-90.0F));
    	buoyLongitude.setText(Float.toString(180.0F*rnd.nextFloat()-90.0F));
    	float altitude = convertFloat(buoyAltitude.getText());
    	buoyAltitude.setText(Float.toString(altitude + 2.0F*rnd.nextFloat()-1.0F));
    }

    @FXML
    private void handleRandomBattery() {
    	int temperature = convertInt(buoyBatteryTemperature.getText());
    	if (temperature < 10) temperature = 10;
    	if (temperature > 50) temperature = 50;
    	buoyBatteryTemperature.setText(Integer.toString(temperature + (rnd.nextInt(40)-20)/10));
    	int level = convertInt(buoyBatteryLevel.getText());
    	buoyBatteryLevel.setText(Integer.toString(level + (rnd.nextInt(20)-10)/10));
    	buoyBatteryLoad.setText(Integer.toString(rnd.nextInt(256)));
    	buoyBatteryDischarge.setText(Integer.toString(rnd.nextInt(256)));
    }

    @FXML
    private void handleRandomAccelerations() {
    	buoySensorsGeneralAcceleration_X.setText(Float.toString(4000.0F*rnd.nextFloat()-2000.0F));
    	buoySensorsGeneralAcceleration_Y.setText(Float.toString(4000.0F*rnd.nextFloat()-2000.0F));
    	buoySensorsGeneralAcceleration_Z.setText(Float.toString(4000.0F*rnd.nextFloat()-2000.0F));
    }

    @FXML
    private void handleRandomRotations() {
    	buoySensorsGeneralRotation_X.setText(Float.toString(2000.0F*rnd.nextFloat()-1000.0F));
    	buoySensorsGeneralRotation_Y.setText(Float.toString(2000.0F*rnd.nextFloat()-1000.0F));
    	buoySensorsGeneralRotation_Z.setText(Float.toString(2000.0F*rnd.nextFloat()-1000.0F));
    }

    @FXML
    private void handleRandomTop() {
    	buoySensorsTopTemperature.setText(Float.toString(60.0F*rnd.nextFloat()-10.0F));
    	buoySensorsTopHumidity.setText(Float.toString(rnd.nextInt(100)));
    	buoySensorsTopLight.setText(Float.toString(rnd.nextInt(256)));
    	buoySensorsTopIR.setText(Integer.toString(rnd.nextInt(256)));
    }

    @FXML
    private void handleRandomBottom() {
    	buoySensorsBottomTemperature.setText(Float.toString(60.0F*rnd.nextFloat()-10.0F));
    	buoySensorsBottomHumidity.setText(Integer.toString(rnd.nextInt(100)));
    	buoySensorsBottomLight.setText(Integer.toString(rnd.nextInt(256)));
    	buoySensorsBottomIR.setText(Integer.toString(rnd.nextInt(256)));
    }

    @FXML
    private void handleRandomTelemetry() {
    	buoySensorsTelemetryFront.setText(Integer.toString(rnd.nextInt(1000)+10));
    	buoySensorsTelemetryBack.setText(Integer.toString(rnd.nextInt(1000)+10));
    	buoySensorsTelemetryLeft.setText(Integer.toString(rnd.nextInt(1000)+10));
    	buoySensorsTelemetryRight.setText(Integer.toString(rnd.nextInt(1000)+10));
    }

    @FXML
    private void initialize() {
    	status = new SimpleStringProperty();
    	statusLabel.textProperty().bind(status);
    	tableColumn.setCellValueFactory(cellData -> cellData.getValue().asString());
    }

	public void setModel(Model model) {
		this.model = model;
		buoyDataTable.setItems(model.getBuoyData());
    	buoyWho.textProperty().bind(model.getBuoy().getWho());
        buoyVersionNumber.textProperty().bind(model.getBuoy().getVersion().getNumber());
        buoyVersionContent.textProperty().bind(model.getBuoy().getVersion().getContent());
    	buoyUsage.textProperty().bind(model.getBuoy().getUsage().asString());
    	buoySensors.textProperty().bind(model.getBuoy().getSensors().asString());

    	buoySensorsGeneralAcceleration_X.disableProperty().bind(model.getBuoy().getSensors().getSensor3DAcceleration().not());
    	buoySensorsGeneralAcceleration_Y.disableProperty().bind(model.getBuoy().getSensors().getSensor3DAcceleration().not());
    	buoySensorsGeneralAcceleration_Z.disableProperty().bind(model.getBuoy().getSensors().getSensor3DAcceleration().not());
    	buoySensorsGeneralRotation_X.disableProperty().bind(model.getBuoy().getSensors().getSensor3DRotation().not());
    	buoySensorsGeneralRotation_Y.disableProperty().bind(model.getBuoy().getSensors().getSensor3DRotation().not());
    	buoySensorsGeneralRotation_Z.disableProperty().bind(model.getBuoy().getSensors().getSensor3DRotation().not());
    	buoySensorsGeneralNorth.disableProperty().bind(model.getBuoy().getSensors().getSensorNorth().not());
    	buoySensorsTopTemperature.disableProperty().bind(model.getBuoy().getSensors().getSensorTop().not());
    	buoySensorsTopHumidity.disableProperty().bind(model.getBuoy().getSensors().getSensorTop().not());
    	buoySensorsTopLight.disableProperty().bind(model.getBuoy().getSensors().getSensorTop().not());
    	buoySensorsTopIR.disableProperty().bind(model.getBuoy().getSensors().getSensorTop().not());
    	buoySensorsBottomTemperature.disableProperty().bind(model.getBuoy().getSensors().getSensorBottom().not());
    	buoySensorsBottomHumidity.disableProperty().bind(model.getBuoy().getSensors().getSensorBottom().not());
    	buoySensorsBottomLight.disableProperty().bind(model.getBuoy().getSensors().getSensorBottom().not());
    	buoySensorsBottomIR.disableProperty().bind(model.getBuoy().getSensors().getSensorBottom().not());
    	buoySensorsTelemetryFront.disableProperty().bind(model.getBuoy().getSensors().getSensorTelemetry().not());
    	buoySensorsTelemetryBack.disableProperty().bind(model.getBuoy().getSensors().getSensorTelemetry().not());
    	buoySensorsTelemetryLeft.disableProperty().bind(model.getBuoy().getSensors().getSensorTelemetry().not());
    	buoySensorsTelemetryRight.disableProperty().bind(model.getBuoy().getSensors().getSensorTelemetry().not());

    	status.set("Ready");
	}

	public void setSession(ISession session) {
		this.session = session;
	}

}
