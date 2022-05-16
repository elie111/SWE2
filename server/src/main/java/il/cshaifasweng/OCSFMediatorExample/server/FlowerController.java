package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Flower;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class FlowerController {
    private Session session;

    public FlowerController(Session session) {
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

    public Flower getDetails(int id) throws Exception {
        List<Flower> flowers = getAllData(Flower.class);
        Flower flower = null;
        for (int i = 0; i < flowers.size(); i++) {
            if (flowers.get(i).getId() == id) {
                flower = flowers.get(i);
                break;
            }
        }
        return flower;
    }

    public void updateData(Flower flower) throws Exception {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Flower> criteriaQuery = builder.createQuery(Flower.class);
        Root<Flower> rootEntry = criteriaQuery.from(Flower.class);
        CriteriaQuery<Flower> allCriteriaQuery = criteriaQuery.select(rootEntry);
        TypedQuery<Flower> allQuery = session.createQuery(allCriteriaQuery);

        session.update(flower);
        session.flush();
    }

    public void addFlower(Flower flower) throws Exception {
        session.save(flower);
        session.flush();
    }

    public void deleteFlower(Flower flower) throws Exception {
        session.delete(flower);
        session.flush();
    }
}
