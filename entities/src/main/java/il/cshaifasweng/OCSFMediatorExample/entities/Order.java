package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")

public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String address;
    private String card;
    private String credit;
    private String cvv;
    private LocalDateTime dateTime;
    private double finalPrice;
    private String formOfSupplying;
    private String monthAndYear;
    private String receiverName;
    private String receiverPhone;
    private String storeName;
    private int userID;

    @ManyToMany
    private List<Flower> flowers;

    public Order(int userID, List<Flower> flowers, String card, String formOfSupplying,
                 String storeName, String address, String receiverName, String receiverPhone,
                 LocalDateTime dateTime, double finalPrice, String credit, String cvv,
                 String monthAndYear) {
        super();
        this.address = address;
        this.card = card;
        this.credit = credit;
        this.cvv = cvv;
        this.dateTime = dateTime;
        this.finalPrice = finalPrice;
        this.formOfSupplying = formOfSupplying;
        this.monthAndYear = monthAndYear;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.storeName = storeName;
        this.userID = userID;
        this.flowers = new ArrayList<>(flowers);
    }

    public Order() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getFormOfSupplying() {
        return formOfSupplying;
    }

    public void setFormOfSupplying(String formOfSupplying) {
        this.formOfSupplying = formOfSupplying;
    }

    public String getMonthAndYear() {
        return monthAndYear;
    }

    public void setMonthAndYear(String monthAndYear) {
        this.monthAndYear = monthAndYear;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public List<Flower> getFlowers() {
        return flowers;
    }

    public void setFlowers(List<Flower> flowers) {
        this.flowers = flowers;
    }
}
