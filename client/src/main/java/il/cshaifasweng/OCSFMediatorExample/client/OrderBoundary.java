package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Flower;
import il.cshaifasweng.OCSFMediatorExample.entities.Order;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static il.cshaifasweng.OCSFMediatorExample.client.CatalogBoundary.getMap;


public class OrderBoundary {
    @FXML
    private AnchorPane Item;
    @FXML
    private TextArea txt;
    @FXML
    private ChoiceBox<String> str;
    @FXML
    private DatePicker datepick;
    @FXML
    private Label finalprice;
    @FXML
    private Button returnbtn;
    @FXML
    private Button confirm;
    @FXML
    private ListView<String> list;
    private int price=0;
    @FXML
    private ChoiceBox<String> payment;
    @FXML
    private ChoiceBox<String> hour;
    @FXML
    private ChoiceBox<String> minutes;

    private ArrayList<String> liststr = new ArrayList<String>();
    private ArrayList<String> cartlist = new ArrayList<String>(); //cart names
    private ArrayList<Flower> flowerslst=new ArrayList<>();
    private HashMap<Flower, Integer> map = CatalogBoundary.getMap();
    private static Label placeholdercart=new Label("cart is empty");


    private ObservableList<String> hourList = FXCollections.observableArrayList("" +
                    "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
            "20", "21", "22", "23");

    private ObservableList<String> minuteList = FXCollections.observableArrayList("" +
                    "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
            "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
            "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
            "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
            "50", "51", "52", "53", "54", "55", "56", "57", "58", "59");

    public void getCartItems() {

        for (Flower key : map.keySet()) {
            cartlist.add(key.getName() + " x" + map.get(key));
            for(int i=0;i<map.get(key);i++) {
                flowerslst.add(key);
            }
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
        placeholdercart.setStyle("-fx-text-fill: linear-gradient(#ff5400, #be1d00);" +
                "-fx-font-size:  14px;-fx-font-weight: bold;-fx-font-style: italic");
        list.setPlaceholder(placeholdercart);
        str.getItems().addAll(liststr);
        str.setValue("none");
        String pricetxt;
        int p = 0;
        for (Flower key : map.keySet()) {
            p += key.getPrice() * map.get(key);
        }

        pricetxt = p + " $";
        price=p;
        finalprice.setText(pricetxt);

        hour.setItems(hourList);
        minutes.setItems(minuteList);

        LocalTime currentTime = LocalTime.now();
        String hours = Integer.toString(currentTime.getHour());
        String minute = Integer.toString(currentTime.getMinute());
        hour.setValue(hours);
        minutes.setValue(minute);
        payment.getItems().add("my credit card");
        payment.getItems().add("new credit card");
        payment.setValue("my credit card");
        LocalDate s=LocalDate.now();
        datepick.setValue(s);
        datepick.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        // create a alert
        Alert a = new Alert(Alert.AlertType.NONE);

        // action event
        EventHandler<ActionEvent> event = new
                EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e)
                    {
                        // set alert type
                        a.setAlertType(Alert.AlertType.CONFIRMATION);

                        // show the dialog

                        a.setContentText("Order Completed !");
                        a.showAndWait();
                        Order order=new Order(price,flowerslst);
                        String msg="#addorder";
                        ArrayList<Object> arr=new ArrayList<>();
                        arr.add(msg);
                        arr.add(order);
                        try {
                            App.getClient().sendToServer(arr);
                            App.setRoot("catalogboundary");
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                    }
                };
        confirm.setOnAction(event);
    }


    @FXML
    public void returnfunc(javafx.event.ActionEvent actionEvent) throws IOException {
        App.setRoot("catalogboundary");
    }

    @FXML
    public void confirmorder(ActionEvent actionEvent) throws IOException {
        Order order=new Order(price,flowerslst);
        String msg="#addorder";
        ArrayList<Object> arr=new ArrayList<>();
        arr.add(msg);
        arr.add(order);
        App.getClient().sendToServer(arr);
        // create a label
        Label label = new Label("Order confirmed !");

        // create a popup
        Popup popup = new Popup();

        // set background
        label.setStyle(" -fx-background-color: white;");

        // add the label
        popup.getContent().add(label);
        popup.centerOnScreen();

        // set size of label
        label.setMinWidth(80);
        label.setMinHeight(50);
        //popup.show(popup.getOwnerWindow());
        App.setRoot("catalogboundary");


    }

    @FXML
    public void removefunc(ActionEvent event) throws IOException {
        String item=list.getSelectionModel().getSelectedItem();
        String item2;
        //mycart.getItems().remove(item);

        for ( Flower key : map.keySet() ) {
            if(item!=null&&item.startsWith(key.getName())==true){
                if(map.get(key)>1){

                    cartlist.remove(key.getName());

                    item2=key.getName()+" x"+String.valueOf(map.get(key)-1);
                    cartlist.add(item2);


                    map.put(key,map.get(key)-1);

                    list.getItems().remove(item);
                    list.getItems().add(item2);


                    //   Collections.sort(mycart.getItems());
                    list.refresh();
                }
                else{

                    cartlist.remove(key.getName());
                    list.getItems().remove(item);
                    map.remove(key);
                    list.refresh();
                }
                break;
            }
        }
        int p=0;
        for ( Flower key : map.keySet() ) {
            p +=key.getPrice()*map.get(key);
        }

        String pricetxt = p + " $";
        price=p;
        finalprice.setText(pricetxt);

        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {


            }
        });

    }
}