package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Complaint;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ComplaintController {
    private Session session;

    public ComplaintController(Session session) {
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

    public void addComplaint(Complaint complaint) throws Exception {
        session.save(complaint);
        session.flush();
    }

    public void updateData(int id, Complaint complain) throws Exception {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Complaint> criteriaQuery = builder.createQuery(Complaint.class);
        Root<Complaint> rootEntry = criteriaQuery.from(Complaint.class);
        CriteriaQuery<Complaint> allCriteriaQuery = criteriaQuery.select(rootEntry);
        TypedQuery<Complaint> allQuery = session.createQuery(allCriteriaQuery);

        allQuery.getResultList().get(id - 1).setContent(complain.getContent());
        allQuery.getResultList().get(id - 1).setDateTime(complain.getDateTime());
        allQuery.getResultList().get(id - 1).setOrderId(complain.getOrderId());
        allQuery.getResultList().get(id - 1).setStatus(complain.getStatus());
        allQuery.getResultList().get(id - 1).setUserId(complain.getUserId());
        allQuery.getResultList().get(id - 1).setPrice(complain.getPrice());

        session.update(allQuery.getResultList().get(id - 1));
        session.flush();
    }
}
