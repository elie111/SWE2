package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Flower;
import il.cshaifasweng.OCSFMediatorExample.entities.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderListEmployeeController implements Initializable {

    @FXML private TableColumn<?, ?> nameCol;

    @FXML private TableView<Order> orderList;

    @FXML private Text ordersText;

    @FXML private TableColumn<?, ?> priceCol;

    @FXML private TableColumn<?, ?> flowersCol;

    @FXML private Button returnBtn;

    @FXML private TableColumn<?, ?> storeCol;
    private static ArrayList<Order> orders=new ArrayList<>();
    public static void setOrders(ArrayList<Order> orders){
        OrderListEmployeeController.orders=orders;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderList.getItems().addAll(orders);
        Order order=new Order();
        order.setAddress("haifa");
        order.setFinalPrice(40);
        order.setUserID(5);
        ArrayList<Flower> flowers=new ArrayList<>();
        Flower flower=new Flower("name","type",1);
        Flower flower2=new Flower("name2","type2",2);
        flowers.add(flower);
        flowers.add(flower2);
        //order.setFlowers(flowers);
        orderList.getItems().add(order);


        nameCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        storeCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("finalPrice"));
        flowersCol.setCellValueFactory(new PropertyValueFactory<>("flowers"));

    }
    @FXML
    void returnFunc(ActionEvent event) throws IOException {
        App.setRoot("CatalogBoundary");
    }


}
