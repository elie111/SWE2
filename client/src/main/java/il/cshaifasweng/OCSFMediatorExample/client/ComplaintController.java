package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Complaint;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ComplaintController implements Initializable {

    @FXML private TextField complaintTF;

    @FXML private TextField customerNameTF;

    @FXML private TextField orderNumberTF;

    @FXML private TextField responseTF;

    @FXML private Button returnBtn;

    @FXML private Button sendBtn;
    private static Complaint currentComplaint;

    public static void setCurrentComplaint(Complaint complaint) {
        ComplaintController.currentComplaint=complaint;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        complaintTF.setEditable(false);
        customerNameTF.setEditable(false);
        orderNumberTF.setEditable(false);
        responseTF.setEditable(true);
        complaintTF.setText(currentComplaint.getContent());
        customerNameTF.setText(Integer.toString(currentComplaint.getId()));
        //orderNumberTF.setText(currentComplaint.getOrderId());
    }
    @FXML
    public void returnFunc(ActionEvent event) throws IOException {
        App.setRoot("ComplaintListEmployee");

    }

    @FXML
    public void sendFunc(ActionEvent event) {

    }

}
