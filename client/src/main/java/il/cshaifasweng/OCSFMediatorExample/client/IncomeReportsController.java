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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
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
    @FXML private BarChart<?, ?> incomeChart;


    private static ArrayList<Order> orderslst=new ArrayList<>();
    private String currentYear="2022";
    private String currentStore="all";
    private int finalprice=0;
    private static SimpleClient client;


    public static void setOrders(ArrayList<Order> orders) {
        IncomeReportsController.orderslst=orders;
    }



    @FXML
    void returnFunc(ActionEvent event) throws IOException {
        App.setRoot("ManageStore");
//        App.setRoot("ManageChainController"); // if the manager is a chain manager

    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        client = SimpleClient.getClient();
        ArrayList<Object> arr2 = new ArrayList<>();
        arr2.add("#getorders");
        try {
            client.sendToServer(arr2);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        LocalDate s = LocalDate.now();
        end.setValue(s);
        start.setValue(s.minusDays(6));
        try {
            initchart();
        } catch (ParseException e) {
            e.printStackTrace();
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
    public void confirm(ActionEvent actionEvent) throws ParseException {
        initchart();
    }

    public void initchart() throws ParseException {
       int numComp=0;
        incomeChart.setAnimated(false);
        incomeChart.getData().clear();
        incomeChart.layout();
        long dur= Math.abs(Duration.between(start.getValue().atStartOfDay(),end.getValue().atStartOfDay()).toDays());
        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("Income");

        int [] reportsPerMonth=new int[(int)dur+1];
        for(int i=0;i<orderslst.size();i++) {
            Date date1 = orderslst.get(i).getCurrentDate();
            if((orderslst.get(i).getStoreName().equals(EntityHolder.getStoreM().getStoreName()))&&
                    date1.after(Date.from((start.getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                    &&date1.before(Date.from(end.getValue().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
                long index=Math.abs(Duration.between(date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay(), start.getValue().atStartOfDay()).toDays());;
                reportsPerMonth[(int)index]+=orderslst.get(i).getFinalPrice();
                numComp+=orderslst.get(i).getFinalPrice();
            }


        }
        for(int i=0;i<reportsPerMonth.length;i++){

            DateFormat dateFormat = new SimpleDateFormat("MMM-dd");
            Date date2=Date.from((start.getValue()).plusDays(i).atStartOfDay(ZoneId.systemDefault()).toInstant());
            String strDate = dateFormat.format(date2);
            dataSeries1.getData().add(new XYChart.Data(strDate, reportsPerMonth[i]));
        }
        finalres.setText(Integer.toString(numComp));

        incomeChart.getData().add(dataSeries1);




    }
}