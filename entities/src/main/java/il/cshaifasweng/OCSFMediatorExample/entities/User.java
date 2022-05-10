package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;

@Entity
@Table(name = "user")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String account;
    private String credit;
    private String cvv;
    private String email;
    private String identificationNumber;
    private String monthAndYear;
    private String password;
    private String phone;
    private String storeOrNull;
    private String userName;

    public User(String name, String identificationNumber, String email, String phone, String credit,
                String monthAndYear, String cvv, String password, String account, String storeOrNull) {
        this.userName = name;
        this.identificationNumber = identificationNumber;
        this.email = email;
        this.phone = phone;
        this.credit = credit;
        this.monthAndYear = monthAndYear;
        this.cvv = cvv;
        this.password = password;
        this.account = account;
        this.storeOrNull = storeOrNull;
    }

    public User() {}

    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public String getId() {
        return identificationNumber;
    }

    public void setId(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getMonthAndYear() {
        return monthAndYear;
    }

    public void setMonthAndYear(String monthAndYear) {
        this.monthAndYear = monthAndYear;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getStoreOrNull() {
        return storeOrNull;
    }

    public void setStoreOrNull(String storeOrNull) {
        this.storeOrNull = storeOrNull;
    }

    public int getID() {
        return id;
    }
}
