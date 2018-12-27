package bgu.spl.net.api.Messages;

import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.Database;

public abstract class ClientToServerMessage extends Message {

    public abstract ServerToClientMessage process(Database db, Connections connection , int connectionId);
}
