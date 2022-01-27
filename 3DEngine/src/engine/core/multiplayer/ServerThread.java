/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.multiplayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {

    private final Server server;
    private ServerSocket socket;
    private boolean online = true;

    public ServerThread(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        try {
            ServerSocket socket = new ServerSocket(server.port());
            this.socket = socket;
            server.onStart();
            while (online) {
                Socket accept = socket.accept();
                new Thread(new ServerCommThread(this, accept)).start();
                server.onAcceptConnection(accept);
            }
            server.onStop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ServerSocket socket() {
        return socket;
    }

    public Server server() {
        return server;
    }

    public boolean online() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
