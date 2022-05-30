package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrderListEmployeeController implements Initializable {
    @FXML
    private Text OrderListL;
    @FXML private Button userName;

    @FXML private TableView<OrderHolder> tableView;
    @FXML private TableColumn<OrderHolder, String> orderN;
    @FXML private TableColumn<OrderHolder, String> orderReceiver;
    @FXML private TableColumn<OrderHolder, String> orderDescription;
    @FXML private TableColumn<OrderHolder, String> orderDate;
    @FXML private TableColumn<OrderHolder, String> orderAddress;

    @FXML private Button returnBtn;
    @FXML private Button suppliedBtn;

    private static ObservableList<OrderHolder> list;

    private String Email;

    public static void setMyOrders(ObservableList<OrderHolder> orders) {
        OrderListEmployeeController.list = FXCollections.observableList(orders);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(EntityHolder.getTable() == -1) {
            userName.setText("Register / Login");
        }
        else {
            if(EntityHolder.getTable() == 0) {
                userName.setText(EntityHolder.getUser().getName());
                Email = EntityHolder.getUser().getEmail();
            }
            else if(EntityHolder.getTable() == 1) {
                userName.setText(EntityHolder.getEmployee().getName());
                Email = EntityHolder.getEmployee().getEmail();
            }
            else if(EntityHolder.getTable() == 2) {
                userName.setText(EntityHolder.getStoreM().getName());
                Email = EntityHolder.getStoreM().getEmail();
            }
            else if(EntityHolder.getTable() == 3) {
                userName.setText(EntityHolder.getChainM().getName());
                Email = EntityHolder.getChainM().getEmail();
            }
        }

        orderN.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        orderReceiver.setCellValueFactory(new PropertyValueFactory<>("receiverName"));
        orderDescription.setCellValueFactory(new PropertyValueFactory<>("flowers"));
        orderDate.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        orderAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tableView.setItems(FXCollections.observableList(list)); // put values in the rows for each order

        suppliedBtn.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());
    }

    @FXML
    public void getDetails(ActionEvent event) throws IOException {
        if(userName.getText().equals("Register / Login")) {
            App.setRoot("LoginOrSignupBoundary");
        }
        else {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Message");
            a.setHeaderText("Do you wish to disconnect?");
            Optional<ButtonType> result = a.showAndWait();
            if (result.get() == ButtonType.OK) {
                ArrayList<Object> arr = new ArrayList<>();
                arr.add("#disconnecting");
                arr.add(Email);
                App.getClient().sendToServer(arr);

                App.setRoot("LoginOrSignupBoundary");
                EntityHolder.setTable(-1);
                EntityHolder.setUser(null);
                EntityHolder.setEmployee(null);
                EntityHolder.setStoreM(null);
                EntityHolder.setChainM(null);
                EntityHolder.setID(-1);
                CatalogBoundaryController c = new CatalogBoundaryController();
                c.refreshAfterDisconnect();
            }
        }
    }

    @FXML
    public void backBtn(ActionEvent event) throws IOException {
        App.setRoot("CatalogEmployee");
    }

    @FXML
    public void supplyOrder(ActionEvent event) throws IOException {
        int index = tableView.getSelectionModel().getSelectedIndex();
        if(index > -1) {
            ArrayList<Object> msg = new ArrayList<>();
            msg.add("#supplyOrder");
            msg.add(index);

            App.getClient().sendToServer(msg);
            message();
            tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
        }
    }

    public void message() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Message");
        a.setHeaderText("Order has been supplied");
        a.showAndWait();
    }
}
