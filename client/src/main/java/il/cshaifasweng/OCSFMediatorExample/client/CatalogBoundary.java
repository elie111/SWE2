package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Flower;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CatalogBoundary implements Initializable {
    @FXML private ListView<String> myListView;
    private static String currentString;
    private static ArrayList<Flower> flowers = new ArrayList<Flower>();
    private static Flower currentflower;

    private ArrayList<String> liststr = new ArrayList<String>();

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
        for(int i = 0; i < flowers.size(); i++) {
            liststr.add(flowers.get(i).getName());
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
}
