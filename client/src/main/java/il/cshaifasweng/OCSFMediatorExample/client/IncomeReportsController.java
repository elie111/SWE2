package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class IncomeReportsController implements Initializable {



    @FXML private Text incomeTxt;

    @FXML private CategoryAxis monthAxis;

    @FXML private Button returnBtn;

    @FXML private ComboBox<String> storeCB;

    @FXML private Button userName;
    @FXML private Label finalres;
    @FXML private  DatePicker start;
    @FXML private  DatePicker end;


    private static ArrayList<Order> orderslst=new ArrayList<>();
    private String currentYear="2022";
    private String currentStore="all";
    private int finalprice=0;


    public static void setOrders(ArrayList<Order> orders) {
        IncomeReportsController.orderslst=orders;
    }



    @FXML
    void returnFunc(ActionEvent event) throws IOException {
        App.setRoot("ManageStore");
//        App.setRoot("ManageChainController"); // if the manager is a chain manager

    }



    private String stores[] =
            { "all","Haifa", "Tel Aviv", "Eilat",
                    "London", "New York" };

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
    public void confirm(ActionEvent actionEvent) {
        double sum=0;
        LocalDate strt= start.getValue();
        LocalDate endd=end.getValue();
        if(strt!=null && endd!=null) {
            for (Order order : orderslst) {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
                LocalDate l = LocalDate.parse(order.getDateTime().substring(0, 10), formatter);
                System.out.println(EntityHolder.getStoreM().getStoreName());
                System.out.println(order.getStoreName());

                String managerstore=EntityHolder.getStoreM().getStoreName();
                String orderstore=order.getStoreName();
                if (l.isAfter(strt) && l.isBefore(endd) && managerstore.equals(orderstore))
                    sum += order.getFinalPrice();
            }
        }
        finalres.setText( String.valueOf(sum));
    }
}