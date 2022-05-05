package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Flower;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimpleServer extends AbstractServer {
	private static Session session;
	private static SessionFactory sessionFactory;
	private static FlowerController flowerController;
	private static CatalogController catalogController;
	private static UserController userController;
	static Scanner sc = new Scanner(System.in);

	public SimpleServer(int port) {
		super(port);
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) throws IOException,Exception {
		ArrayList<Object> arr = new ArrayList<>();
		try {
			//sessionFactory = getSessionFactory();
		 	session = App.getSession().openSession();
		 	session.beginTransaction();
			FlowerController flowerController = new FlowerController(session);
			CatalogController catalogController = new CatalogController(session);
			UserController userController = new UserController(session);
			flowerController.setSession(session);
			catalogController.setSession(session);

			arr = (ArrayList<Object>) msg;

			if ("#addflower".equals(arr.get(0))) {
				//1 is name 2 is type 3 is price
				Flower flower = new Flower((String)(arr.get(1)),(String)(arr.get(2)),(double)(arr.get(3)));
				flowerController.addFlower(flower);
				session.getTransaction().commit();
			}

			if ((arr.get(0)).equals("#updateflower")) {
				// 1 is id 2 is price 3 is discount
				flowerController.updateData((int)(arr.get(1)),(double)(arr.get(2)),(int)(arr.get(3)));
				session.getTransaction().commit();
			}

			if((arr.get(0)).equals("#getcatalog")) {
				List<Flower> lst = flowerController.getAllData(Flower.class);
				ArrayList<Object> answers = new ArrayList<>();
				ArrayList<ArrayList<Object>> newarr = new ArrayList<>();
				// 1 for name, 2 for id, 3 for price, 4 for sale
				// 5 for discount, 6 for color, 7 for type, 8 for image url
				for(int i = 0; i < lst.size(); i++) {
					newarr.add(new ArrayList<>());
					(newarr.get(i)).add(lst.get(i).getName());
					(newarr.get(i)).add(lst.get(i).getId());
					(newarr.get(i)).add(lst.get(i).getPrice());
					(newarr.get(i)).add(lst.get(i).getSale());
					(newarr.get(i)).add(lst.get(i).getDiscount());
					(newarr.get(i)).add(lst.get(i).getColor());
					(newarr.get(i)).add(lst.get(i).getType());
					(newarr.get(i)).add(lst.get(i).getImageurl());
				}
				answers.add("#getcatalog");
				newarr.add(answers);
				//if we added more than one catalog we must change 1
				sendToAllClients(newarr);
			}

			if((arr.get(0)).equals("#register")) {
				User user = new User((String)arr.get(1), (String)arr.get(2), (String)arr.get(3),
									 (String)arr.get(4), (String)arr.get(5), (String)arr.get(6),
									 (String)arr.get(7), (String)arr.get(8), (String)arr.get(9),
									 (String)arr.get(10));
				userController.addUser(user);
				session.getTransaction().commit();
			}

			if((arr.get(0)).equals("#loginUser")) {
				List<User> lst = userController.getAllData(User.class);
				ArrayList<Object> answers = new ArrayList<>();
				ArrayList<ArrayList<Object>> newarr = new ArrayList<>();

				String eMail = "", password = "";
				String myMail = (String)arr.get(1);
				String myPassword = (String)arr.get(2);

				for(int i = 0; i < lst.size(); i++) {
					eMail = lst.get(i).getEmail();
					if(eMail.equals(myMail)) {
						password = lst.get(i).getPassword();
						if(password.equals(myPassword)) {
							answers.add("#connectUser");
							answers.add(true);
							answers.add(lst.get(i));
							newarr.add(answers);
						}
					}
					else {
						answers.add("#connectUser");
						answers.add(false);
						newarr.add(answers);
					}
				}

				client.sendToClient(newarr);
			}
		}
		catch (Exception exception) {
			if (session != null) {
				session.getTransaction().rollback();
			}
			System.err.println("An error occured, changes have been rolled back.");
			exception.printStackTrace();
		}
		finally {
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
		if (args.length != 1) {
			System.out.println("Required argument: <port>");
		}
		else {
			System.out.println("please enter the port number: ");
			SimpleServer server = new SimpleServer(sc.nextInt());
			server.listen();
		}
	}
}
