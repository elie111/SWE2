package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "flowers")
public class Flower implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String type;
    private int discount;
    private String imageurl;
    private double price;
    private String color;
    private Boolean sale;
    private String description;

    @ManyToOne
    private Catalog catalog;

    public Flower() {
        super();
        this.sale = false;
        this.discount = 0;
    }

    public Flower(String name, String description, String type, String image, String color, double price) {
        super();
        this.name = name;
        this.description = description;
        this.type = type;
        this.imageurl = image;
        this.color = color;
        this.price = price;
        this.sale = false;
        this.discount = 0;
    }

    public String getImageurl() {
        return imageurl;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getDiscount() {
        return discount;
    }

    public double getPrice() {
        return price;
    }

    public String getColor() {
        return color;
    }

    public Boolean getSale() {
        return sale;
    }

    public String getDescription() {
        return description;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setSale(Boolean sale) {
        this.sale = sale;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
