package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Flower;

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

		ArrayList<Object> msgarray=new ArrayList<>();

		msgarray=(ArrayList<Object>) msg;


		if(msgarray.get(0).equals("#getcatalog")) {
			CatalogBoundary.setFlowers((ArrayList<Flower>)( msgarray.get(1)));
			CatalogEmployee.setFlowers((ArrayList<Flower>)( msgarray.get(1)));
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