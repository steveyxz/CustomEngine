/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class ClientThread implements Runnable {

    private final Client client;
    private boolean connected = true;

    public ClientThread(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket("localhost", client.port());
            client.onConnect();
            DataInputStream dinp = new DataInputStream(socket.getInputStream());
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
            while (connected) {
                System.out.println("What do you say to the server? ");
                Scanner sc = new Scanner(System.in);
                dout.writeUTF(sc.nextLine());
                dout.flush();
                System.out.println("The server said '" + dinp.readUTF() + "'");
            }
            client.onDisconnect();
        } catch (IOException e) {
            if (e instanceof SocketException) {
                connected = false;
                client.onDisconnect();
                return;
            }
            e.printStackTrace();
        }
    }

    public Client client() {
        return client;
    }

    public boolean connected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
