package bgu.spl.net.api.Messages;

public abstract class ServerToClientMessage extends Message {

    public abstract byte[] encode();
}
