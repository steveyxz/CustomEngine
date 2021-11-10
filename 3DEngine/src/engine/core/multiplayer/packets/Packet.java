package engine.core.multiplayer.packets;

import java.util.List;

public abstract class Packet {

    private final List<PacketArgument> args;

    public Packet(List<PacketArgument> args) {
        this.args = args;
    }

    public abstract String getId();

    public String toString() {
        StringBuilder argString = new StringBuilder();
        for (PacketArgument arg : args) {
            argString.append(arg.toString());
            argString.append(";");
        }
        return getId() + ":" + argString;
    }

    public List<PacketArgument> getArgs() {
        return args;
    }

    public static Packet convertStringToPacket(String input) {
        int indexOfSep = input.indexOf(':');
        String packetId = input.substring(0, indexOfSep);
        String packetArgs = input.substring(indexOfSep + 1);
        return null;
    }

    public static String convertPacketToString(Packet packet) {
        return packet.toString();
    }
}
