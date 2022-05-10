package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import java.util.ArrayList;

public class OrderBoundary {
    @FXML private AnchorPane Item;
    @FXML private TextField txt;
    @FXML private ChoiceBox<String> str;
    @FXML private DatePicker datepick;
    @FXML private Label finalprice;

    @FXML private ChoiceBox<String> payment;

    private ArrayList<String> liststr = new ArrayList<String>();

    @FXML
    void initialize() {
        liststr.add("none");
        liststr.add("tel-aviv");
        liststr.add("haifa");
        liststr.add("Herzilya");
        liststr.add("Ramat-Gan");

        str.getItems().addAll(liststr);
        str.setValue("none");
        finalprice.setText("120$");

        payment.getItems().add("my credit card");
        payment.getItems().add("new credit card");
        payment.setValue("my credit card");

        datepick.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });
    }
}