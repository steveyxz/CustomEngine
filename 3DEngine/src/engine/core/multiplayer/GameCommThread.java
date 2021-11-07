package engine.core.multiplayer;

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

    @Override
    public void run() {
        DataOutputStream dout = null;
        DataInputStream dinp = null;
        try {
            dinp = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        while (online) {
            try {
                String input = dinp.readUTF();

            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

}
