package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Order;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class OrderController {
    private Session session;

    public OrderController(Session session) {
        this.session = session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public <T> List<T> getAllData(Class<T> c) throws Exception {


        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(c);
        Root<T> rootEntry = criteriaQuery.from(c);
        CriteriaQuery<T> allCriteriaQuery = criteriaQuery.select(rootEntry);
        TypedQuery<T> allQuery = session.createQuery(allCriteriaQuery);
        return allQuery.getResultList();


    }


//    public Order getDetails(int id) throws Exception {
//        List<Order> orders = getAllData(Order.class);
//        Order order = null;
//        for (int i = 0; i < orders.size(); i++) {
//            if (orders.get(i).getId() == id) {
//                order = orders.get(i);
//                break;
//            }
//        }
//        return order;
//
//    }

    public void updateData(int id, double price, int sale) throws Exception {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
        Root<Order> rootEntry = criteriaQuery.from(Order.class);
        CriteriaQuery<Order> allCriteriaQuery = criteriaQuery.select(rootEntry);
        TypedQuery<Order> allQuery = session.createQuery(allCriteriaQuery);
        //allQuery.getResultList().get(id-1).setPrice(price);
        //allQuery.getResultList().get(id-1).setDiscount(sale);

        session.update(allQuery.getResultList().get(id - 1));

        session.flush();


    }

    public void addOrder(Order order) throws Exception {

        session.save(order);

        session.flush();

    }

    public void deleteOrder(Order order) throws Exception {
        session.delete(order);
        session.flush();


    }

    public OrderController() {
    }
}