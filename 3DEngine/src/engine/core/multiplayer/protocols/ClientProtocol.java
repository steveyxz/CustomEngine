/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.multiplayer.protocols;

import engine.core.multiplayer.Client;
import engine.core.multiplayer.packets.Packet;

public abstract class ClientProtocol {

    private final Packet sentPacket;

    public ClientProtocol(Packet sentPacket) {
        this.sentPacket = sentPacket;
    }

    public Packet sentPacket() {
        return sentPacket;
    }

    public abstract void call();
    public abstract String getReceivingId();
}
