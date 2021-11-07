package engine.core.multiplayer.protocols;

import java.util.HashMap;
import java.util.Map;

public class ProtocolParser {

    private static final Map<String, Protocol> protocols = new HashMap<>();

    public static void addProtocol(Protocol protocol) {
        protocols.put(protocol.getId(), protocol);
    }

    public static void runProtocol(String packet) {
        for (String protocol : protocols.keySet()) {
            if (packet.startsWith(protocol)) {
                packet = packet.replace(protocol, "");
                if (!packet.startsWith(":")) {
                    packet = protocol + packet;
                } else {
                    packet = packet.substring(1);
                    protocols.get(protocol).run(packet);
                }
            }
        }
    }

}
