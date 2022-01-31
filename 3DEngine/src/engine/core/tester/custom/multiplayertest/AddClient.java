/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester.custom.multiplayertest;

import engine.core.multiplayer.Client;

public class AddClient extends Client {
    public AddClient(int port) {
        super(port);
    }

    @Override
    public void onConnect() {
        System.out.println("Connected!");
    }

    @Override
    public void onDisconnect() {
        System.out.println("Disconnected!");
    }
}
