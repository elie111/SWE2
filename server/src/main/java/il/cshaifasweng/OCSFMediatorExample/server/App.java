package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Catalog;
import il.cshaifasweng.OCSFMediatorExample.entities.Flower;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** Hello world! */
public class App {
	private static SimpleServer server;
    private static Session session;
    private static SessionFactory sessionFactory;
    private static FlowerController flowerController;
    private static CatalogController catalogController;
    static Scanner sc = new Scanner(System.in);
    private static UserController userController;

    public static SessionFactory getSession(){
        return sessionFactory;
    }

    private static SessionFactory getSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        // Add ALL of your entities here. You can also try adding a whole package.
        configuration.addAnnotatedClass(Catalog.class);
        configuration.addAnnotatedClass(Flower.class);
        configuration.addAnnotatedClass(User.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static void main( String[] args ) throws IOException {
        System.out.println("please enter the port number: ");
        server = new SimpleServer(sc.nextInt());
        server.listen();
        try {
            sessionFactory = getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            flowerController = new FlowerController(session);
            catalogController = new CatalogController(session);
            userController = new UserController(session);

            List<Flower> flowerslst = new ArrayList<Flower>();
            Catalog catalog = new Catalog();

            Flower floweritem = new Flower("Infinite Happiness","Lile",120);
            floweritem.setImageurl("Images/lile.jpg");
            floweritem.setCatalog(catalog);
            flowerslst.add(floweritem);
            session.save(floweritem);

            floweritem = new Flower("Rosy Smile","Rose",350);
            floweritem.setImageurl("Images/smileyroses.jpg");
            floweritem.setCatalog(catalog);
            flowerslst.add(floweritem);
            session.save(floweritem);

            floweritem = new Flower("Inmarcesible Passion","Red Rose",198);
            floweritem.setImageurl("Images/inmarcible.jpg");
            floweritem.setCatalog(catalog);
            flowerslst.add(floweritem);
            session.save(floweritem);

            floweritem = new Flower("Beaming Blush","roses, lilies and matthiola ",45);
            floweritem.setImageurl("Images/beamingblush.jpg");
            floweritem.setCatalog(catalog);
            flowerslst.add(floweritem);
            session.save(floweritem);

            floweritem = new Flower("Wild Cherry","Rose",150);
            floweritem.setImageurl("Images/wildcherry.jpg");
            floweritem.setCatalog(catalog);
            flowerslst.add(floweritem);
            session.save(floweritem);

            floweritem = new Flower("Velvety Burgundy","Rose",60);
            floweritem.setImageurl("Images/velvet.jpg");
            floweritem.setCatalog(catalog);
            flowerslst.add(floweritem);
            session.save(floweritem);

            floweritem = new Flower("Soulful Yellow","yellow flowers and roses",65);
            floweritem.setImageurl("Images/yellow.jpg");
            floweritem.setCatalog(catalog);
            flowerslst.add(floweritem);
            session.save(floweritem);

            floweritem = new Flower("Roseate Charm","combination of hydrangeas, chrysanthemums, amaryllis, and roses",160);
            floweritem.setImageurl("Images/roseat.jpg");
            floweritem.setCatalog(catalog);
            flowerslst.add(floweritem);
            session.save(floweritem);

            floweritem = new Flower("Ignited Passion","fiery roses",120);
            floweritem.setImageurl("Images/ignited.jpg");
            floweritem.setCatalog(catalog);
            flowerslst.add(floweritem);
            session.save(floweritem);

            floweritem = new Flower("Dreamy Pink","pink hyacinths",95);
            floweritem.setImageurl("Images/dreamypink.jpg");
            floweritem.setCatalog(catalog);
            flowerslst.add(floweritem);

            catalog.setFlowers(flowerslst);
            session.save(floweritem);
            session.save(catalog);

            session.flush();

            session.getTransaction().commit();
        } catch (Exception exception) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.err.println("An error occured, changes have been rolled back.");
            exception.printStackTrace();
        } finally {
            session.close();
        }
    }
}

