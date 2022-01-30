/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester;

import engine.core.multiplayer.packets.Packet;
import engine.core.multiplayer.packets.PacketArgument;

import java.util.List;

public class NumberPacket extends Packet {
    public NumberPacket(int number) {
        super("number", Packet.getPacketArgsList(new PacketArgument(number)));
    }

    public NumberPacket(List<PacketArgument> p) {
        super("number", p);
    }

    public NumberPacket() {
        this(0);
    }
}
