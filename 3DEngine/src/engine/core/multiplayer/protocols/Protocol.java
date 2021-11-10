package engine.core.multiplayer.protocols;

import engine.core.multiplayer.GameClientThread;
import engine.core.multiplayer.GameServerThread;
import engine.core.multiplayer.packets.Packet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

public interface Protocol {

    //Clientside, interpreting packet from the server
    void interpretFromServer(Packet input, GameClientThread clientThread);

    //Serverside, interpreting packet from the client and returning a packet
    Packet interpretFromClient(Packet input, GameServerThread serverThread);

    String getId();

    default String getInputPacketId() {

        return null;
    }


}
