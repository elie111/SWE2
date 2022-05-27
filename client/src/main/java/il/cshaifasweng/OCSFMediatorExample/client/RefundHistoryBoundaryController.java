package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class RefundHistoryBoundaryController implements Initializable {
    @FXML private Text refundHistoryL;
    @FXML private Button userName;

    @FXML private TableView<RefundHolder> refundTV;
    @FXML private TableColumn<RefundHolder, String> orderN;
    @FXML private TableColumn<RefundHolder, String> orderRefund;

    @FXML private Button backBtn;

    private static ObservableList<RefundHolder> list;

    public static void setMyRefunds(ObservableList<RefundHolder> orders) {
        RefundHistoryBoundaryController.list = FXCollections.observableList(orders);
    }

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

        orderN.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        orderRefund.setCellValueFactory(new PropertyValueFactory<>("refund"));
        refundTV.setItems(FXCollections.observableList(list));
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
    public void backBtn(ActionEvent event) throws IOException {
        App.setRoot("MyProfileBoundary");
    }
}
