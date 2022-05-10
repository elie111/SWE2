package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class fileCompleintController {
    @FXML
    private Label complaintLable;

    @FXML
    private TextField complaintTF;

    @FXML
    private Button sendBtn;

    @FXML
    private Button cancleBtn;

    @FXML
    private Label orderNumberLable;

    @FXML
    private TextField orderNumberTF;

    @FXML
    private Label orderNumEmpty;

    @FXML
    private Label invalidInputOrNum;

    @FXML
    private Label compEmpty;


//    Complaint complaint = new Complaint(); //create complaint in db

    @FXML
    public void onCLickCancel(ActionEvent event) throws IOException {
        App.setRoot("catalogBoundary");
    }

    //check that order number is included only numbers
    private boolean isValidOrderNum (TextField fieldA){
        for (int i = 0; i<fieldA.getText().length() ; i++){
            char temp = fieldA.getText().charAt(i);
            if(!((temp >= '0') && (temp <= '9'))){
                return false;
            }
        }
        return true;
    }

    //check that relevant fields are not empty
    private boolean inputCheck (){
        boolean retVal = true;

        if (complaintTF.getText().isEmpty())
        {
            compEmpty.setVisible(true);
            retVal = false;
        }
        return retVal;
    }

    //if field is empty or invalid show massage - called when sendComplaint is called
    private void restVisible() {
        orderNumEmpty.setVisible(false);
        compEmpty.setVisible(false);
        invalidInputOrNum.setVisible(false);
    }


    @FXML
    public void onCLickSend(ActionEvent event) throws IOException {
        // user put username and password that are not only spaces or null
        String orderNumber = orderNumberTF.getText();
        String content = complaintTF.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.parse(LocalDate.now().toString(), formatter);
        
        // write to database
        ArrayList<Object> arr = new ArrayList<>();
        arr.add("#complaint");
        arr.add(orderNumber); //order number
        arr.add(content); //content of complaint
        arr.add(true); //status
        arr.add(today); //the date of the complaint
        App.getClient().sendToServer(arr);
    }



}
