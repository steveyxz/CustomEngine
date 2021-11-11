/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.multiplayer;

public abstract class Server {

    private final int port;

    public Server(int port) {
        this.port = port;
        new Thread(new ServerThread(this)).start();
    }

    public int port() {
        return port;
    }

    public abstract void onStart();
    public abstract void onStop();
    public abstract void onAcceptConnection();

}
