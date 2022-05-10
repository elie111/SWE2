package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;

public class LoginOrSignupController {
    @FXML private Text welcome;
    @FXML private Button loginBtn;
    @FXML private Button signupBtn;
    @FXML private Button showCatalogBtn;

    // go to registration screen
    @FXML
    public void signupBtn(ActionEvent event) throws IOException {
        App.setRoot("registrationBoundary");
    }

    // go to login screen
    @FXML
    public void loginBtn(ActionEvent event) throws IOException {
        App.setRoot("loginBoundary");
    }

    // go to catalog screen
    @FXML
    public void showCatalogBtn(ActionEvent event) throws IOException {
        App.setRoot("catalogboundary");
    }
}
