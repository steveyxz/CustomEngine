/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.multiplayer.packets;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PacketManager {

    public static final Map<String, Packet> packets = new HashMap<>();

    public static Packet getNewPacket(String type, List<PacketArgument> args) {
        try {
            if (getPacketByName(type) == null) {
                return null;
            }
            return Objects.requireNonNull(getPacketByName(type)).getClass().getDeclaredConstructor(List.class).newInstance(args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addPacket(Packet packet) {
        packets.put(packet.id(), packet);
    }

    public static void removePacket(Packet packet) {
        packets.remove(packet.id());
    }

    public static Packet getPacketByName(String name) {
        return packets.get(name);
    }

    public static void removePacketByName(String name) {
        packets.remove(name);
    }

}
