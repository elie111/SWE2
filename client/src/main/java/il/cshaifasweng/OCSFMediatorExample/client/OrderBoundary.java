package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Flower;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static il.cshaifasweng.OCSFMediatorExample.client.CatalogBoundary.getMap;

public class OrderBoundary {
    @FXML private AnchorPane Item;
    @FXML private TextArea txt;
    @FXML private ChoiceBox<String> str;
    @FXML private DatePicker datepick;
    @FXML private Label finalprice;
    @FXML private Button returnbtn;
    @FXML private ListView list;

    @FXML private ChoiceBox<String> payment;

    private ArrayList<String> liststr = new ArrayList<String>();
    private ArrayList<String> cartlist = new ArrayList<String>();
    HashMap<Flower,Integer> map=CatalogBoundary.getMap();
    public void getCartItems() {

        for ( Flower key : map.keySet() ) {
            cartlist.add(key.getName()+" x"+map.get(key));
        }
        list.getItems().addAll(cartlist);
    }



    @FXML
    void initialize() {
        liststr.add("none");
        liststr.add("tel-aviv");
        liststr.add("haifa");
        liststr.add("Herzilya");
        liststr.add("Ramat-Gan");
        getCartItems();


        str.getItems().addAll(liststr);
        str.setValue("none");
        String pricetxt;
        int p=0;
        for ( Flower key : map.keySet() ) {
            p +=key.getPrice()*map.get(key);
        }

        pricetxt =   p + " $";
        finalprice.setText(pricetxt);


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


    @FXML
    public void returnfunc(javafx.event.ActionEvent actionEvent) throws IOException {
        App.setRoot("catalogboundary");
    }
}