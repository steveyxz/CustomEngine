/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.multiplayer;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ServerCommThread implements Runnable {

    private final ServerThread server;
    private final Socket connection;
    private boolean connected = true;

    public ServerCommThread(ServerThread server, Socket connection) {
        this.server = server;
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            DataInputStream dinp = new DataInputStream(connection.getInputStream());
            DataOutputStream dout = new DataOutputStream(connection.getOutputStream());
            while (connected) {
                String inp = dinp.readUTF();
                System.out.println("Client says: '" + inp + "'");
                String out = new String(new StringBuilder(inp).reverse());
                System.out.println("We say: '" + out + "'");
                dout.writeUTF(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ServerThread server() {
        return server;
    }

    public boolean connected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
