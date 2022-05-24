package il.cshaifasweng.OCSFMediatorExample.client;

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
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class IncomeReportsController implements Initializable {

    @FXML
    private NumberAxis incomeAxis;

    @FXML
    private BarChart<?, ?> incomeChart;

    @FXML
    private Text incomeTxt;

    @FXML
    private CategoryAxis monthAxis;

    @FXML
    private Button returnBtn;

    @FXML
    private ComboBox<String> storeCB;

    @FXML
    private Button userNameBtn;

    @FXML
    private ComboBox<String> yearCB;

    @FXML
    void getDetails(ActionEvent event) {

    }

    @FXML
    void returnFunc(ActionEvent event) throws IOException {
        App.setRoot("CatalogEmployee");

    }


    private int currentyear=1;
    private String years[] =
            { "2022", "2021", "2020",
                    "2019", "2018" };
    private String stores[] =
            { "all","haifa", "tel aviv", "eilat",
                    "london", "new york" };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        yearCB.setItems(FXCollections.observableArrayList(years));
        storeCB.setItems(FXCollections.observableArrayList(stores));
        EventHandler<ActionEvent> event2 =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e)
                    {
//                        selected.setText(yearCB.getValue() + " selected");
//                        currentyear=Integer.parseInt(yearCB.getSelectionModel().getSelectedItem());
                        currentyear+=1000;
                        initchart();
                    }
                };
        EventHandler<ActionEvent> event =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e)
                    {
//                        selected.setText(yearCB.getValue() + " selected");
//                        currentyear=Integer.parseInt(yearCB.getSelectionModel().getSelectedItem());
                        currentyear+=1000;
                        initchart();
                    }
                };

        // Set on action
        yearCB.getSelectionModel().selectFirst();
        yearCB.setOnAction(event);
        storeCB.getSelectionModel().selectFirst();
        storeCB.setOnAction(event2);
        currentyear=Integer.parseInt(yearCB.getSelectionModel().getSelectedItem());
        initchart();

    }
    public void initchart(){
        incomeChart.setAnimated(false);
        incomeChart.getData().clear();
        incomeChart.layout();
        XYChart.Series dataSeries1 = new XYChart.Series();
        XYChart.Series dataSeries2 = new XYChart.Series();
        dataSeries1.setName("all");

        dataSeries1.getData().add(new XYChart.Data("Jan", currentyear));
        dataSeries1.getData().add(new XYChart.Data("Feb"  , currentyear));
        dataSeries1.getData().add(new XYChart.Data("mar"  , currentyear));
        dataSeries1.getData().add(new XYChart.Data("apr"  , currentyear));
        dataSeries1.getData().add(new XYChart.Data("may"  , currentyear));
        dataSeries1.getData().add(new XYChart.Data("jun"  , currentyear));
        dataSeries1.getData().add(new XYChart.Data("jul"  , currentyear));
        dataSeries1.getData().add(new XYChart.Data("aug"  , currentyear));
        dataSeries1.getData().add(new XYChart.Data("sep"  , currentyear));
        dataSeries1.getData().add(new XYChart.Data("oct"  , currentyear));
        dataSeries1.getData().add(new XYChart.Data("nov"  , currentyear));
        dataSeries1.getData().add(new XYChart.Data("dec"  , currentyear));

        dataSeries2.setName("self pickup");
        Random rand = new Random();
        dataSeries2.getData().add(new XYChart.Data("Jan",  rand.nextInt(currentyear)));
        dataSeries2.getData().add(new XYChart.Data("Feb"  , rand.nextInt(currentyear)));
        dataSeries2.getData().add(new XYChart.Data("mar"  , rand.nextInt(currentyear)));
        dataSeries2.getData().add(new XYChart.Data("apr"  , rand.nextInt(currentyear)));
        dataSeries2.getData().add(new XYChart.Data("may"  , rand.nextInt(currentyear)));
        dataSeries2.getData().add(new XYChart.Data("jun"  , rand.nextInt(currentyear)));
        dataSeries2.getData().add(new XYChart.Data("jul"  , rand.nextInt(currentyear)));
        dataSeries2.getData().add(new XYChart.Data("aug"  , rand.nextInt(currentyear)));
        dataSeries2.getData().add(new XYChart.Data("sep"  , rand.nextInt(currentyear)));
        dataSeries2.getData().add(new XYChart.Data("oct"  , rand.nextInt(currentyear)));
        dataSeries2.getData().add(new XYChart.Data("nov"  , rand.nextInt(currentyear)));
        dataSeries2.getData().add(new XYChart.Data("dec"  , rand.nextInt(currentyear)));




        incomeChart.getData().add(dataSeries1);
        incomeChart.getData().add(dataSeries2);

    }
}
