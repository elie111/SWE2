package il.cshaifasweng.OCSFMediatorExample.server;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Flow;


import il.cshaifasweng.OCSFMediatorExample.entities.Catalog;
import il.cshaifasweng.OCSFMediatorExample.entities.Flower;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class SimpleServer extends AbstractServer {
	private static Session session;
	private static SessionFactory sessionFactory;
	private static FlowerController flowerController;
	private static CatalogController catalogController;
	static Scanner  sc=new Scanner(System.in);
	private static SessionFactory getSessionFactory() throws HibernateException {

		Configuration configuration = new Configuration();
		// Add ALL of your entities here. You can also try adding a whole package.
		configuration.addAnnotatedClass(Catalog.class);
		configuration.addAnnotatedClass(Flower.class);



		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.build();

		return configuration.buildSessionFactory(serviceRegistry);
	}

	public SimpleServer(int port) {
		super(port);
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) throws IOException,Exception {

		ArrayList<Object> arr=new ArrayList<>();
		try
		{
		 //sessionFactory = getSessionFactory();
		 session = sessionFactory.openSession();
		 session.beginTransaction();
		 flowerController.setSession(session);
		 catalogController.setSession(session);

		arr=(ArrayList<Object>) msg;


		if ("#addflower".equals(arr.get(0))) {
			Flower flower=new Flower((String)(arr.get(1)),(String)(arr.get(2)),(double)(arr.get(3)));

			flowerController.addFlower(flower);

			session.getTransaction().commit();
		}
		//1 is name 2 is type 3 is price

		if((arr.get(0)).equals("#updateflower")){

			flowerController.updateData((int)(arr.get(1)),(double)(arr.get(2)),(int)(arr.get(3)));
			session.getTransaction().commit();
			// 1 is id 2 is price 3 is discount
		}
		if((arr.get(0)).equals("#getcatalog")){
			List<Flower> lst=flowerController.getAllData(Flower.class);

			ArrayList<ArrayList<Object>> newarr=new ArrayList<>();
			for(int i=0;i< lst.size();i++){
				newarr.add(new ArrayList<>());
				(newarr.get(i)).add(lst.get(i).getName());
				(newarr.get(i)).add(lst.get(i).getId());
				(newarr.get(i)).add(lst.get(i).getPrice());
				(newarr.get(i)).add(lst.get(i).getSale());
				(newarr.get(i)).add(lst.get(i).getDiscount());
				(newarr.get(i)).add(lst.get(i).getColor());
				(newarr.get(i)).add(lst.get(i).getType());
			}

			// 1 for name
			// 2 for id
			// 3 for price
			//4 for sale
			// 5 for discount
			//6 for color
			//7 for type
			sendToAllClients(newarr);
			//if we added more than one catalog we must change 1
		}


	} catch (Exception exception) {
		if (session != null) {
			session.getTransaction().rollback();

		}
		System.err.println("An error occured, changes have been rolled back.");
		exception.printStackTrace();
	} finally

	{

		session.close();


	}

	}




	@Override
	protected synchronized void clientDisconnected(ConnectionToClient client) {
		// TODO Auto-generated method stub

		System.out.println("Client Disconnected.");
		super.clientDisconnected(client);
	}



	@Override
	protected void clientConnected(ConnectionToClient client) {
		super.clientConnected(client);
		System.out.println("Client connected: " + client.getInetAddress());
	}
	private static <T> List<T> getAllData(Class<T> c) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = builder.createQuery(c);
		Root<T> rootEntry = criteriaQuery.from(c);
		CriteriaQuery<T> allCriteriaQuery = criteriaQuery.select(rootEntry);
		TypedQuery<T> allQuery = session.createQuery(allCriteriaQuery);
		return allQuery.getResultList();
	}

	public static void main(String[] args) throws IOException,Exception {

		try {

			sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			flowerController=new FlowerController(session);
			catalogController=new CatalogController(session);

			List<Flower> flowerslst = new ArrayList<Flower>();
			for(int i=0; i<5;i++){


				Flower floweritem=new Flower("name of flower"+i,"type"+i,i*9);
				flowerslst.add(floweritem);
				session.save(floweritem);
				session.flush();



			}

			Flower flower=new Flower("name flower","type",100);
			flowerController.addFlower(flower);
			Flower flower2=new Flower("name flower2","type",100);
			flowerController.addFlower(flower2);
			flowerController.updateData(1,12,0);
			//flowerController.deleteFlower(flower);
			//session.delete(flower);
			flowerController.getDetails(flower.getId());



			session.getTransaction().commit();

			if (args.length != 1) {
				System.out.println("Required argument: <port>");
			} else {
				SimpleServer server = new SimpleServer(Integer.parseInt(args[0]));
				server.listen();
			}
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
