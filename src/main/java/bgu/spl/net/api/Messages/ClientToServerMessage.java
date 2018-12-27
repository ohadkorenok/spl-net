package bgu.spl.net.api.Messages;

import bgu.spl.net.srv.Database;

public abstract class ClientToServerMessage extends Message {

    public abstract Message process();
}
