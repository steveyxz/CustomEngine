package engine.core.multiplayer.protocols;

import engine.core.multiplayer.GameClientThread;
import engine.core.multiplayer.GameServerThread;
import engine.core.multiplayer.packets.Packet;

import java.util.*;

public class ProtocolParser {

    private static final List<Protocol> protocols = new ArrayList<>();

    public static void addProtocol(Protocol protocol) {
        protocols.add(protocol);
    }

    public static Packet acceptPacketFromClient(Packet packet, GameServerThread serverThread) {
        String id = packet.getId();
        return Objects.requireNonNull(findProtocolAcceptingPacket(id)).interpretFromClient(packet, serverThread);
    }

    private static Protocol findProtocolAcceptingPacket(String id) {
        for (Protocol p : protocols) {
            System.out.println(p.getInputPacketId());
            if (p.getInputPacketId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public static void acceptPacketFromServer(Packet packet, GameClientThread clientThread) {
    }

}
