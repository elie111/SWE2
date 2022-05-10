package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML private Text signin;
    @FXML private Label labelE;
    @FXML private Label labelP;
    @FXML private TextField email;
    @FXML private TextField password;
    @FXML private Button loginBtn;
    @FXML private Button backBtn;
    @FXML private TextArea message = new TextArea();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginBtn.setDisable(true);
        message.setText("One or more details are wrong.\nPlease fill again");
        message.setStyle("-fx-font-weight: bold");
        message.setEditable(false);
        message.setVisible(false);
        loginBtn.disableProperty().bind(
                Bindings.isEmpty(email.textProperty())
                        .or(Bindings.isEmpty(password.textProperty())));
    }

    @FXML
    public void onClick(ActionEvent event) throws IOException {
        // user put username and password that are not only spaces or null
        String un = email.getText();
        String up = password.getText();

        if(un.trim().isEmpty() || up.trim().isEmpty()) {
            showMessage();
        }
        else {
            // write to database
            ArrayList<Object> arr = new ArrayList<>();
            arr.add("#loginUser");
            arr.add(un);
            arr.add(up);
            App.getClient().sendToServer(arr);
        }
    }

//    public void keyReleasedPropert() {
//        // disable signIn button while user name or password is empty or spaces
//        String un = email.getText();
//        String up = password.getText();
//        // check if user put input and that the input isn't spaces
//        boolean isDisable = (un.isEmpty() || un.trim().isEmpty() || up.isEmpty() || up.trim().isEmpty());
//        loginBtn.setDisable(isDisable);
//    }

    public void showMessage() {
        message.setText("One or more details are wrong.\nPlease fill again");
        message.setStyle("-fx-font-weight: bold");
        message.setEditable(false);
        message.setVisible(true);
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        App.setRoot("loginOrsignupBoundary");
    }

    public void nextStep(int i) {
        if(i == 1) {
            try {
                moveForward();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(i == 2) {
            showMessage();
        }
    }

    public void moveForward() throws IOException {
        App.setRoot("catalogboundary");
    }
}
