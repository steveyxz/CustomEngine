/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.multiplayer;

import engine.core.multiplayer.packets.InvalidPacket;
import engine.core.multiplayer.packets.Packet;
import engine.core.multiplayer.protocols.ProtocolHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

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
                System.out.println("Client sends packet: '" + inp + "'");
                receivePacket(Packet.convertStringToPacket(inp), dout);
            }
        } catch (Exception e) {
            if (e instanceof SocketException) {
                System.out.println("Client " + connection.getLocalAddress() + ":" + connection.getLocalPort() + " disconnected.");
            } else {
                e.printStackTrace();
            }
        }
    }

    private void receivePacket(Packet packet, DataOutputStream out) throws IOException {
        Packet returnPacket = ProtocolHandler.callServerProtocol(packet);
        if (returnPacket == null) {
            out.writeUTF(new InvalidPacket().toString());
            out.flush();
            return;
        }
        out.writeUTF(returnPacket.toString());
        out.flush();
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
