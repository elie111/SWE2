package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/** JavaFX App */
public class App extends Application {
    private static Scene scene;
    private static SimpleClient client = new SimpleClient("a", 3000);
    private static int orderID = 3548;

    public static SimpleClient getClient() {
        return client;
    }

    public static void setClient(SimpleClient clientA) {
        client = clientA;
    }

    @Override
    public void start(Stage stage) throws IOException {
    	EventBus.getDefault().register(this);
//    	client = SimpleClient.getClient();
//    	client.openConnection();
//        ArrayList<Object> arr = new ArrayList<>();
//        arr.add("#getcatalog");
//        client.sendToServer(arr);
//
//        scene = new Scene(loadFXML("LoginOrSignupBoundary"), 600, 400);
        scene = new Scene(loadFXML("FirstScreen"), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
    	EventBus.getDefault().unregister(this);
		super.stop();
	}
    
    @Subscribe
    public void onWarningEvent(WarningEvent event) {
    	Platform.runLater(() -> {
    		Alert alert = new Alert(AlertType.WARNING,
        			String.format("Message: %s\nTimestamp: %s\n",
        					event.getWarning().getMessage(),
        					event.getWarning().getTime().toString())
        	);
        	alert.show();
    	});
    }

	public static void main(String[] args) {
        launch();
    }

    public static void setOrderID(int newOrderID) {
        orderID = newOrderID;
    }

    public static int getOrderID() {
        return orderID;
    }
}