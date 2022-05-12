package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Flower;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FlowerEmployee implements Initializable {

    @FXML
    private AnchorPane Item;

    @FXML
    private TextField descriptiontxt;

    @FXML
    private Button editbtn;

    @FXML
    private ImageView flowerImg;

    @FXML
    private TextField pricetxt;

    @FXML
    private Button returnbtn;

    @FXML
    private TextField txt;
    private Flower flower = new Flower();
    private String imageUrl;
    private static boolean addnew;

    public static boolean isAddnew() {
        return addnew;
    }

    public static void setAddnew(boolean addnew) {
        FlowerEmployee.addnew = addnew;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(addnew==true){
            flower=new Flower("name","type",0);
            pricetxt.setEditable(true);
            txt.setEditable(true);
            descriptiontxt.setEditable(true);
            editbtn.setText("done");
            pricetxt.setStyle("-fx-background-color: white;");
        }
        else {
            flower = CatalogEmployee.getCurrentFlower();
            txt.setEditable(false);
            descriptiontxt.setEditable(false);
            pricetxt.setEditable(false);
        }
        txt.setText(flower.getName());
        descriptiontxt.setText(flower.getType());
        pricetxt.setText((flower.getPrice()) + "");

        //descriptiontxt.setWrapText(true);


        flowerImg.setImage(new Image(getClass().getResourceAsStream(flower.getImageurl())));
    }
    @FXML
    void edit(ActionEvent event) throws IOException {
        if(editbtn.getText().equals("Update")) {
            pricetxt.setEditable(true);
            txt.setEditable(true);
            descriptiontxt.setEditable(true);
            editbtn.setText("done");
            pricetxt.setStyle("-fx-background-color: white;");
        }
        else {
            pricetxt.setStyle("-fx-background-color: transparent;-fx-font-size:  14px;-fx-font-weight: bold;-fx-font-style: italic");
            pricetxt.setEditable(false);
            txt.setEditable(false);
            descriptiontxt.setEditable(false);
            editbtn.setText("Update");
            if(addnew==false) {

                flower.setPrice(Double.parseDouble(pricetxt.getText().toString()));
                flower.setName(txt.getText());
                flower.setType(descriptiontxt.getText());
                ArrayList<Object> arr = new ArrayList<>();
                arr.add("#updateflower");
//            arr.add(flower.getId());
//            arr.add(flower.getPrice());
//            arr.add(flower.getDiscount());
                arr.add(flower);

                App.getClient().sendToServer(arr);
            }
            if(addnew==true){
                addnew=false;
                flower.setPrice(Double.parseDouble(pricetxt.getText().toString()));
                flower.setName(txt.getText());
                flower.setType(descriptiontxt.getText());
                ArrayList<Object> arr = new ArrayList<>();
                arr.add("#addflower");
                arr.add(flower);
                App.getClient().sendToServer(arr);

            }
        }
    }

    @FXML
    void returnBtn(ActionEvent event) throws IOException {
      App.setRoot("catalogemployee");
    }


}
