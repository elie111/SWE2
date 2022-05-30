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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

public class IncomeReportsController implements Initializable {

    @FXML private NumberAxis incomeAxis;

    @FXML private BarChart<?, ?> incomeChart;

    @FXML private Text incomeTxt;

    @FXML private CategoryAxis monthAxis;

    @FXML private Button returnBtn;

    @FXML private ComboBox<String> storeCB;

    @FXML private Button userNameBtn;
    @FXML private Label yearIncome;

    @FXML private ComboBox<String> yearCB;
    private static ArrayList<Order> orderslst=new ArrayList<>();
    private String currentYear="2022";
    private String currentStore="all";
    private int finalprice=0;


    public static void setOrders(ArrayList<Order> orders) {
        IncomeReportsController.orderslst=orders;
    }

    @FXML
    void getDetails(ActionEvent event) {

    }

    @FXML
    void returnFunc(ActionEvent event) throws IOException {
        App.setRoot("ManageStoreController");
//        App.setRoot("ManageChainController"); // if the manager is a chain manager

    }


    private int yearrandom=1;
    private String years[] =
            { "2022", "2021", "2020",
                    "2019", "2018" };
    private String stores[] =
            { "all","Haifa", "Tel Aviv", "Eilat",
                    "London", "New York" };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
                        try {
                            initchart();
                        } catch (ParseException parseException) {
                            parseException.printStackTrace();
                        }
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
                        try {
                            initchart();
                        } catch (ParseException parseException) {
                            parseException.printStackTrace();
                        }
                    }
                };

        // Set on action
        yearCB.getSelectionModel().selectFirst();
        yearCB.setOnAction(event);
        storeCB.getSelectionModel().selectFirst();
        storeCB.setOnAction(event2);
        yearrandom=Integer.parseInt(yearCB.getSelectionModel().getSelectedItem());
        try {
            initchart();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    public void initchart() throws ParseException {
        finalprice=0;
        incomeChart.setAnimated(false);
        incomeChart.getData().clear();
        incomeChart.layout();
        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("Income");
        double [] incomePerMonth=new double[12];
        for(int i=0;i<orderslst.size();i++){
            String sDate1=orderslst.get(i).getDateTime();
            Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);

            if((orderslst.get(i).getStoreName().equals(currentStore)||currentStore.equals("all"))
            &&((date1.getYear()+1900)==Integer.parseInt(currentYear))) {
                incomePerMonth[date1.getMonth()] += orderslst.get(i).getFinalPrice();
                finalprice+=orderslst.get(i).getFinalPrice();
            }
        }
        yearIncome.setText(Integer.toString(finalprice));

        dataSeries1.getData().add(new XYChart.Data("Jan", incomePerMonth[0]));
        dataSeries1.getData().add(new XYChart.Data("Feb"  , incomePerMonth[1]));
        dataSeries1.getData().add(new XYChart.Data("mar"  , incomePerMonth[2]));
        dataSeries1.getData().add(new XYChart.Data("apr"  , incomePerMonth[3]));
        dataSeries1.getData().add(new XYChart.Data("may"  , incomePerMonth[4]));
        dataSeries1.getData().add(new XYChart.Data("jun"  , incomePerMonth[5]));
        dataSeries1.getData().add(new XYChart.Data("jul"  , incomePerMonth[6]));
        dataSeries1.getData().add(new XYChart.Data("aug"  , incomePerMonth[7]));
        dataSeries1.getData().add(new XYChart.Data("sep"  , incomePerMonth[8]));
        dataSeries1.getData().add(new XYChart.Data("oct"  , incomePerMonth[9]));
        dataSeries1.getData().add(new XYChart.Data("nov"  , incomePerMonth[10]));
        dataSeries1.getData().add(new XYChart.Data("dec"  , incomePerMonth[11]));

        incomeChart.getData().add(dataSeries1);




    }
}
