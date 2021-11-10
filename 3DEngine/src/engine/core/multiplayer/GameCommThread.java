package engine.core.multiplayer;

import engine.core.multiplayer.packets.Packet;
import engine.core.multiplayer.protocols.ProtocolParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class GameCommThread implements Runnable {

    private final GameServerThread serverThread;
    private Socket socket;
    private boolean online = true;

    public GameCommThread(Socket socket, GameServerThread serverThread) {
        this.socket = socket;
        this.serverThread = serverThread;
    }

    public GameServerThread getServerThread() {
        return serverThread;
    }

    public Socket getSocket() {
        return socket;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @Override
    public void run() {
        DataOutputStream dout;
        DataInputStream dinp;
        try {
            dinp = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        while (online) {
            try {
                String input = dinp.readUTF();
                Packet output = ProtocolParser.acceptPacketFromClient(Packet.convertStringToPacket(input), serverThread);
                if (output == null) {
                    dout.writeUTF("error: ");
                }
                dout.writeUTF(output.toString());
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

}
