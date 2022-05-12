package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")

public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String card;
    private int userId;
    private String formOfOrder;
    private double finalPrice;
    private String paymentMethod;
    private Date date;
    private String pickup;
    private String status;
    private int shopId;
    @ManyToMany
    private List<Flower> flowers;

public Order(String card, int userId, String formOfOrder, double finalPrice, String paymentMethod, Date date, String pickup, String status, int shopId, ArrayList<Flower> flowers) {
    super();
    this.card = card;
    this.userId = userId;
    this.formOfOrder = formOfOrder;
    this.finalPrice = finalPrice;
    this.paymentMethod = paymentMethod;
    this.date = date;
    this.pickup = pickup;
    this.status = status;
    this.shopId = shopId;

}

    public Order(double finalPrice,ArrayList<Flower> flowers) {
        this.finalPrice = finalPrice;
        this.flowers = new ArrayList<>(flowers);

    }

    public Order() {
        super();
        this.card = "card";
        this.pickup = "pickup";

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFormOfOrder() {
        return formOfOrder;
    }

    public void setFormOfOrder(String formOfOrder) {
        this.formOfOrder = formOfOrder;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }



}
