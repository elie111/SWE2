package il.cshaifasweng.OCSFMediatorExample.client;



import il.cshaifasweng.OCSFMediatorExample.entities.Flower;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


public class FlowerBoundary {


    @FXML
    private AnchorPane Item;

    @FXML
    private Button addbtn;

    @FXML
    private Button editbtn;

    @FXML
    private ImageView img;

    @FXML
    private Button returnbtn;

    @FXML
    private TextField txt;
    @FXML
    private TextField descriptiontxt;
    @FXML
    private TextField pricetxt;
    Flower flower=new Flower();


    // private String[] itemstr={"flower 1","flower 2","flower 3","flower 4","flower 5","flower 6"};


    @FXML
    void initialize(){
        flower=CatalogBoundary.getCurrentflower();
        txt.setText(flower.getName());
        descriptiontxt.setText(flower.getType());
        pricetxt.setText((flower.getPrice())+"$");
        txt.setEditable(false);
        descriptiontxt.setEditable(false);
        pricetxt.setEditable(false);
        // txt.setText(itemstr[0]);
        // img=new ImageView(new Image("target/image1.png"));

    }
    @FXML
    void addBtn(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Do you want to this Item to your cart?");
        alert.setContentText("");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }
    @FXML
    void edit(ActionEvent event) throws IOException {
        if(editbtn.getText().equals("edit")) {
            txt.setEditable(true);
            descriptiontxt.setEditable(true);
            pricetxt.setEditable(true);
            editbtn.setText("done");

        }
        else{


            txt.setEditable(false);
            descriptiontxt.setEditable(false);
            pricetxt.setEditable(false);
            editbtn.setText("edit");
            flower.setPrice(Integer.parseInt(pricetxt.getText().toString()));
            ArrayList<Object> arr=new ArrayList<>();
            arr.add("#updateflower");
            arr.add(flower.getId());
            arr.add(flower.getPrice());
            arr.add( flower.getSale());
            App.getClient().sendToServer(arr);

        }
    }
    @FXML
    void returnbtn(ActionEvent event) throws IOException {
        try {

            App.setRoot("catalogboundary");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
