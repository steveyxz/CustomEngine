/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.multiplayer.protocols;

import engine.core.multiplayer.packets.Packet;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ProtocolHandler {

    private static final Map<String, Class<? extends ClientProtocol>> clientProtocols = new HashMap<>();
    private static final Map<String, Class<? extends ServerProtocol>> serverProtocols = new HashMap<>();

    public static void registerClientProtocol(ClientProtocol protocol) {
        clientProtocols.put(protocol.getReceivingId(), protocol.getClass());
    }

    public static void registerClientProtocol(Class<? extends ClientProtocol> protocolClass) {
        ClientProtocol protocol;
        try {
            protocol = protocolClass.getDeclaredConstructor(Packet.class).newInstance(new Packet(null, null) {});
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return;
        }
        clientProtocols.put(protocol.getReceivingId(), protocolClass);
    }

    public static void registerServerProtocol(ServerProtocol protocol) {
        serverProtocols.put(protocol.getReceivingId(), protocol.getClass());
    }

    public static void registerServerProtocol(Class<? extends ServerProtocol> protocolClass) {
        ServerProtocol protocol;
        try {
            protocol = protocolClass.getDeclaredConstructor(Packet.class).newInstance(new Packet(null, null) {});
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return;
        }
        serverProtocols.put(protocol.getReceivingId(), protocolClass);
    }

    public static void callClientProtocol(Packet input) {
        Class<? extends ClientProtocol> type = clientProtocols.get(input.id());
        if (type == null) {
            return;
        }
        try {
            ClientProtocol toCall = type.getDeclaredConstructor(Packet.class).newInstance(new Packet(null, null) {});
            toCall.call();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static Packet callServerProtocol(Packet input) {
        Class<? extends ServerProtocol> type = serverProtocols.get(input.id());
        if (type == null) {
            return null;
        }
        try {
            ServerProtocol toCall = type.getDeclaredConstructor(Packet.class).newInstance(input);
            return toCall.call();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
