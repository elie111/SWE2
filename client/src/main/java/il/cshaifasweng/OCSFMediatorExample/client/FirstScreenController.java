package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.greenrobot.eventbus.EventBus;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FirstScreenController implements Initializable {
    @FXML private Text dataL;
    @FXML private Label ipL;
    @FXML private TextField ipText;
    @FXML private Label portL;
    @FXML private TextField portText;
    @FXML private Button sendBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

//    @FXML
//    public void sendPress(ActionEvent event) throws IOException {
//        String ip = ipText.getText();
//        String port = portText.getText();
//
//        EventBus.getDefault().register(this);
//        SimpleClient client = SimpleClient.getClient(ip, port);
//        App.setClient(client);
//        client.openConnection();
//        ArrayList<Object> arr = new ArrayList<>();
//        arr.add("#getcatalog");
//        client.sendToServer(arr);
//        App.setRoot("LoginOrSignupBoundary");
//    }

    public void sendPress(javafx.event.ActionEvent event) throws IOException {
        String ip = ipText.getText();
        String port = portText.getText();

        EventBus.getDefault().register(this);
        SimpleClient client = SimpleClient.getClient(ip, port);
        App.setClient(client);
        client.openConnection();
        ArrayList<Object> arr = new ArrayList<>();
        arr.add("#getcatalog");
        client.sendToServer(arr);
        App.setRoot("LoginOrSignupBoundary");
    }
}
