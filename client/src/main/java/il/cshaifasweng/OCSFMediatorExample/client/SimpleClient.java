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
		ArrayList<ArrayList<Object>> newarr = new ArrayList<>();
		newarr = (ArrayList<ArrayList<Object>>)msg;

		ArrayList<Flower> arr = new ArrayList<>();
		// right now we only need the caralog from the server if we changed that
		// then we need to also get #getcatalog from the server
		ArrayList<ArrayList<Object>> arrarr = new ArrayList<ArrayList<Object>>();
		arrarr = (ArrayList<ArrayList<Object>>)(msg);

		for(int i = 0; i < arrarr.size(); i++) {
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
        CatalogBoundary.setFlowers(arr);
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