/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.multiplayer.packets;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class Packet {

    static {
        Reflections r = new Reflections();
        Set<Class<? extends Packet>> classes = r.getSubTypesOf(Packet.class);
        System.out.println(classes);
        for (Class<? extends Packet> p : classes) {
            try {
                p.getDeclaredConstructor(List.class).newInstance((Object) null);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    private final String id;
    private final List<PacketArgument> arguments;

    public Packet(String id, List<PacketArgument> arguments) {
        this.id = id;
        this.arguments = arguments;
        PacketManager.addPacket(this);
    }

    public static Packet convertStringToPacket(String string) {
        return PacketManager.getNewPacket(string.substring(0, string.indexOf(':')), Packet.parseArguments(string.substring(string.indexOf(':') + 1)));
    }

    //Args should be the part after the semicolon
    public static List<PacketArgument> parseArguments(String args) {
        String[] argList = args.split(";");
        ArrayList<PacketArgument> returned = new ArrayList<>();
        for (String argument : argList) {
            returned.add(new PacketArgument(argument));
        }
        return returned;
    }

    public static List<PacketArgument> getPacketArgsList(PacketArgument... args) {
        return List.of(args);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (PacketArgument argument : arguments) {
            sb.append(argument.toString());
            sb.append(";");
        }
        return id + ":" + new String(sb);
    }

    public String id() {
        return id;
    }

    public List<PacketArgument> arguments() {
        return arguments;
    }
}
