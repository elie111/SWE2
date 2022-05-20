package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "complaint")

public class Complaint implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
<<<<<<< Updated upstream
    private int userId;
    private int orderId; //number of order
    private int content;
    private LocalDateTime dateTime; //date of complaint
    private int status; //status of order

    public Complaint(int userId, int orderId, int content, LocalDateTime dateTime, int status) {
=======
    private String content;
    // date of complaint
    private String dateTime;
    // order number
    private int orderId;
    // status of complaint: 1 = submitted, 2 = in process, 3 = closed
    private int status;
    private int userId;

    public Complaint(int userId, int orderId, String content, String dateTime, int status) {
>>>>>>> Stashed changes
        super();
        this.userId = userId;
        this.orderId = orderId;
        this.content = content;
        this.dateTime = dateTime;
        this.status = status;
    }

    public Complaint() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

<<<<<<< Updated upstream
    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    //0 - complaint is open, 1 - complaint in process, 2- user got anser about the complaint
=======
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

>>>>>>> Stashed changes
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
