/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester;

import engine.core.multiplayer.packets.Packet;
import engine.core.multiplayer.packets.PacketArgument;

import java.util.ArrayList;

public class TestPacket extends Packet {
    public TestPacket(ArrayList<PacketArgument> arguments) {
        super("test", arguments);
    }
}
