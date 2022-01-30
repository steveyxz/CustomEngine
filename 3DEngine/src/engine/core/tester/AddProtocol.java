/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tester;

import engine.core.multiplayer.packets.Packet;
import engine.core.multiplayer.packets.PacketArgument;
import engine.core.multiplayer.packets.PacketManager;
import engine.core.multiplayer.protocols.ProtocolHandler;
import engine.core.multiplayer.protocols.ServerProtocol;

public class AddProtocol extends ServerProtocol {
    public AddProtocol(Packet sentPacket) {
        super(sentPacket);
    }

    @Override
    public Packet call() {
        return PacketManager.getNewPacket("number", Packet.getPacketArgsList(new PacketArgument((int) sentPacket().arguments().get(0).data() + (int) sentPacket().arguments().get(1).data())));
    }

    @Override
    public String getReceivingId() {
        return "add";
    }
}
