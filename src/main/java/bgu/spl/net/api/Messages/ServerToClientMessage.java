package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Messages.ServerToClient.NotificationMessage;

public abstract class ServerToClientMessage extends Message {

    public abstract byte[] encode();
}
