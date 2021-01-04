package fr.ensisa.darcel.buoys.service;

import java.io.IOException;

import fr.ensisa.darcel.buoys.service.model.Model;
import fr.ensisa.darcel.buoys.service.network.ISession;
import fr.ensisa.darcel.buoys.service.network.ServiceSession;
import fr.ensisa.darcel.buoys.service.view.BuoyController;
import fr.ensisa.darcel.buoys.network.Protocol;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Model model;
	private ISession session;

	private Scene initRootLayout() {
       try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/BuoyService.fxml"));
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
			session = new ServiceSession("localhost", Protocol.BUOYS_TCP_PORT);
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
		primaryStage.setTitle("Buoy - Service");
        primaryStage.show();
	}

    public static void main(String[] args) {
		launch(args);
	}

}
