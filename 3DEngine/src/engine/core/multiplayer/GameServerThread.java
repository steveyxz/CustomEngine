package engine.core.multiplayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServerThread implements Runnable {

    private final int port;
    private final ServerSocket serverSocket;
    private final GameServer server;
    private boolean serverRunning = true;
    private Socket socket;

    public GameServerThread(int port, GameServer server) throws IOException {
        this.port = port;
        this.server = server;
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        server.onStart();
        while (serverRunning) {
            try {
                socket = serverSocket.accept();
                server.onAcceptConnection(socket);
                new Thread(new GameCommThread(socket, this)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        server.onStop();
    }

    public int getPort() {
        return port;
    }

    public boolean isServerRunning() {
        return serverRunning;
    }

    public void setServerRunning(boolean serverRunning) {
        this.serverRunning = serverRunning;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public Socket getSocket() {
        return socket;
    }

    public GameServer getServer() {
        return server;
    }
}
