package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Order;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class IncomeReportsChainController implements Initializable {

    @FXML private DatePicker endDate1;

    @FXML private DatePicker endDate2;

    @FXML private Button generateBtn;

    @FXML private Text incomeTxt;

    @FXML private Label priceLabel;

    @FXML private Button returnBtn;

    @FXML private DatePicker startDate1;

    @FXML private DatePicker startDate2;

    @FXML private ComboBox<String> storeCB;

    @FXML private Label storeLabel1;

    @FXML private Label storeLabel2;

    @FXML private Button userNameBtn;

    @FXML private Label yearIncome1;
    @FXML private Label yearIncome2;

    private static ArrayList<Order> orderslst=new ArrayList<>();
    private int finalprice1=0;
    private int finalprice2=0;
    private String currentStore="all";

    private String stores[] =
            { "all","Haifa", "Tel Aviv", "Eilat",
                    "London", "New York" };

    public static void setOrders(ArrayList<Order> orders) {
        IncomeReportsChainController.orderslst=orders;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate s = LocalDate.now();
        endDate1.setValue(s);
        startDate1.setValue(s.minusDays(7));
        endDate2.setValue(s);
        startDate2.setValue(s.minusDays(7));

        storeCB.setItems(FXCollections.observableArrayList(stores));
        EventHandler<ActionEvent> event =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e)
                    {
                        currentStore=storeCB.getSelectionModel().getSelectedItem();
                        try {
                            initchart();
                        } catch (ParseException parseException) {
                            parseException.printStackTrace();
                        }
                    }
                };


        storeCB.getSelectionModel().selectFirst();
        storeCB.setOnAction(event);

        try {
            initchart();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void initchart() throws ParseException {
        finalprice1=0;
        finalprice2=0;
        for(int i=0;i<orderslst.size();i++){
            Date date1=orderslst.get(i).getCurrentDate();

            if((orderslst.get(i).getStoreName().equals(currentStore)||currentStore.equals("all"))&&
                    date1.after(Date.from((startDate1.getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                    &&date1.before(Date.from(endDate1.getValue().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
                finalprice1+=orderslst.get(i).getFinalPrice();
            }
        }
        for(int i=0;i<orderslst.size();i++){
            Date date1=orderslst.get(i).getCurrentDate();

            if((orderslst.get(i).getStoreName().equals(currentStore)||currentStore.equals("all"))&&
                    date1.after(Date.from((startDate2.getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                    &&date1.before(Date.from(endDate2.getValue().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
                finalprice1+=orderslst.get(i).getFinalPrice();
            }
        }
        yearIncome1.setText(Integer.toString(finalprice1)+" $");
        yearIncome2.setText(Integer.toString(finalprice2)+" $");
    }
    @FXML
    void generateFunc(ActionEvent event) throws ParseException {
           initchart();
    }

    @FXML
    void getDetails(ActionEvent event) {

    }

    @FXML
    void returnFunc(ActionEvent event) throws IOException {
    App.setRoot("ManageChain");
    }

}
