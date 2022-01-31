/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.multiplayer;

import engine.core.multiplayer.packets.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayDeque;

public class ClientThread extends Thread {

    private final Client client;
    private final ArrayDeque<Packet> queue = new ArrayDeque<>();
    private boolean connected = true;
    private DataInputStream dinp;
    private DataOutputStream dout;
    private boolean disconnected = false;

    public ClientThread(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket("localhost", client.port());
            client.onConnect();
            dinp = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
            while (!disconnected) {
                if (!queue.isEmpty()) {
                    dout.writeUTF(queue.poll().toString());
                    dout.flush();
                }
                Thread.sleep(1);
            }
        } catch (IOException e) {
            if (e instanceof SocketException) {
                connected = false;
                client.onDisconnect();
                return;
            }
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Packet sendPacket(Packet packet) throws IOException {
        queue.add(packet);
        return Packet.convertStringToPacket(dinp.readUTF());
    }

    public void disconnect() {
        disconnected = true;
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
