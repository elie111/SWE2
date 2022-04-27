package il.cshaifasweng.OCSFMediatorExample.entities;


import javax.persistence.*;

@Entity
@Table(name = "flowers")
public class Flower {
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
    @ManyToOne
    private Catalog catalog;

    public Flower() {
        this.imageurl="Images/lile.jpg" ;//defualt image
        this.sale=false;
        this.color="red";
    }

    public String getImageurl() {
        return imageurl;

    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Flower(String name, String type, double price) {
        super();
        this.name = name;
        this.type = type;

        this.price = price;
        this.imageurl="Images/lile.jpg" ;//defualt image
        this.sale=false;
        this.color="red";

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getSale() {
        return sale;
    }

    public void setSale(Boolean sale) {
        this.sale = sale;
    }
}
