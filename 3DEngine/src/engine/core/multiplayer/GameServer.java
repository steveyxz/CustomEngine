package engine.core.multiplayer;

import java.io.IOException;
import java.net.Socket;

public abstract class GameServer {

    public GameServer(int port) {
        try {
            new Thread(new GameServerThread(port, this)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void onStart();

    public abstract void onStop();

    public abstract void onAcceptConnection(Socket socket);

}
