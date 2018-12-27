package bgu.spl.net.api.Messages.ClientToServer;

import bgu.spl.net.api.Messages.ClientToServerMessage;
import bgu.spl.net.api.Messages.ServerToClientMessage;
import bgu.spl.net.api.State;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.Database;

import java.util.LinkedList;

public class ClientToServerNullMessage extends ClientToServerMessage {

    private static final State state = State.NULLSTATE;

    @Override
    public void decode(LinkedList args) {
        System.out.println("NULL MESSAGE");
    }

    @Override
    public ServerToClientMessage process(Database db, Connections connection, int connectionId) {
        return null;
    }
}
