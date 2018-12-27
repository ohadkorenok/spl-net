package bgu.spl.net.impl.bidi;

import bgu.spl.net.api.Messages.ClientToServer.LoginMessage;
import bgu.spl.net.api.Messages.ClientToServerMessage;
import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.api.Messages.ServerToClient.AckMessage;
import bgu.spl.net.api.Messages.ServerToClientMessage;
import bgu.spl.net.api.User;
import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.Database;

public class BidiProtocol<Message> implements BidiMessagingProtocol<Message> {
    private Connections connections;
    private static Database db=new Database();
    private int connectionId;
    private boolean isTerminated;

    @Override
    public void start(int connectionId, Connections connections) {
        this.connections = connections;
        this.connectionId = connectionId;

    }

    @Override
    public void process(Message message) {
        if (message instanceof ClientToServerMessage){
            ServerToClientMessage response = ((ClientToServerMessage) message).process(db, connections, connectionId);

            connections.send(connectionId, response);
        }
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }

}
