/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester.custom;

import engine.core.multiplayer.Server;

import java.net.Socket;

public class AddServer extends Server {
    public AddServer(int port) {
        super(port);
    }

    @Override
    public void onStart() {
        System.out.println("Started!");
    }

    @Override
    public void onStop() {
        System.out.println("Stopped!");
    }

    @Override
    public void onAcceptConnection(Socket s) {
        System.out.println(s.getLocalAddress() + " joined");
    }
}
