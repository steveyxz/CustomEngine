/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.multiplayer.packets;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PacketManager {

    public static final List<Packet> packets = new ArrayList<>();

    public static Packet getNewPacket(String type, List<PacketArgument> args) {
        try {
            if (getPacketByName(type) == null) {
                return null;
            }
            return Objects.requireNonNull(getPacketByName(type)).getClass().getDeclaredConstructor(args.getClass()).newInstance(args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addPacket(Packet packet) {
        packets.add(packet);
    }

    public static void removePacket(Packet packet) {
        packets.remove(packet);
    }

    public static Packet getPacketByName(String name) {
        for (Packet p : packets) {
            if (p.id().equals(name)) {
                return p;
            }
        }
        return null;
    }

    public static void removePacketByName(String name) {
        packets.removeIf(p -> p.id().equals(name));
    }

}
