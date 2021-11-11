/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.multiplayer;

public abstract class Client {

    private final int port;

    public Client(int port) {
        this.port = port;
        new Thread(new ClientThread(this)).start();
    }

    public int port() {
        return port;
    }

    public abstract void onConnect();
    public abstract void onDisconnect();

}
