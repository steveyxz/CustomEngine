/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester;

import engine.core.multiplayer.packets.Packet;
import engine.core.multiplayer.packets.PacketArgument;

import java.util.ArrayList;

public class RunClient {

    public static void main(String[] args) {
        TestClient c = new TestClient(1234);
        Packet p = c.sendPacket(new TestPacket((ArrayList<PacketArgument>) Packet.parseArguments("int[5]")));
        System.out.println(p);
    }

}
