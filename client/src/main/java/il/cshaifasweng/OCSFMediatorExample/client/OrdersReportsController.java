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
import javafx.scene.control.*;
import javafx.scene.text.Text;

import javax.print.attribute.HashAttributeSet;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
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

    @FXML private Label numOfFlowers;

    @FXML
    private Label yearIncome;
    private static ArrayList<Order> orderslst=new ArrayList<>();
    private static ArrayList<Flower> flowerlst=new ArrayList<>();
    private int numOrders=0;
    private int numFlowers=0;
    private HashMap<String,Integer> flowerMap=new HashMap<>();
    private static SimpleClient client;


    public static void setOrders(ArrayList<Order> orders) {
        OrdersReportsController.orderslst=orders;
    }

    public static void setFlowers(ArrayList<Flower> flowers) {
        OrdersReportsController.flowerlst=flowers;
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
//        App.setRoot("ManageChainController"); // if the manager is a chain manager
        //App.getClient().sendToServer();

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
        numOrders=0;
        numFlowers=0;
        flowerMap.clear();
        ordersChart.getData().clear();
        ordersChart.layout();
        int pot=0;
        int arrang=0;
        int boq=0;
        for(int i=0;i<orderslst.size();i++) {
            Date date1 = orderslst.get(i).getCurrentDate();
            if ((orderslst.get(i).getStoreName().equals(EntityHolder.getStoreM().getStoreName()))&&
                    date1.after(Date.from((startDate.getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                    && date1.before(Date.from(endDate.getValue().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
                   String names=orderslst.get(i).getFlowers();
                   String [] flowerNames=names.split("\n");

                   for (int k=0;k<flowerNames.length;k++){
                       if(flowerMap.get(flowerNames[k])==null){
                           flowerMap.put(flowerNames[k],1);
                       }
                       else{
                           int count=flowerMap.get(flowerNames[k])+1;
                           flowerMap.put(flowerNames[k],count);
                       }
                   }



                numOrders += 1;
            }
        }
        for (String key : flowerMap.keySet()) {
            for(int k=0;k<flowerlst.size();k++){
                if(flowerlst.get(k).getName().equals(key)){
                    if(flowerlst.get(k).getType().equals("Flower Pot")){
                        pot+=flowerMap.get(key);
                    }
                    if(flowerlst.get(k).getType().equals("Flower Arrangement")){
                        arrang+=flowerMap.get(key);
                    }
                    if(flowerlst.get(k).getType().equals("Bridal Bouquet")){
                        boq+=flowerMap.get(key);
                    }
                    numFlowers+=flowerMap.get(key);
                    break;
                }
            }
        }
            PieChart.Data slice1 = new PieChart.Data("Flower Pot", pot);
            PieChart.Data slice2 = new PieChart.Data("Flower Arrangement"  , arrang);
            PieChart.Data slice3 = new PieChart.Data("Bridal Bouquet" , boq);

            ordersChart.getData().add(slice1);
            ordersChart.getData().add(slice2);
            ordersChart.getData().add(slice3);



        yearIncome.setText(Integer.toString(numOrders));
        numOfFlowers.setText(Integer.toString(numFlowers));



    }
    @FXML
    void generateFunc(ActionEvent event) throws ParseException {
        initchart();
    }
}
