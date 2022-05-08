package il.cshaifasweng.OCSFMediatorExample.client;


import il.cshaifasweng.OCSFMediatorExample.entities.Flower;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CatalogBoundary implements Initializable  {

    @FXML
    private ListView<String> myListView;
    @FXML
    private ListView<String> mycart;
    @FXML
    private Slider pricerange;
    @FXML
    private VBox vb;
    @FXML
    private Label pricetext;
    @FXML
    private Button clearbtn;
    @FXML
    private Button orderbtn;
    @FXML
    private Button searchbtn;
    @FXML
    private Button resetbtn;

    @FXML
    private CheckBox all;
    @FXML
    private CheckBox red;
    @FXML
    private CheckBox pink;
    @FXML
    private CheckBox blue;
    @FXML
    private CheckBox yellow;

    @FXML
    private  Label price;
    private static String pricetxt;


    private int pricelimit=1000;
    private int toplimit=1000;
    private String colorfilter;




    private static String currentString;
    private static ArrayList<Flower> flowers=new ArrayList<Flower>();
    private static ArrayList<Flower> cartlst=new ArrayList<Flower>();
    private static Flower currentflower;


    private  ArrayList<String> liststr=new ArrayList<String>();
    private  ArrayList<String> cartnames=new ArrayList<String>();

    public static ArrayList<Flower> getFlowers() {
        return flowers;
    }

    public static void setFlowers(ArrayList<Flower> flowers) {
        CatalogBoundary.flowers = flowers;

    }


    public static Flower getCurrentflower() {
        return currentflower;
    }

    public static String getCurrentString() {
        return currentString;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //pricerange = new Slider(0, 100, 0);
        pricerange.setMin(0);
        pricerange.setMax(350);
        pricerange.setValue(350);
        pricerange.setBlockIncrement(10);
        pricerange.setShowTickMarks(false);
        pricerange.setShowTickLabels(true);
        vb = new VBox();
        vb.setPadding(new Insets(20));
        vb.setSpacing(10);
        getcatalogitems();
        getcartitems();
        int p=0;
        for(int i=0;i<cartlst.size();i++){
            p+=cartlst.get(i).getPrice();
        }
        pricetxt="final price: "+p+" $";
        price.setText(pricetxt);

        pricerange.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                pricelimit=pricerange.valueProperty().intValue();

            }
        });

    }

    public void getcartitems(){
        for(int i=0;i<cartlst.size();i++) {


            cartnames.add(cartlst.get(i).getName());

        }
        mycart.getItems().addAll(cartnames);


    }
    public static void addtocart(Flower flower){
        cartlst.add(flower);

//        cartnames.add(flower.getName());


    }
    public void getcatalogitems(){

        for(int i=0;i<flowers.size();i++) {
         //   System.out.println(pricelimit);
         if(flowers.get(i).getPrice()<=pricelimit) {

             liststr.add(flowers.get(i).getName());

         }

        }


        myListView.getItems().addAll(liststr);
        myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                int currentid=myListView.getSelectionModel().getSelectedIndex();
                for(int i=0;i<10;i++){
                    if(flowers.get(i).getId()==(currentid+1)) {
                        currentflower = flowers.get(i);
                        break;
                    }
                }
                currentString=(myListView.getSelectionModel().getSelectedItem()).toString();
                try {

                    App.setRoot("flowerboundary");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @FXML
    void clear(ActionEvent event) throws IOException {
        cartlst.clear();
        cartnames.clear();
        mycart.getItems().clear();
        pricetxt="final price: 0 $";
        price.setText(pricetxt);

    }
    @FXML
    void updatefilter(ActionEvent event) throws IOException {
        liststr.clear();
        myListView.getItems().clear();
        getcatalogitems();

    }
    @FXML
    void resetfilter(ActionEvent event) throws IOException {
        pricelimit=350;
        pricerange.setValue(350);
        liststr.clear();
        myListView.getItems().clear();
        getcatalogitems();

    }


}
