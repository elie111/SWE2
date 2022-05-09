package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {
    @FXML private TextField email;
    @FXML private PasswordField password;
    @FXML private Button loginBtn;
    @FXML private Button backBtn;

    public void initialize() {
        loginBtn.setDisable(true);
    }

    @FXML
    public void onClick(ActionEvent event) throws IOException {
        // user put username and password that are not only spaces or null
        String un = email.getText();
        String up = password.getText();

        // write to database
        ArrayList<Object> arr = new ArrayList<>();
        arr.add("#loginUser");
        arr.add(un);
        arr.add(up);
        App.getClient().sendToServer(arr);
    }

    public void keyReleasedPropert() {
        // disable signIn button while user name or password is empty or spaces
        String un = email.getText();
        String up = password.getText();
        // check if user put input and that the input isn't spaces
        boolean isDisable = (un.isEmpty() || un.trim().isEmpty()) || (up.isEmpty() || up.trim().isEmpty());
        loginBtn.setDisable(isDisable);
    }

    public void showMessage(boolean answer, User user) {

    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        App.setRoot("loginOrsignupBoundary");
    }
}
