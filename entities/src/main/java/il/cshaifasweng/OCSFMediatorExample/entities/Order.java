package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name = "orders")

public class Order {
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
    @OneToMany
    private ArrayList<Flower> flowers;

    public Order(String card, int userId, String formOfOrder, double finalPrice, String paymentMethod, Date date, String pickup, String status, int shopId, ArrayList<Flower> flowers) {
        this.card = card;
        this.userId = userId;
        this.formOfOrder = formOfOrder;
        this.finalPrice = finalPrice;
        this.paymentMethod = paymentMethod;
        this.date = date;
        this.pickup = pickup;
        this.status = status;
        this.shopId = shopId;
        this.flowers = new ArrayList<>();
    }

    public Order(String card, Date date, String pickup) {
        this.flowers=new ArrayList<>();
        this.card = card;
        this.date = date;
        this.pickup = pickup;

    }

    public Order() {
        this.card = "card";
        this.pickup = "pickup";
    }
}
