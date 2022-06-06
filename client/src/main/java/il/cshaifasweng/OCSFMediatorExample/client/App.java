package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/** JavaFX App */
public class App extends Application {
    @FXML private TextField ipText;
    @FXML private TextField portText;
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
<<<<<<< Updated upstream
    	EventBus.getDefault().register(this);
    	client = SimpleClient.getClient();
    	client.openConnection();
        ArrayList<Object> arr = new ArrayList<>();
        arr.add("#getcatalog");
        client.sendToServer(arr);
        ArrayList<Object> arr2 = new ArrayList<>();
        arr2.add("#getorders");
        client.sendToServer(arr2);
        ArrayList<Object> arr3 = new ArrayList<>();
        arr3.add("#getcomplaints");
        client.sendToServer(arr3);

        scene = new Scene(loadFXML("LoginOrSignupBoundary"), 600, 400);
=======
//    	EventBus.getDefault().register(this);
//    	client = SimpleClient.getClient();
//    	client.openConnection();
//        ArrayList<Object> arr = new ArrayList<>();
//        arr.add("#getcatalog");
//        client.sendToServer(arr);
//
//        scene = new Scene(loadFXML("LoginOrSignupBoundary"), 600, 400);
//        stage.setScene(scene);
//        stage.show();
        scene = new Scene(loadFXML("FirstScreen"), 600, 400);
>>>>>>> Stashed changes
        stage.setScene(scene);
        stage.show();
    }

//    @FXML
//    public void sendA(ActionEvent event) throws IOException {
//        String ip = ipText.getText();
//        String port = portText.getText();
//
//        EventBus.getDefault().register(this);
//        client = SimpleClient.getClient(ip, port);
//        client.openConnection();
//        ArrayList<Object> arr = new ArrayList<>();
//        arr.add("#getcatalog");
//        client.sendToServer(arr);
//
//        App.setRoot("LoginOrSignupBoundary");
//    }

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