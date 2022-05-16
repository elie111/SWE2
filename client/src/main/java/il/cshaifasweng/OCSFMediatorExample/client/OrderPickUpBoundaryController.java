package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Flower;
import il.cshaifasweng.OCSFMediatorExample.entities.Order;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Popup;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class OrderPickUpBoundaryController implements Initializable {
    @FXML private Text orderL;
    @FXML private Button userName;
    @FXML private Label addCardL;
    @FXML private TextArea txt;
    @FXML private Label timeL;
    @FXML private ComboBox<String> timeChoose;
    @FXML private Label pickupDateL;
    @FXML private DatePicker datePick;
    @FXML private Label pickupTimeL;
    @FXML private ComboBox<String> hour;
    @FXML private Label seperator;
    @FXML private ComboBox<String> minutes;
    @FXML private Button applyR;
    @FXML private Button cancelR;
    @FXML private Label chooseStoreL;
    @FXML private ComboBox<String> chooseStore;
    @FXML private Label paymentL;
    @FXML private ComboBox<String> str;
    @FXML private Label creditL;
    @FXML private TextField creditText;
    @FXML private Label labelValid;
    @FXML private ComboBox<String> chooseMonth;
    @FXML private Label slash;
    @FXML private ComboBox<String> chooseYear;
    @FXML private Label labelCVV;
    @FXML private TextField textCVV;
    @FXML private Label cartL;
    @FXML private ListView<String> list;
    @FXML private Label priceL;
    @FXML private Label finalPrice;
    @FXML private Button removeBtn;
    @FXML private Button confirm;
    @FXML private Button returnBtn;

    private double refund;
    private static Label placeHolderCart = new Label("Cart is empty");
    private HashMap<Flower, Integer> map = CatalogBoundaryController.getMap();
    private ArrayList<String> cartList = new ArrayList<String>();
    private ArrayList<Flower> flowersList = new ArrayList<>();
    private double p = 0;
    private int refundFlag = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(EntityHolder.getTable() == -1) {
            userName.setText("Register / Login");
        }
        else {
            if(EntityHolder.getTable() == 0) {
                userName.setText(EntityHolder.getUser().getName());
            }
            else if(EntityHolder.getTable() == 1) {
                userName.setText(EntityHolder.getEmployee().getName());
            }
            else if(EntityHolder.getTable() == 2) {
                userName.setText(EntityHolder.getStoreM().getName());
            }
            else if(EntityHolder.getTable() == 3) {
                userName.setText(EntityHolder.getChainM().getName());
            }
        }

        hour.getItems().addAll("00", "01", "02", "03", "04", "05", "06", "07",
                                    "08", "09", "10", "11", "12", "13", "14", "15",
                                    "16", "17", "18", "19", "20", "21", "22", "23");
        minutes.getItems().addAll("00", "15", "30", "45");
        chooseMonth.getItems().addAll("01", "02", "03", "04", "05", "06",
                                           "07", "08", "09", "10", "11", "12");
        chooseYear.getItems().addAll("22", "23", "24", "25", "26", "27",
                                          "28", "29", "30", "31", "32", "33");
        chooseStore.getItems().addAll("Haifa", "Tel Aviv", "New york", "Eilat", "London");
        
        timeChoose.getItems().addAll("Supply immediately", "Pick Date & Time");
        pickupDateL.setVisible(false);
        datePick.setVisible(false);
        pickupTimeL.setVisible(false);
        hour.setVisible(false);
        seperator.setVisible(false);
        minutes.setVisible(false);
        timeChoose.getSelectionModel().selectedItemProperty().addListener((option, oldV, newV) -> {
            if(newV.equals("Pick Date & Time")) {
                pickupDateL.setVisible(true);
                datePick.setVisible(true);
                pickupTimeL.setVisible(true);
                hour.setVisible(true);
                seperator.setVisible(true);
                minutes.setVisible(true);
            }
            else {
                pickupDateL.setVisible(false);
                datePick.setVisible(false);
                pickupTimeL.setVisible(false);
                hour.setVisible(false);
                seperator.setVisible(false);
                minutes.setVisible(false);
            }
        });

        refund = EntityHolder.getUser().getRefund();
        if(refund == 0) {
            applyR.setVisible(false);
            cancelR.setVisible(false);
        }
        else {
            cancelR.setDisable(true);
        }

        str.getItems().addAll("My Credit Card", "New Credit Card");
        creditL.setVisible(false);
        creditText.setVisible(false);
        labelValid.setVisible(false);
        chooseMonth.setVisible(false);
        slash.setVisible(false);
        chooseYear.setVisible(false);
        labelCVV.setVisible(false);
        textCVV.setVisible(false);
        str.getSelectionModel().selectedItemProperty().addListener((option, oldV, newV) -> {
            if(newV.equals("New Credit Card")) {
                creditL.setVisible(true);
                creditText.setVisible(true);
                labelValid.setVisible(true);
                chooseMonth.setVisible(true);
                slash.setVisible(true);
                chooseYear.setVisible(true);
                labelCVV.setVisible(true);
                textCVV.setVisible(true);
            }
            else {
                creditL.setVisible(false);
                creditText.setVisible(false);
                labelValid.setVisible(false);
                chooseMonth.setVisible(false);
                slash.setVisible(false);
                chooseYear.setVisible(false);
                labelCVV.setVisible(false);
                textCVV.setVisible(false);
            }
        });

        getCartItems();
        placeHolderCart.setStyle("-fx-text-fill: linear-gradient(#ff5400, #be1d00);-fx-font-size: 14px;-fx-font-weight: bold;-fx-font-style: italic");
        list.setPlaceholder(placeHolderCart);

        String priceTxt;
        for (Flower key : map.keySet()) {
            p += key.getPrice() * map.get(key);
        }
        priceTxt = p + " $";
        finalPrice.setText(priceTxt);

        removeBtn.disableProperty().bind(list.getSelectionModel().selectedItemProperty().isNull());
        confirm.disableProperty().bind(timeChoose.valueProperty().isNull()
                .or(chooseStore.valueProperty().isNull())
                .or(str.valueProperty().isNull()));

        LocalDate s = LocalDate.now();
        datePick.setValue(s);
        datePick.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });
    }

    public void getCartItems() {
        for (Flower key : map.keySet()) {
            cartList.add(key.getName() + " x" + map.get(key));
            for(int i = 0; i < map.get(key); i++) {
                flowersList.add(key);
            }
        }
        list.getItems().addAll(cartList);
    }

    @FXML
    public void applyR(ActionEvent event) {
        refundFlag = 1;
        p -= refund;
        String priceTxt;
        priceTxt = p + " $";
        finalPrice.setText(priceTxt);
        applyR.setDisable(true);
        cancelR.setDisable(false);
    }

    @FXML
    public void cancelR(ActionEvent event) {
        refundFlag = 0;
        p += refund;
        String priceTxt;
        priceTxt = p + " $";
        finalPrice.setText(priceTxt);
        applyR.setDisable(false);
        cancelR.setDisable(true);
    }

    @FXML
    public void removeFunc(ActionEvent event) {
        String item = list.getSelectionModel().getSelectedItem();
        String item2;

        for (Flower key : map.keySet()) {
            if(item != null && item.startsWith(key.getName()) == true) {
                if(map.get(key) > 1) {
                    cartList.remove(key.getName());
                    item2 = key.getName() + " x" + String.valueOf(map.get(key) - 1);
                    cartList.add(item2);
                    map.put(key, map.get(key) - 1);
                    list.getItems().remove(item);
                    list.getItems().add(item2);
                    list.refresh();
                }
                else {
                    cartList.remove(key.getName());
                    list.getItems().remove(item);
                    map.remove(key);
                    list.refresh();
                }
                break;
            }
        }

        p = 0;
        for (Flower key : map.keySet()) {
            p += key.getPrice() * map.get(key);
        }
        if(refundFlag == 1) {
            p -= refund;
        }
        String pricetxt = p + " $";
        finalPrice.setText(pricetxt);
        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {}
        });
    }

    @FXML
    public void confirmOrder(ActionEvent actionEvent) {
        int userId = EntityHolder.getUser().getID();
        List<Flower> flowers = getItems();
        String card = checkCard();
        String formOfS = timeChoose.getSelectionModel().getSelectedItem();
        // if()

        LocalDateTime localDateTime;
        String timeC = timeChoose.getSelectionModel().getSelectedItem();
        if(timeC.equals("Supply immediately")) {
            localDateTime = LocalDateTime.now();
        }
        else {
            LocalDate day = datePick.getValue();

        }
    }

    @FXML
    public void returnFunc(ActionEvent actionEvent) throws IOException {
        App.setRoot("CatalogBoundary");
    }

    public List<Flower> getItems() {
        List<Flower> list = new ArrayList<>();
        for(Flower key : map.keySet()) {
            list.add(key);
        }
        return list;
    }

    public String checkCard() {
        String card = txt.getText();
        if(card == null) {
            card = "";
        }
        return card;
    }
}