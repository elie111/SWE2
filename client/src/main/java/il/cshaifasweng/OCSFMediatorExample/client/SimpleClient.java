package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Flower;
import il.cshaifasweng.OCSFMediatorExample.entities.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

public class SimpleClient extends AbstractClient {
	private static final Logger LOGGER = Logger.getLogger(SimpleClient.class.getName());
	static Scanner sc = new Scanner(System.in);
	private static SimpleClient client = null;

	public SimpleClient(String host, int port) {
		super(host, port);
	}

	public static SimpleClient getClient() {
		if (client == null) {
			System.out.println("please enter the IP address and then the port number: ");
			client = new SimpleClient(sc.next(), sc.nextInt());
		}
		return client;
	}

	@Override
	protected void connectionEstablished() {
		// TODO Auto-generated method stub
		super.connectionEstablished();
		LOGGER.info("Connected to server.");
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		ArrayList<Flower> arr = new ArrayList<>();
		ArrayList<ArrayList<Object>> arrarr = new ArrayList<ArrayList<Object>>();
		arrarr = (ArrayList<ArrayList<Object>>)(msg);

		String answer = (String)arrarr.get(arrarr.size() - 1).get(0);

		if(answer.equals("#getcatalog")) {
			for(int i = 0; i < arrarr.size() - 1; i++) {
				//go over all the flowers
				Flower f = new Flower();
				f.setId((int)arrarr.get(i).get(1));
				f.setPrice((double)arrarr.get(i).get(2));
				f.setColor((String) arrarr.get(i).get(5));
				f.setDiscount((int)arrarr.get(i).get(4));
				f.setName((String) arrarr.get(i).get(0));
				f.setType((String) arrarr.get(i).get(6));
				f.setSale((Boolean) arrarr.get(i).get(3));
				f.setImageurl((String) arrarr.get(i).get(7));
				arr.add(f);
			}
			CatalogBoundaryController.setFlowers(arr);
		}

		if(answer.equals("#connectUser")) {
			boolean a = (boolean)arrarr.get(arrarr.size() - 1).get(1);
			if(a == true) {
				User user = new User((String)arrarr.get(arrarr.size() - 1).get(2),
									 (String)arrarr.get(arrarr.size() - 1).get(3),
									 (String)arrarr.get(arrarr.size() - 1).get(4),
									 (String)arrarr.get(arrarr.size() - 1).get(5),
									 (String)arrarr.get(arrarr.size() - 1).get(6),
									 (String)arrarr.get(arrarr.size() - 1).get(7),
									 (String)arrarr.get(arrarr.size() - 1).get(8),
									 (String)arrarr.get(arrarr.size() - 1).get(9),
									 (String)arrarr.get(arrarr.size() - 1).get(10),
									 (String)arrarr.get(arrarr.size() - 1).get(11));
				int id = (int)arrarr.get(arrarr.size() - 1).get(12);
				UserHolder.setUser(user);
				UserHolder.setID(id);
				int index = (int)arrarr.get(arrarr.size() - 1).get(13);
				if(index == 1) {
					LoginController loginController = new LoginController();
					loginController.nextStep(1);
				}
			}
			else {
				LoginController loginController = new LoginController();
				loginController.nextStep(2);
			}
		}
	}

	@Override
	protected void connectionClosed() {
		// TODO Auto-generated method stub
		super.connectionClosed();
	}

	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("Required arguments: <host> <port>");
		}
		else {
			String host = args[0];
			int port = Integer.parseInt(args[1]);
			SimpleClient Client = new SimpleClient(host, port);
			Client.openConnection();
		}
	}
}