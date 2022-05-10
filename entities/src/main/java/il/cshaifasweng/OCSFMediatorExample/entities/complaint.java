package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;

@Entity
@Table(name = "complaint")

public class Compleint{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String orderId;
    private String content;
    private boolean status;
    private localDate date;
    private String answer;

    public Compleint(String name, String identificationNumber, String email, String phone, String credit,
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

    public Compleint(int id, String orderId, String content, boolean status, Date date, String answer) {
        this.id = id;
        this.orderId = orderId;
        this.content = content;
        this.status = status;
        this.date = date;
        this.answer = answer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getContent() {
        return content;
    }

    public boolean isStatus() {
        return status;
    }

    public Date getDate() {
        return date;
    }

    public String getAnswer() {
        return answer;
    }
}
