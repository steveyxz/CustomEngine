/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.multiplayer.packets;

import java.util.ArrayList;
import java.util.List;

public class InvalidPacket extends Packet {
    public InvalidPacket() {
        super("invalid", new ArrayList<>());
    }
}
