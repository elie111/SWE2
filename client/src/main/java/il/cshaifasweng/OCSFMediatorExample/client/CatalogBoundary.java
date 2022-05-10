package il.cshaifasweng.OCSFMediatorExample.client;

import com.mysql.cj.exceptions.CJCommunicationsException;
import il.cshaifasweng.OCSFMediatorExample.entities.Flower;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.hibernate.mapping.Property;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CatalogBoundary implements Initializable {
    @FXML private ListView<String> myListView;
    @FXML private ListView<String> mycart;
    @FXML private Slider pricerange;
    @FXML private VBox vb;
    @FXML private Label pricetext;
    @FXML private Button clearbtn;
    @FXML private Button orderbtn;
    @FXML private Button searchbtn;
    @FXML private Button resetbtn;

    @FXML private  CheckBox all;
    @FXML private  CheckBox red;
    @FXML private  CheckBox pink;
    @FXML private  CheckBox blue;
    @FXML private  CheckBox yellow;

    private static HashMap<String,Integer> colorsmap=new HashMap<>();

    @FXML private  Label price;
    private static String pricetxt;
    private static Label placeholdercart=new Label("add flowers to your cart");
    private static Label placeholderlist=new Label("no flowers in catalog");
    private int pricelimit = 1000;
    private int toplimit = 1000;
    int p;
    private String colorfilter;

    private static String currentString;
    private static ArrayList<Flower> flowers = new ArrayList<Flower>();
   // private static ArrayList<Flower> cartlst = new ArrayList<Flower>();
    private static HashMap<Flower,Integer> cartmap=new HashMap<>();
    private static Flower currentflower;

    private ArrayList<String> liststr = new ArrayList<String>();
    private ArrayList<String> cartnames = new ArrayList<String>();

    public static ArrayList<Flower> getFlowers() {
        return flowers;
    }

    public static void setFlowers(ArrayList<Flower> flowers) {
        CatalogBoundary.flowers = flowers;
    }

    public static Flower getCurrentFlower() {
        return currentflower;
    }

    public static String getCurrentString() {
        return currentString;
    }

    public  static HashMap<Flower,Integer>  getMap()
    {
        return cartmap;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pricerange.setMin(0);
        pricerange.setMax(350);
        pricerange.setValue(350);
        pricerange.setBlockIncrement(10);
        pricerange.setShowTickMarks(false);
        pricerange.setShowTickLabels(true);
        all.setSelected(true);
        colorsmap.put("all",1);
        colorsmap.put("red",0);
        colorsmap.put("pink",0);
        colorsmap.put("blue",0);
        colorsmap.put("yellow",0);

        placeholdercart.setStyle("-fx-text-fill: linear-gradient(#ff5400, #be1d00);-fx-font-size:  14px;-fx-font-weight: bold;-fx-font-style: italic");
        placeholderlist.setStyle("-fx-text-fill: linear-gradient(#ff5400, #be1d00);-fx-font-size:  14px;-fx-font-weight: bold;-fx-font-style: italic");
        myListView.setPlaceholder(placeholderlist);
        mycart.setPlaceholder(placeholdercart);

        vb = new VBox();
        vb.setPadding(new Insets(20));
        vb.setSpacing(10);

        getCatalogItems();
        getCartItems();
        p = 0;

//        for (int i = 0; i < cartlst.size(); i++) {
//            p += cartlst.get(i).getPrice();
//        }
        for ( Flower key : cartmap.keySet() ) {
            p+=key.getPrice()*cartmap.get(key);
        }

        pricetxt = "final price: " + p + " $";
        price.setText(pricetxt);
        pricerange.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                pricelimit = pricerange.valueProperty().intValue();
            }
        });
    }

    public void getCartItems() {
//        for(int i = 0; i < cartlst.size(); i++) {
//            cartnames.add(cartlst.get(i).getName());
//        }
        for ( Flower key : cartmap.keySet() ) {
            cartnames.add(key.getName()+" x"+cartmap.get(key));
        }
        mycart.getItems().addAll(cartnames);
    }

    public static void addToCart(Flower flower) {
       // cartlst.add(flower);
        int counter;
        if(cartmap.get(flower)==null)
        cartmap.put(flower,1);
        else{
            counter=cartmap.get(flower)+1;
            cartmap.put(flower,counter);
        }
    }

    public void getCatalogItems() {
        for(int i = 0; i < flowers.size(); i++ ) {
            if(flowers.get(i).getPrice() <= pricelimit && ((colorsmap.get(flowers.get(i).getColor())==1)
                    ||colorsmap.get("all")==1)) {
                liststr.add(flowers.get(i).getName());
            }
        }

        myListView.getItems().addAll(liststr);
        myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                int currentid = myListView.getSelectionModel().getSelectedIndex();
                for(int i = 0; i < 10; i++) {
                    if(flowers.get(i).getId() == (currentid + 1)) {
                        currentflower = flowers.get(i);
                        break;
                    }
                }
                currentString = (myListView.getSelectionModel().getSelectedItem()).toString();
                try {
                    App.setRoot("flowerboundary");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    public void clear(ActionEvent event) throws IOException {
        if(mycart.getItems().size()>0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("clear cart");
            alert.setHeaderText("are you sure you want \nto remove all items in your cart?");

            ButtonType confirmbtn = new ButtonType("remove all");
            ButtonType cancelbtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(confirmbtn, cancelbtn);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == confirmbtn) {
                cartmap.clear();
                cartnames.clear();
                mycart.getItems().clear();
                pricetxt = "final price: 0 $";
                price.setText(pricetxt);
            } else {

            }

        }
    }

    @FXML
    public void updateFilter(ActionEvent event) throws IOException {
        liststr.clear();
        myListView.getItems().clear();
        if(all.isSelected())
            colorsmap.put("all",1);
        else{
            colorsmap.put("all",0);
        }
        if(red.isSelected())
            colorsmap.put("red",1);
        else{
            colorsmap.put("red",0);
        }
        if(pink.isSelected())
            colorsmap.put("pink",1);
        else{
            colorsmap.put("pink",0);
        }
        if(blue.isSelected())
            colorsmap.put("blue",1);
        else{
            colorsmap.put("blue",0);
        }
        if(yellow.isSelected())
            colorsmap.put("yellow",1);
        else{
            colorsmap.put("yellow",0);
        }
        getCatalogItems();
    }

    @FXML
    public void resetFilter(ActionEvent event) throws IOException {
        pricelimit = 350;
        pricerange.setValue(350);
        liststr.clear();
        myListView.getItems().clear();
        colorsmap.put("all",1);
        colorsmap.put("red",0);
        colorsmap.put("pink",0);
        colorsmap.put("yellow",0);
        colorsmap.put("blue",0);
        all.setSelected(true);
        red.setSelected(false);
        pink.setSelected(false);
        blue.setSelected(false);
        yellow.setSelected(false);

        getCatalogItems();
    }

    @FXML
    public void goToOrder(ActionEvent event) throws IOException {
        App.setRoot("Order");
    }
    @FXML
    public void removefunc(ActionEvent event) throws IOException {
        String item=mycart.getSelectionModel().getSelectedItem();
        String item2;
        //mycart.getItems().remove(item);

        for ( Flower key : cartmap.keySet() ) {
            if(item!=null&&item.startsWith(key.getName())==true){
                if(cartmap.get(key)>1){

                    cartnames.remove(key.getName());

                    item2=key.getName()+" x"+String.valueOf(cartmap.get(key)-1);
                    cartnames.add(item2);


                    cartmap.put(key,cartmap.get(key)-1);

                    mycart.getItems().remove(item);
                    mycart.getItems().add(item2);


                 //   Collections.sort(mycart.getItems());
                    mycart.refresh();
                }
                else{

                    cartnames.remove(key.getName());
                    mycart.getItems().remove(item);
                    cartmap.remove(key);
                    mycart.refresh();
                }
                break;
            }
        }
        p=0;
        for ( Flower key : cartmap.keySet() ) {
            p +=key.getPrice()*cartmap.get(key);
        }

        pricetxt = "final price: " + p + " $";
        price.setText(pricetxt);

        mycart.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {


            }
        });

    }



}