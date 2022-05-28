package il.cshaifasweng.OCSFMediatorExample.client;

public class OrderHolder {
    private String orderID;
    private String dateTime;
    private String finalPrice;
    private String flowers;
    private String status;

    public OrderHolder(int orderID, String dateTime, double finalPrice, String flowers, int status) {
        this.orderID = Integer.toString(orderID);
        this.dateTime = dateTime;
        this.finalPrice = Double.toString(finalPrice);

        this.flowers = flowers;

        if(status == 1) {
            this.status = "Active";
        }
        else if(status == 2) {
            this.status = "Supplied";
        }
        else if(status == 3) {
            this.status = "Cancelled";
        }
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getFlowers() {
        return flowers;
    }

    public void setFlowers(String flowers) {
        this.flowers = flowers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
