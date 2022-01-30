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

    private static final Map<String, ClientProtocol> clientProtocols = new HashMap<>();
    private static final Map<String, ServerProtocol> serverProtocols = new HashMap<>();

    public static void registerClientProtocol(ClientProtocol protocol) {
        clientProtocols.put(protocol.getReceivingId(), protocol);
    }

    public static void registerServerProtocol(ServerProtocol protocol) {
        serverProtocols.put(protocol.getReceivingId(), protocol);
    }

    public static void callClientProtocol(Packet input) {
        ClientProtocol type = clientProtocols.get(input.id());
        if (type == null) {
            return;
        }
        try {
            ClientProtocol toCall = ClientProtocol.class.getDeclaredConstructor(Packet.class).newInstance(input);
            toCall.call();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static Packet callServerProtocol(Packet input) {
        ServerProtocol type = serverProtocols.get(input.id());
        if (type == null) {
            return null;
        }
        try {
            ServerProtocol toCall = ServerProtocol.class.getDeclaredConstructor(Packet.class).newInstance(input);
            return toCall.call();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
