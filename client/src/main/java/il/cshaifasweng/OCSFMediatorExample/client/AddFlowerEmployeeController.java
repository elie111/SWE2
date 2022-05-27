package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddFlowerEmployeeController implements Initializable {
    @FXML private Text addFlowerL;
    @FXML private Button userName;
    @FXML private Label LableName;
    @FXML private TextField textName;
    @FXML private Label labelDescription;
    @FXML private TextField textDescription;
    @FXML private Label labelType;
    @FXML private ComboBox<String> chooseType;
    @FXML private Label labelImage;
    @FXML private ComboBox<String> chooseImage;
    @FXML private Label labelColor;
    @FXML private ComboBox<String> chooseColor;
    @FXML private Label labelPrice;
    @FXML private TextField textPrice;
    @FXML private Label labelSale;
    @FXML private ComboBox<String> ChooseSale;
    @FXML private Label labelDiscount;
    @FXML private TextField textDiscount;
    @FXML private Button backBtn;
    @FXML private Button signBtn;

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

        chooseType.getItems().addAll("Flower Pot", "Bridal Bouquet", "Flower Arrangement");
        String path = "C:\\Users\\inbar lev tov\\Desktop\\software 2022\\project\\SWE2\\client\\src\\main\\resources\\il\\cshaifasweng\\OCSFMediatorExample\\client\\Images";
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for (File f: listOfFiles) {
            chooseImage.getItems().add(f.getName());
        }
        chooseColor.getItems().addAll("pink", "red", "yellow", "blue");
        ChooseSale.getItems().addAll("True", "False");

        labelDiscount.setVisible(false);
        textDiscount.setVisible(false);
        ChooseSale.getSelectionModel().selectedItemProperty().addListener((option, oldV, newV) -> {
            if(newV.equals("True")) {
                labelDiscount.setVisible(true);
                textDiscount.setVisible(true);
            }
            else {
                labelDiscount.setVisible(false);
                textDiscount.setVisible(false);
            }
        });

        signBtn.disableProperty().bind(
                Bindings.isEmpty(textName.textProperty())
                        .or(Bindings.isEmpty(textDescription.textProperty()))
                        .or(chooseType.valueProperty().isNull())
                        .or(chooseImage.valueProperty().isNull())
                        .or(chooseColor.valueProperty().isNull())
                        .or(Bindings.isEmpty(textPrice.textProperty()))
                        .or(ChooseSale.valueProperty().isNull()));
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
    public void backButton(ActionEvent event) throws IOException {
        App.setRoot("CatalogEmployee");
    }

    @FXML
    public void signButton(ActionEvent event) throws IOException {

    }
}
