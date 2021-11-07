package engine.core.multiplayer.protocols;

public abstract class Protocol {

    private final String id;

    public Protocol(String id) {
        this.id = id;
        ProtocolParser.addProtocol(this);
    }

    public abstract void run(String info);

    public String getId() {
        return id;
    }
}
