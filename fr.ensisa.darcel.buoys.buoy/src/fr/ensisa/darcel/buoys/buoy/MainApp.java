package fr.ensisa.darcel.buoys.buoy;

import java.io.IOException;

import fr.ensisa.darcel.buoys.buoy.model.Model;
import fr.ensisa.darcel.buoys.buoy.network.BuoySession;
import fr.ensisa.darcel.buoys.buoy.network.ISession;
import fr.ensisa.darcel.buoys.buoy.view.BuoyController;
import fr.ensisa.darcel.buoys.network.Protocol;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

	private ISession session;
	private Model model;

	private Scene initRootLayout() {
       try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/BuoyBuoy.fxml"));
            Parent rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            BuoyController controller = loader.getController();
            controller.setModel(getModel());
            controller.setSession(getSession());
            return scene;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}

    public ISession getSession() {
		if (session == null) {
			session = new BuoySession("localhost", Protocol.BUOYS_TCP_PORT, Protocol.BUOYS_UDP_PORT);
			session.open();
		}
		return session;
	}

    public Model getModel() {
		if (model == null) model = new Model();
		return model;
	}

	@Override
	public void start(Stage primaryStage) {
		Scene scene = initRootLayout();
        primaryStage.setScene(scene);
		primaryStage.setTitle("Buoy - Buoy");
        primaryStage.show();
	}

    public static void main(String[] args) {
		launch(args);
	}

}
