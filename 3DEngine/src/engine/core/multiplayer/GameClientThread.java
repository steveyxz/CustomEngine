package engine.core.multiplayer;

public class GameClientThread implements Runnable {

    private GameClient client;
    private boolean connected = true;

    public GameClientThread(GameClient client) {
        this.client = client;
    }

    @Override
    public void run() {
        while (connected) {

        }
    }
}
