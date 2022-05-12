package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Flower;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CatalogEmployee implements Initializable {

    @FXML
    private Button addbtn;

    @FXML
    private CheckBox all;

    @FXML
    private CheckBox blue;

    @FXML
    private ListView<String> myListView;

    @FXML
    private CheckBox pink;

    @FXML
    private Slider pricerange;

    @FXML
    private Label pricetext;

    @FXML
    private CheckBox red;

    @FXML
    private Button removebtn;

    @FXML
    private Button resetbtn;

    @FXML
    private Button searchbtn;

    @FXML
    private Button updatebtn;

    @FXML
    private VBox vb;

    @FXML
    private CheckBox yellow;

    private static HashMap<String,Integer> colorsmap=new HashMap<>();


    private static Label placeholdercart=new Label("add flowers to your cart");
    private static Label placeholderlist=new Label("no flowers in catalog");
    private int pricelimit = 1000;
    private int toplimit = 1000;

    private String colorfilter;
    private static ArrayList<Flower> flowers = new ArrayList<Flower>();

    private static Flower currentflower;

    private ArrayList<String> liststr = new ArrayList<String>();

    public static void setFlowers(ArrayList<Flower> flowers) {
        CatalogEmployee.flowers = flowers;
    }

    public static Flower getCurrentFlower() {
        return currentflower;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Object> arr = new ArrayList<>();
        arr.add("#getcatalog");
        try {
            App.getClient().sendToServer(arr);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        vb = new VBox();
        vb.setPadding(new Insets(20));
        vb.setSpacing(10);

        getCatalogItems();

        pricerange.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                pricelimit = pricerange.valueProperty().intValue();
            }
        });
    }
    public void getCatalogItems() {
        for(int i = 0; i < flowers.size(); i++ ) {
            if(flowers.get(i).getPrice() <= pricelimit && ((colorsmap.get(flowers.get(i).getColor())==1)
                    ||colorsmap.get("all")==1)) {
                liststr.add(flowers.get(i).getName());
            }
        }

        myListView.getItems().addAll(liststr);
        //myListView.refresh();
    }


    @FXML
    void addflower(ActionEvent event) throws IOException {
        FlowerEmployee.setAddnew(true);
        App.setRoot("floweremployee");
    }

    @FXML
    void removefunc(ActionEvent event) throws IOException {
        int index=myListView.getSelectionModel().getSelectedIndex();
        if(myListView.getSelectionModel().getSelectedItem()!=null) {
            ArrayList<Object> msg = new ArrayList<>();
            msg.add("#deleteflower");
            msg.add(flowers.get(index));
            myListView.getItems().remove(index);
            liststr.remove(index);
            flowers.remove(index);
            myListView.refresh();

            App.getClient().sendToServer(msg);
        }

    }

    @FXML
    void resetFilter(ActionEvent event) {
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
    void updateFilter(ActionEvent event) {
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
    void updateflower(ActionEvent event) throws IOException {
        int index=myListView.getSelectionModel().getSelectedIndex();
        if(myListView.getSelectionModel().getSelectedItem()!=null) {
            currentflower = flowers.get(index);
            App.setRoot("floweremployee");
        }
        myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

            }
        });
    }

    @FXML
    void refresh(ActionEvent event) {
        myListView.getItems().clear();
        liststr.clear();
        getCatalogItems();
    }
}
