/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester;

import engine.core.multiplayer.protocols.ProtocolHandler;

public class RunServer {

    public static void main(String[] args) {
        new AddPacket();
        new NumberPacket();
        ProtocolHandler.registerServerProtocol(AddProtocol.class);
        new TestServer(1234);
    }
}
