package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.User;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {
    @FXML Label LFname;
    @FXML Label Lid;
    @FXML Label Lemail;
    @FXML Label Lphone;
    @FXML Label Lcredit;
    @FXML Label Lvalid;
    @FXML Label Lcvv;
    @FXML Label Lpassword;

    @FXML TextField TFname;
    @FXML TextField Tid;
    @FXML TextField Temail;
    @FXML TextField Tphone;
    @FXML TextField Tcredit;
    @FXML ComboBox<String> month;
    @FXML ComboBox<String> year;
    @FXML TextField Tcvv;
    @FXML TextField Tpassword;

    @FXML Button backBtn;
    @FXML Button signBtn;

    @FXML Text Registration;

    @FXML Text Lsubsctiption;
    @FXML ComboBox<String> Cmembership;
    @FXML Text Lstore;
    @FXML ComboBox<String> Cstore;

    @FXML TextField error;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Lstore.setVisible(false);
        Cstore.setVisible(false);
        error.setText("please fill all fields correctly");
        error.setStyle("-fx-font-weight: bold");
        error.setVisible(false);
        signBtn.disableProperty().bind(
                Bindings.isEmpty(TFname.textProperty())
                        .or(Bindings.isEmpty(Tid.textProperty()))
                        .or(Bindings.isEmpty(Temail.textProperty()))
                        .or(Bindings.isEmpty(Tphone.textProperty()))
                        .or(Bindings.isEmpty(Tcredit.textProperty()))
                        .or(Bindings.isEmpty(Tcvv.textProperty()))
                        .or(Bindings.isEmpty(Tpassword.textProperty()))
                        .or(month.valueProperty().isNull())
                        .or(year.valueProperty().isNull())
                        .or(Cmembership.valueProperty().isNull()));

        month.getItems().addAll("", "01", "02", "03", "04", "05", "06",
                                "07", "08", "09", "10", "11", "12");
        year.getItems().addAll("", "22", "23", "24", "25", "26", "27",
                                "28", "29", "30", "31", "32", "33");
        Cmembership.getItems().addAll("", "Store Account", "Chain Account", "Yearly Chain Account");
        // in the future, take shop list from database
        Cstore.getItems().addAll("", "Haifa", "Tel Aviv", "New york", "Eilat", "London");

        Cmembership.getSelectionModel().selectedItemProperty().addListener((option, oldV, newV) -> {
            if(newV.equals("Store Account")){
                Lstore.setVisible(true);
                Cstore.setVisible(true);
            }
            else{
                Lstore.setVisible(false);
                Cstore.setVisible(false);
            }
        });
    }

    public void backButton(ActionEvent event) throws IOException {
        App.setRoot("catalogboundary");
    }

    public void signButton(ActionEvent event) throws IOException {
        String firstName = TFname.getText();
        String ID = Tid.getText();
        String email = Temail.getText();
        String phoneN = Tphone.getText();
        String creditCard = Tcredit.getText();
        String cvv = Tcvv.getText();
        String password = Tpassword.getText();
        String vMonth = month.getSelectionModel().getSelectedItem();
        String vYear = year.getSelectionModel().getSelectedItem();
        String account = Cmembership.getSelectionModel().getSelectedItem();
        String store = Cstore.getSelectionModel().getSelectedItem();
        if(account.equals("Store Account")){
            if(store == null){
                error.setVisible(true);
                store = Cstore.getSelectionModel().getSelectedItem();
            }
        }
        else{
            store = null;
        }

        // 0 = name, 1 = ID, 2 = email, 3 = phone, 4 = credit, 5 = cvv, 6 = password
        boolean[] answers = new boolean[8];
        answers[0] = checkName(firstName);
        answers[1] = checkID(ID);
        answers[2] = checkEmail(email);
        answers[3] = checkPhoneNumber(phoneN);
        answers[4] = checkCreditCard(creditCard);
        answers[5] = checkCVV(cvv);
        answers[6] = checkPassword(password);
        answers[7] = checkMonthYear(vMonth, vYear);

        int counter = 0;
        for(int i = 0; i < 8; i++){
            if(!answers[i]){
                counter++;
            }
        }
        if(counter != 0){
            error.setVisible(true);
        }
        else{
            // adding data to database
            // pay attention that store could be null if it's not store account
            addNewUser(firstName, ID, email, phoneN, creditCard, vMonth, vYear, cvv, password, account, store);
            error.setVisible(false);
        }
    }

    public boolean checkName(String name){
        if(name == null){
            return false;
        }
        if(name.trim().isEmpty()){
            return false;
        }

        int length = name.length();
        char ch = ' ';
        for(int i = 0; i < length; i++){
            ch = name.charAt(i);
            if (Character.isLetter(ch) || ch == ' ') {
                continue;
            }
            else{
                return false;
            }
        }

        return true;
    }

    public boolean checkID(String ID){
        if(ID == null){
            return false;
        }

        int length = ID.length();
        int c = 0;
        if(length != 9){
            return false;
        }
        else{
            for(int i = 0; i < length; i++){
                c = ID.charAt(i) - 48;
                if(c < 0 || c > 9){
                    return false;
                }
            }
        }

        return true;
    }

    public boolean checkEmail(String Email){
        if(Email == null){
            return false;
        }

        char a = '@';
        String begin = "";
        String end = "";

        int index = Email.indexOf(a);
        if(index == -1){
            return false;
        }
        else{
            begin = Email.substring(0, index);
            if(begin  == null || begin.trim().isEmpty()){
                return false;
            }
            end = Email.substring(index + 1, Email.length());
            if(end == null){
                return false;
            }
        }

        if(!end.equals("gmail.com")){
            return false;
        }

        for(int i = 0; i < begin.length(); i++){
            a = begin.charAt(i);
            if (Character.isLetter(a) || (a >= '0' && a <= '9')) {
                continue;
            }
            else{
                return false;
            }
        }

        return true;
    }

    public boolean checkPhoneNumber(String phoneNumber){
        if(phoneNumber == null){
            return false;
        }

        int length = phoneNumber.length();
        int c = 0;
        if(length != 10){
            return false;
        }
        else{
            for(int i = 0; i < length; i++){
                c = phoneNumber.charAt(i) - 48;
                if(c < 0 || c > 9){
                    return false;
                }
            }
        }

        return true;
    }

    public boolean checkCreditCard(String creditCard){
        if(creditCard == null){
            return false;
        }

        int length = creditCard.length();
        int c = 0;
        if(length != 16){
            return false;
        }
        else{
            for(int i = 0; i < length; i++){
                c = creditCard.charAt(i) - 48;
                if(c < 0 || c > 9){
                    return false;
                }
            }
        }

        return true;
    }

    public boolean checkCVV(String cvv){
        if(cvv == null){
            return false;
        }

        int length = cvv.length();
        int c = 0;
        if(length != 3){
            return false;
        }
        else{
            for(int i = 0; i < length; i++){
                c = cvv.charAt(i) - 48;
                if(c < 0 || c > 9){
                    return false;
                }
            }
        }

        return true;
    }

    public boolean checkPassword(String password){
        if(password == null){
            return false;
        }

        int length = password.length();
        char a = ' ';
        if(length < 8){
            return false;
        }
        else{
            for(int i = 0; i < length; i++){
                a = password.charAt(i);
                if (Character.isLetter(a) || (a >= '0' && a <= '9')) {
                    continue;
                }
                else{
                    return false;
                }
            }
        }

        return true;
    }

    public boolean checkMonthYear(String month, String year){
        String monthAndYear = "20" + year + "-" + month + "-" + "01";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.parse(LocalDate.now().toString(), formatter);
        LocalDate myDate = LocalDate.parse(monthAndYear, formatter);

//        System.out.println(today);
//        System.out.println(myDate);

        if(myDate.isBefore(today)) {
            return false;
        }
        else {
            return true;
        }
    }

    public void addNewUser(String name, String id, String email, String phone, String credit, String month,
                           String year, String cvv, String password, String account, String storeOrNull) throws IOException {
        String monthAndYear = month + "/" + year;
        ArrayList<Object> arr = new ArrayList<>();
        arr.add("#register");
        arr.add(name);
        arr.add(id);
        arr.add(email);
        arr.add(phone);
        arr.add(credit);
        arr.add(monthAndYear);
        arr.add(cvv);
        arr.add(password);
        arr.add(account);
        if(storeOrNull == null){
            arr.add("");
        }
        else{
            arr.add(storeOrNull);
        }

        App.getClient().sendToServer(arr);
    }
}
