package engine.core.multiplayer;

public class GameClient {

    private final int port;

    public GameClient(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }
}
