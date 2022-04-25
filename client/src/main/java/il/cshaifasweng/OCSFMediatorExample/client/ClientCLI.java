package il.cshaifasweng.OCSFMediatorExample.client;



import il.cshaifasweng.OCSFMediatorExample.entities.Flower;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Flow;

public class ClientCLI {

    private SimpleClient client;
    private boolean isRunning;
    private static final String SHELL_STRING = "Enter message (or exit to quit)> ";
    private Thread loopThread;

    public ClientCLI(SimpleClient client) {
        this.client = client;
        this.isRunning = false;
    }

    public void loop() throws IOException {
        loopThread = new Thread(new Runnable() {

            @Override
            public void run() {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String message;
                while (client.isConnected()) {
                   // System.out.print(SHELL_STRING);
                    try {
                        message = reader.readLine();
                        if (message.isBlank())
                            continue;
                        Flower flower=new Flower("name","type",(double) 34);


                        ArrayList<Object> arr=new ArrayList<>();
                        arr.add(message);
                        arr.add("name");
                        arr.add("type2");
                        arr.add(420.0);
                        client.sendToServer(arr);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                }
            }
        });

        loopThread.start();
        this.isRunning = true;

    }

    public void displayMessage(Object message) {
        if (isRunning) {
            System.out.print("(Interrupted)\n");
        }
     //   System.out.println("Received message from server: " + message.toString());
        if (isRunning)
            System.out.print(SHELL_STRING);
    }

    public void closeConnection() {
        System.out.println("Connection closed.");
        System.exit(0);
    }
}