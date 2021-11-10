package engine.core.tester;

import engine.core.multiplayer.GameServer;

import java.net.Socket;

public class TesterServer extends GameServer {
    public TesterServer(int port) {
        super(port);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onAcceptConnection(Socket socket) {

    }
}
