package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String card;
    private Date date;
    private String pickup;

    public Order( String card, Date date, String pickup) {
        //this.id = id;
        this.card = card;
        this.date = date;
        this.pickup = pickup;
    }
    public Order()
    {
        this.card = "card";
        this.pickup = "pickup";
    }



}
