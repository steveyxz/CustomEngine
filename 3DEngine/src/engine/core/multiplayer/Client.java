/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.multiplayer;

import engine.core.multiplayer.packets.Packet;

import java.io.IOException;

public abstract class Client {

    private final int port;
    private final ClientThread thread;

    public Client(int port) {
        this.port = port;
        this.thread = new ClientThread(this);
        thread.start();
    }

    public int port() {
        return port;
    }

    public abstract void onConnect();

    public abstract void onDisconnect();

    public void sendPacket(Packet packet) {
        thread.sendPacket(packet);
    }
}
