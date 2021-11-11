/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.multiplayer.packets;

import java.util.List;

public abstract class Packet {

    private final String id;
    private final List<PacketArgument> arguments;

    public Packet(String id, List<PacketArgument> arguments) {
        this.id = id;
        this.arguments = arguments;
    }

    public String id() {
        return id;
    }

    public List<PacketArgument> arguments() {
        return arguments;
    }
}
