package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Complaint;
import il.cshaifasweng.OCSFMediatorExample.entities.Order;
import javafx.collections.FXCollections;
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
import java.util.*;

public class ComplaintsReportsController implements Initializable {

    @FXML private BarChart<?, ?> complaintsChart;

    @FXML private NumberAxis incomeAxis;

    @FXML private Text incomeTxt;

    @FXML private CategoryAxis monthAxis;

    @FXML private Label priceLabel;

    @FXML private Button returnBtn;


    @FXML private Button userNameBtn;

    @FXML private ComboBox<String> yearCB;

    @FXML private Label yearIncome;

    private static ArrayList<Complaint> compList=new ArrayList<>();
    private String currentTimePeriod="all";
    private String currentStore="all";
    private int numComp=0;

    public static void setComplaints(ArrayList<Complaint> complaints) {
        ComplaintsReportsController.compList=complaints;
    }

    @FXML void getDetails(ActionEvent event) {

    }

    @FXML
    void returnFunc(ActionEvent event) throws IOException {
        App.setRoot("ManageStoreController");
//        App.setRoot("ManageChainController"); // if the manager is a chain manager

    }
    private String timeperiod[] =
            { "all" ,"last week", "last month", "last year"};
    private String stores[] =
            { "all","Haifa", "Tel Aviv", "Eilat",
                    "London", "New York" };
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        yearCB.setItems(FXCollections.observableArrayList(timeperiod));
        EventHandler<ActionEvent> event =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e)
                    {

                        currentTimePeriod=yearCB.getSelectionModel().getSelectedItem();
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
        try {
            initchart();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void initchart() throws ParseException {
        numComp=0;
        complaintsChart.setAnimated(false);
        complaintsChart.getData().clear();
        complaintsChart.layout();
        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("Complaints");




             if(currentTimePeriod.equals("all")){
                 int [] reportsPerMonth=new int[5];
                 for(int i=0;i<compList.size();i++) {
                     String sDate1 = compList.get(i).getDateTime();
                     Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
                     reportsPerMonth[(date1.getYear()+1900)-2018]+=1;
                 }
                 yearIncome.setText(Integer.toString(numComp));
                 dataSeries1.getData().add(new XYChart.Data("2018", reportsPerMonth[0]));
                 dataSeries1.getData().add(new XYChart.Data("2019"  , reportsPerMonth[1]));
                 dataSeries1.getData().add(new XYChart.Data("2020"  , reportsPerMonth[2]));
                 dataSeries1.getData().add(new XYChart.Data("2021"  , reportsPerMonth[3]));
                 dataSeries1.getData().add(new XYChart.Data("2022"  , reportsPerMonth[4]));

             }
            if(currentTimePeriod.equals("last week")){
                int [] reportsPerMonth=new int[7];
                for(int i=0;i<compList.size();i++) {
                    String sDate1 = compList.get(i).getDateTime();
                    Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);

                    Calendar currentCalendar = Calendar.getInstance();
                    int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
                    int year = currentCalendar.get(Calendar.YEAR);
                    Calendar targetCalendar = Calendar.getInstance();
                    targetCalendar.setTime(date1);
                    int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
                    int targetYear = targetCalendar.get(Calendar.YEAR);
                    if( week == targetWeek && year == targetYear){
                        System.out.println("test");
                        reportsPerMonth[date1.getDay()]+=1;
                    }


                }
                Date date = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
                c.add(Calendar.DATE, -i - 7);
                Date start = c.getTime();
                c.add(Calendar.DATE, 6);
                Date end = c.getTime();

                yearIncome.setText(Integer.toString(numComp));
                dataSeries1.getData().add(new XYChart.Data(start.getDay(), reportsPerMonth[start.getDay()]));
                dataSeries1.getData().add(new XYChart.Data(start.getDay()+1  , reportsPerMonth[start.getDay()+1]));
                dataSeries1.getData().add(new XYChart.Data(start.getDay()+2  , reportsPerMonth[start.getDay()+2]));
                dataSeries1.getData().add(new XYChart.Data(start.getDay()+3  , reportsPerMonth[start.getDay()+3]));
                dataSeries1.getData().add(new XYChart.Data(start.getDay()+4  , reportsPerMonth[start.getDay()+4]));
                dataSeries1.getData().add(new XYChart.Data(start.getDay()+5  , reportsPerMonth[start.getDay()+5]));
                dataSeries1.getData().add(new XYChart.Data(start.getDay()+6  , reportsPerMonth[start.getDay()+6]));

            }
            if(currentTimePeriod.equals("last month")){

            }
            if(currentTimePeriod.equals("last year")){

            }






        complaintsChart.getData().add(dataSeries1);




    }
}
