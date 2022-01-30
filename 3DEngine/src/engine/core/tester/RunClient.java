/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester;

import engine.core.multiplayer.packets.Packet;
import engine.core.multiplayer.packets.PacketArgument;
import engine.core.multiplayer.protocols.ProtocolHandler;

import java.io.IOException;
import java.util.ArrayList;

public class RunClient {

    public static void main(String[] args) throws IOException {
        new AddPacket();
        new NumberPacket();
        ProtocolHandler.registerServerProtocol(AddProtocol.class);
        TestClient c = new TestClient(1234);
        Packet p = c.sendPacket(new AddPacket(3, 4));
        System.out.println(p);
    }

}
