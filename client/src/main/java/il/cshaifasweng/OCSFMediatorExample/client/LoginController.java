package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.User;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.security.auth.RefreshFailedException;
import javax.security.auth.Refreshable;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginController {
    @FXML private TextField email;
    @FXML private PasswordField password;
    @FXML private Button loginBtn;

    public void initialize() {
        loginBtn.setDisable(true);
    }

    public void onClick() throws IOException {
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

    public void keyReleasedPropert(){
        // disable signIn button while user name or password is empty or spaces
        String un = email.getText();
        String up = password.getText();
        // check if user put input and that the input isn't spaces
        boolean isDisable = (un.isEmpty() || un.trim().isEmpty()) || (up.isEmpty() || up.trim().isEmpty());
        loginBtn.setDisable(isDisable);
    }

    public void showMessage(boolean answer, User user){

    }
}
