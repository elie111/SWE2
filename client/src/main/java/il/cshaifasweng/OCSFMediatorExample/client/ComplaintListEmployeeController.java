//package il.cshaifasweng.OCSFMediatorExample.client;
//
//import il.cshaifasweng.OCSFMediatorExample.entities.Complaint;
//import il.cshaifasweng.OCSFMediatorExample.entities.Order;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Button;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.text.Text;
//
//import java.awt.event.MouseEvent;
//import java.io.IOException;
//import java.lang.reflect.Array;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.ResourceBundle;
//
//public class ComplaintListEmployeeController implements Initializable {
//
//    @FXML private TableColumn<?, ?> nameCol;
//
//    @FXML private TableView<Complaint> complaintTable;
//
//    @FXML private Text ordersText;
//
//    @FXML private TableColumn<?, ?> dateCol;
//
//    @FXML private Button returnBtn;
//
//    @FXML private TableColumn<?, ?> contentCol;
//    private static ArrayList<Complaint> complaints=new ArrayList<>();
//    public static void setOrders(ArrayList<Complaint> complaints){
//        ComplaintListEmployeeController.complaints=complaints;
//    }
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        complaints.clear();
//        complaintTable.getItems().clear();
//        complaintTable.getItems().addAll(complaints);
//        Complaint complaint=new Complaint();
//       // complaint.setOrderId("2");
//        complaint.setContent("blah");
////        complaint.setDate("1/1/1");
//        complaints.add(complaint);
//        complaintTable.getItems().addAll(complaints);
//
//        nameCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
//        contentCol.setCellValueFactory(new PropertyValueFactory<>("content"));
//        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
//    }
//    @FXML
//    void returnFunc(ActionEvent event) throws IOException {
//        App.setRoot("CatalogBoundary");
//    }
//    @FXML
//    public void clickItem(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
//        if (mouseEvent.getClickCount() == 2) //Checking double click
//        {
//            ComplaintController.setCurrentComplaint(complaints.get(complaintTable.getSelectionModel().getSelectedIndex()));
//            App.setRoot("ComplaintBoundary");
//        }
//    }
//}
