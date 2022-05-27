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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class OrdersReportsController implements Initializable {

    @FXML private NumberAxis incomeAxis;

    @FXML private BarChart<?, ?> ordersChart;

    @FXML private Text incomeTxt;

    @FXML private CategoryAxis monthAxis;

    @FXML private Button returnBtn;

    @FXML private ComboBox<String> storeCB;

    @FXML private Button userNameBtn;
    @FXML private Label yearIncome;

    @FXML private ComboBox<String> yearCB;
    private static ArrayList<Order> orderslst=new ArrayList<>();
    private String currentYear="all";
    private String currentStore="all";
    private int finalprice=0;


    public static void setOrders(ArrayList<Order> orders) {
        OrdersReportsController.orderslst=orders;
    }

    @FXML
    void getDetails(ActionEvent event) {

    }

    @FXML
    void returnFunc(ActionEvent event) throws IOException {
        App.setRoot("CatalogEmployee");

    }


    private int yearrandom=1;
    private String years[] =
            { "2022", "2021", "2020",
                    "2019", "2018" };
    private String stores[] =
            { "all","haifa", "tel aviv", "eilat",
                    "london", "new york" };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("orderlst is: "+orderslst);
        yearCB.setItems(FXCollections.observableArrayList(years));
        storeCB.setItems(FXCollections.observableArrayList(stores));
        EventHandler<ActionEvent> event =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e)
                    {
//                        selected.setText(yearCB.getValue() + " selected");
//                        currentyear=Integer.parseInt(yearCB.getSelectionModel().getSelectedItem());
                        yearrandom+=1000;
                        currentYear=yearCB.getSelectionModel().getSelectedItem();
                        initchart();
                    }
                };
        EventHandler<ActionEvent> event2 =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e)
                    {
//                        selected.setText(yearCB.getValue() + " selected");
//                        currentyear=Integer.parseInt(yearCB.getSelectionModel().getSelectedItem());
                        yearrandom+=1000;
                        currentStore=storeCB.getSelectionModel().getSelectedItem();
                        initchart();
                    }
                };

        // Set on action
        yearCB.getSelectionModel().selectFirst();
        yearCB.setOnAction(event);
        storeCB.getSelectionModel().selectFirst();
        storeCB.setOnAction(event2);
        yearrandom=Integer.parseInt(yearCB.getSelectionModel().getSelectedItem());
        initchart();

    }
    public void initchart(){
        ordersChart.setAnimated(false);
        ordersChart.getData().clear();
        ordersChart.layout();
        XYChart.Series dataSeries1 = new XYChart.Series();
        XYChart.Series dataSeries2 = new XYChart.Series();
        XYChart.Series dataSeries3 = new XYChart.Series();
        dataSeries1.setName("Arrangement");
        double [] incomePerMonth=new double[12];
        for(int i=0;i<orderslst.size();i++){
            //we must also check if the order year is equal to currentYear
            // and check if the order store is equal to currentStore
            if(orderslst.get(i).getStoreName()==currentStore||currentStore=="all") {
                incomePerMonth[0] += orderslst.get(i).getFinalPrice();
                finalprice+=orderslst.get(i).getFinalPrice();
            }
        }
        yearIncome.setText(Integer.toString(finalprice));
        Random rand = new Random();

        dataSeries1.getData().add(new XYChart.Data("Jan", rand.nextInt(yearrandom)));
        dataSeries1.getData().add(new XYChart.Data("Feb"  ,  rand.nextInt(yearrandom)));
        dataSeries1.getData().add(new XYChart.Data("mar"  ,  rand.nextInt(yearrandom)));
        dataSeries1.getData().add(new XYChart.Data("apr"  ,  rand.nextInt(yearrandom)));
        dataSeries1.getData().add(new XYChart.Data("may"  ,  rand.nextInt(yearrandom)));
        dataSeries1.getData().add(new XYChart.Data("jun"  ,  rand.nextInt(yearrandom)));
        dataSeries1.getData().add(new XYChart.Data("jul"  ,  rand.nextInt(yearrandom)));
        dataSeries1.getData().add(new XYChart.Data("aug"  ,  rand.nextInt(yearrandom)));
        dataSeries1.getData().add(new XYChart.Data("sep"  ,  rand.nextInt(yearrandom)));
        dataSeries1.getData().add(new XYChart.Data("oct"  ,  rand.nextInt(yearrandom)));
        dataSeries1.getData().add(new XYChart.Data("nov"  ,  rand.nextInt(yearrandom)));
        dataSeries1.getData().add(new XYChart.Data("dec"  ,  rand.nextInt(yearrandom)));

        dataSeries2.setName("pot");
//        Random rand = new Random();
        dataSeries2.getData().add(new XYChart.Data("Jan",  rand.nextInt(yearrandom)));
        dataSeries2.getData().add(new XYChart.Data("Feb"  , rand.nextInt(yearrandom)));
        dataSeries2.getData().add(new XYChart.Data("mar"  , rand.nextInt(yearrandom)));
        dataSeries2.getData().add(new XYChart.Data("apr"  , rand.nextInt(yearrandom)));
        dataSeries2.getData().add(new XYChart.Data("may"  , rand.nextInt(yearrandom)));
        dataSeries2.getData().add(new XYChart.Data("jun"  , rand.nextInt(yearrandom)));
        dataSeries2.getData().add(new XYChart.Data("jul"  , rand.nextInt(yearrandom)));
        dataSeries2.getData().add(new XYChart.Data("aug"  , rand.nextInt(yearrandom)));
        dataSeries2.getData().add(new XYChart.Data("sep"  , rand.nextInt(yearrandom)));
        dataSeries2.getData().add(new XYChart.Data("oct"  , rand.nextInt(yearrandom)));
        dataSeries2.getData().add(new XYChart.Data("nov"  , rand.nextInt(yearrandom)));
        dataSeries2.getData().add(new XYChart.Data("dec"  , rand.nextInt(yearrandom)));

        dataSeries3.setName("Bouquet");
//        Random rand = new Random();
        dataSeries3.getData().add(new XYChart.Data("Jan",  rand.nextInt(yearrandom)));
        dataSeries3.getData().add(new XYChart.Data("Feb"  , rand.nextInt(yearrandom)));
        dataSeries3.getData().add(new XYChart.Data("mar"  , rand.nextInt(yearrandom)));
        dataSeries3.getData().add(new XYChart.Data("apr"  , rand.nextInt(yearrandom)));
        dataSeries3.getData().add(new XYChart.Data("may"  , rand.nextInt(yearrandom)));
        dataSeries3.getData().add(new XYChart.Data("jun"  , rand.nextInt(yearrandom)));
        dataSeries3.getData().add(new XYChart.Data("jul"  , rand.nextInt(yearrandom)));
        dataSeries3.getData().add(new XYChart.Data("aug"  , rand.nextInt(yearrandom)));
        dataSeries3.getData().add(new XYChart.Data("sep"  , rand.nextInt(yearrandom)));
        dataSeries3.getData().add(new XYChart.Data("oct"  , rand.nextInt(yearrandom)));
        dataSeries3.getData().add(new XYChart.Data("nov"  , rand.nextInt(yearrandom)));
        dataSeries3.getData().add(new XYChart.Data("dec"  , rand.nextInt(yearrandom)));


        ordersChart.getData().add(dataSeries1);
        ordersChart.getData().add(dataSeries2);
        ordersChart.getData().add(dataSeries3);



    }
}
