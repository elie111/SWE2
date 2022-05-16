package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.*;

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
		ArrayList<Object> msgArray = new ArrayList<>();
		msgArray = (ArrayList<Object>) msg;

		if(msgArray.get(0).equals("#getcatalog")) {
			CatalogBoundaryController.setFlowers((ArrayList<Flower>)(msgArray.get(1)));
			CatalogEmployeeController.setFlowers((ArrayList<Flower>)(msgArray.get(1)));
		}

		if(msgArray.get(0).equals("#connectUserAfterRegistration")) {
			EntityHolder.setTable(0);
			User user = new User((String)msgArray.get(1), (String)msgArray.get(2),
								 (String)msgArray.get(3), (String)msgArray.get(4),
								 (String)msgArray.get(5), (String)msgArray.get(6),
								 (String)msgArray.get(7), (String)msgArray.get(8),
								 (String)msgArray.get(9), (String)msgArray.get(10),
								 (double)msgArray.get(11));
			int id = (int)msgArray.get(12);
			EntityHolder.setUser(user);
			EntityHolder.setID(id);
			RegistrationBoundaryController r = new RegistrationBoundaryController();
			r.nextStep();
		}

		if(msgArray.get(0).equals("#connectEntity")) {
			if((boolean)msgArray.get(1) == true) {
				if(msgArray.get(2).equals("User")) {
					EntityHolder.setTable(0);
					User user = new User((String)msgArray.get(3), (String)msgArray.get(4),
										 (String)msgArray.get(5), (String)msgArray.get(6),
										 (String)msgArray.get(7), (String)msgArray.get(8),
										 (String)msgArray.get(9), (String)msgArray.get(10),
										 (String)msgArray.get(11), (String)msgArray.get(12),
										 (double)msgArray.get(13));
					int id = (int)msgArray.get(14);
					EntityHolder.setUser(user);
					EntityHolder.setID(id);
					LoginBoundaryController loginController = new LoginBoundaryController();
					loginController.nextStep(2);
				}
				else if(msgArray.get(2).equals("Employee")) {
					EntityHolder.setTable(1);
					Employee employee = new Employee((String)msgArray.get(3),
													 (String)msgArray.get(4),
													 (String)msgArray.get(5));
					int id = (int)msgArray.get(6);
					EntityHolder.setEmployee(employee);
					EntityHolder.setID(id);
					LoginBoundaryController loginController = new LoginBoundaryController();
					loginController.nextStep(3);
				}
				else if(msgArray.get(2).equals("Store Manager")) {
					EntityHolder.setTable(2);
					StoreManager storeManager = new StoreManager((String)msgArray.get(3),
																 (String)msgArray.get(4),
																 (String)msgArray.get(5),
																 (String)msgArray.get(6));
					int id = (int)msgArray.get(7);
					EntityHolder.setStoreM(storeManager);
					EntityHolder.setID(id);
					LoginBoundaryController loginController = new LoginBoundaryController();
					loginController.nextStep(4);
				}
				else if(msgArray.get(2).equals("Chain Manager")) {
					EntityHolder.setTable(3);
					ChainManager chainManager = new ChainManager((String)msgArray.get(3),
																 (String)msgArray.get(4),
																 (String)msgArray.get(5));
					int id = (int)msgArray.get(6);
					EntityHolder.setChainM(chainManager);
					EntityHolder.setID(id);
					LoginBoundaryController loginController = new LoginBoundaryController();
					loginController.nextStep(5);
				}
			}
			else {
				LoginBoundaryController loginController = new LoginBoundaryController();
				loginController.showMessage();
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

/*
@Override
	protected void handleMessageFromServer(Object msg) {
		ArrayList<Flower> arr = new ArrayList<>();

		ArrayList<Object> msgarray=new ArrayList<>();

		msgarray=(ArrayList<Object>) msg;


		if(msgarray.get(0).equals("#getcatalog")) {
			CatalogBoundary.setFlowers((ArrayList<Flower>)( msgarray.get(1)));
			CatalogEmployee.setFlowers((ArrayList<Flower>)( msgarray.get(1)));
		}

	}

 */