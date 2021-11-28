/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester;

import engine.core.multiplayer.packets.Packet;
import engine.core.multiplayer.packets.PacketManager;

import java.io.IOException;

public class TestScriptTwo {

    public static void main(String[] args) throws IOException {
        new TestClient(10).sendPacket(PacketManager.getNewPacket("test", Packet.parseArguments("string[hi];")));
    }

}
