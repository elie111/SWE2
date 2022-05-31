package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Flower;
import il.cshaifasweng.OCSFMediatorExample.entities.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.Flow;

public class OrdersReportsController implements Initializable {

    @FXML private DatePicker endDate;

    @FXML private Button generateBtn;

    @FXML private Text incomeTxt;

    @FXML private Label priceLabel;

    @FXML private PieChart ordersChart;

    @FXML private Button returnBtn;

    @FXML private DatePicker startDate;

    @FXML private Label storeLabel;

    @FXML private Button userNameBtn;

    @FXML
    private Label yearIncome;
    private static ArrayList<Order> orderslst=new ArrayList<>();
    private int numOrders=0;


    public static void setOrders(ArrayList<Order> orders) {
        OrdersReportsController.orderslst=orders;
    }

    @FXML
    void getDetails(ActionEvent event) {

    }

    @FXML
    void returnFunc(ActionEvent event) throws IOException {
        App.setRoot("ManageStore");
//        App.setRoot("ManageChainController"); // if the manager is a chain manager
        //App.getClient().sendToServer();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LocalDate s = LocalDate.now();
        endDate.setValue(s);
        startDate.setValue(s.minusDays(7));
        try {
            initchart();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    public void initchart() throws ParseException {
        numOrders=0;
        ordersChart.getData().clear();
        ordersChart.layout();
        int pot=0;
        int arrang=0;
        int boq=0;
        for(int i=0;i<orderslst.size();i++) {
            Date date1 = orderslst.get(i).getCurrentDate();
            if (date1.after(Date.from((startDate.getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                    && date1.before(Date.from(endDate.getValue().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
//                for(int k=0;k<orderslst.get(i).getFlowers().length();k++){
//                    Flower flower=orderslst.get(i).getFlowers();
//                }

                numOrders += 1;
            }
        }
            PieChart.Data slice1 = new PieChart.Data("Pot", 25);
            PieChart.Data slice2 = new PieChart.Data("Arrangment"  , 100);
            PieChart.Data slice3 = new PieChart.Data("Bouquet" , 46);

            ordersChart.getData().add(slice1);
            ordersChart.getData().add(slice2);
            ordersChart.getData().add(slice3);

        yearIncome.setText(Integer.toString(numOrders));



    }
    @FXML
    void generateFunc(ActionEvent event) throws ParseException {
        initchart();
    }
}
