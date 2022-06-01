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
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class ComplaintsReportsController implements Initializable {

    @FXML private BarChart<?, ?> complaintsChart;

    @FXML private DatePicker endDate;

    @FXML private Button generateBtn;

    @FXML private NumberAxis incomeAxis;

    @FXML private Text incomeTxt;

    @FXML private CategoryAxis monthAxis;

    @FXML private Label priceLabel;

    @FXML private Button returnBtn;

    @FXML private DatePicker startDate;

    @FXML private Button userNameBtn;

    @FXML private Label yearIncome;

    private static ArrayList<Complaint> compList=new ArrayList<>();
    private int numComp=0;
    private static SimpleClient client;

    public static void setComplaints(ArrayList<Complaint> complaints) {
        ComplaintsReportsController.compList=complaints;
    }

    @FXML
    public void getDetails(ActionEvent event) throws IOException {
        if(userNameBtn.getText().equals("Register / Login")) {
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
    void returnFunc(ActionEvent event) throws IOException {
        App.setRoot("ManageStore");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        client = SimpleClient.getClient();
        ArrayList<Object> arr3 = new ArrayList<>();
        arr3.add("#getcomplaints");
        try {
            client.sendToServer(arr3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(EntityHolder.getTable() == -1) {
            userNameBtn.setText("Register / Login");
        }
        else {
            if(EntityHolder.getTable() == 0) {
                userNameBtn.setText(EntityHolder.getUser().getName());
            }
            else if(EntityHolder.getTable() == 1) {
                userNameBtn.setText(EntityHolder.getEmployee().getName());
            }
            else if(EntityHolder.getTable() == 2) {
                userNameBtn.setText(EntityHolder.getStoreM().getName());
            }
            else if(EntityHolder.getTable() == 3) {
                userNameBtn.setText(EntityHolder.getChainM().getName());
            }
        }
        LocalDate s = LocalDate.now();
        endDate.setValue(s);
        startDate.setValue(s.minusDays(6));
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
        long dur= Math.abs(Duration.between(startDate.getValue().atStartOfDay(),endDate.getValue().atStartOfDay()).toDays());
        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("Complaints");

                 int [] reportsPerMonth=new int[(int)dur+1];
                 for(int i=0;i<compList.size();i++) {
                     String sDate1 = compList.get(i).getDateTime();
                     Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);

                     if(date1.after(Date.from((startDate.getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                             &&date1.before(Date.from(endDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
                         long index=Math.abs(Duration.between(date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay(), startDate.getValue().atStartOfDay()).toDays());;
                         reportsPerMonth[(int)index]+=1;
                         numComp+=1;
                     }


                 }
                 for(int i=0;i<reportsPerMonth.length;i++){

                     DateFormat dateFormat = new SimpleDateFormat("MMM-dd");
                     Date date2=Date.from((startDate.getValue()).plusDays(i).atStartOfDay(ZoneId.systemDefault()).toInstant());
                     String strDate = dateFormat.format(date2);
                     dataSeries1.getData().add(new XYChart.Data(strDate, reportsPerMonth[i]));
                 }
                 yearIncome.setText(Integer.toString(numComp));

        complaintsChart.getData().add(dataSeries1);




    }

    @FXML
    void generateFunc(ActionEvent event) throws ParseException {
        initchart();
    }
}
