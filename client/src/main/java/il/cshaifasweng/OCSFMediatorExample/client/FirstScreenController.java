package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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

    @FXML
    public void sendPress(ActionEvent event) throws IOException {
        String ip = ipText.getText();
        String port = portText.getText();

        SimpleClient client = SimpleClient.getClient(ip, port);
        App.setClient(client);
        client.openConnection();
        ArrayList<Object> arr = new ArrayList<>();
        arr.add("#getcatalog");
        client.sendToServer(arr);
        ArrayList<Object> arr2 = new ArrayList<>();
        arr2.add("#getAllLists");
        App.getClient().sendToServer(arr2);

        App.setRoot("LoginOrSignupBoundary");
    }
}
